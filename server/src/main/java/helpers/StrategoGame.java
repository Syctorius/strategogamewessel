package helpers;

import enums.BattleOutcome;
import enums.Color;
import enums.GameStatus;
import enums.Rank;
import interfaces.IGame;
import interfaces.StrategoServer;

import java.awt.*;

public class StrategoGame implements IGame {
    GameStatus status;
    User user;
    User opponent;
    Color turnColor = Color.RED;
    Board board;
    private static StrategoServer application;
    private static Boolean singlePlayerMode;
    private Integer key;

    public StrategoGame(User user, User opponent, StrategoServer serverEndPoint) {
        this.user = user;
        this.opponent = opponent;
        this.application = serverEndPoint;
    }


    public StrategoGame() {

    }

    public StrategoGame(Integer key, StrategoServer serverEndPoint) {
        this.key = key;
        this.application = serverEndPoint;
    }

    // Starts the game


    public GameStatus getStatus() {
        return status;
    }

    public boolean areAllPiecesPlaced() {
        if (board.getBluePieces().size() == 0 && board.getRedPieces().size() == 0) {
            return true;
        }
        return false;

    }

    @Override
    public void startGamePlanningPhase() {
        board = new Board(10, 10);
        this.status = GameStatus.SETUP;
    }

    @Override
    public void startGamePlayingPhase() {
        this.status = GameStatus.PLAYING;
    }

    public void endGame() throws UnsupportedOperationException {
        status = GameStatus.STOPPED;
    }

    @Override
    public void registerPlayer(String name, String password, boolean singlePlayerMode) {
        this.singlePlayerMode = singlePlayerMode;

        if (name == null || name == "") {
            // application.showErrorMessage(user.getPlayerNr(), "Please fill in the name field.");
            throw new IllegalArgumentException();
        }
        if (password == null || password == "") {
            // application.showErrorMessage(user.getPlayerNr(), "Please fill in the password field.");
            throw new IllegalArgumentException();
        }


        if (application == null) {
            throw new IllegalArgumentException();
        }

        if (user.getUsername() == name) {
            throw new IllegalArgumentException();
        }

        if (singlePlayerMode && user != null && opponent != null) {
            //application.showErrorMessage(user.getPlayerNr(), "Unexpected error: Lobby size is 2.");
            throw new IllegalArgumentException();
        }


        if (singlePlayerMode && user == null) {
            user = new User(0, name);

            opponent = new User(1, "AI");

            this.application = application;

            // application.setPlayerNumber(currentPlayer.getPlayerNr(), currentPlayer.getUsername());

            return;
        }
        if (!singlePlayerMode && user == null || opponent == null) {
            user = new User(0, name);

        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean loginPlayer(String name, String password, boolean singlePlayerMode) {
        return false;
    }


    @Override
    public void placePiecesAutomatically(int playerNr, Color color) {
        removeAllPieces(playerNr);

        Board playerBoard = user.getBoard();
        playerBoard.PlacePiecesAutomatically(color);
    }


    @Override
    public boolean placePiece(int playerNr, Rank rank, int X, int Y, Color color) {

        Piece pieceToPlace = new Piece(rank, color);

        if (board.PlacePiece(pieceToPlace, X, Y, color)) {
            return true;
        }
        return false;

    }

    @Override
    public void removeAllPieces(int playerNr) {
        Board board;
        switch (playerNr) {
            case 0:
                board = user.getBoard();
                break;
            case 1:
                board = opponent.getBoard();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + playerNr);
        }

        board.RemoveAllPieces();


    }

    @Override
    public void movePiece(Point oldPos, Point newPos) {
        Piece myPiece = checkForPiece(oldPos);
        Piece enemyPiece = checkForPiece(newPos);
        if (oldPos.getLocation() != newPos.getLocation()) {
            if (myPiece != null) {
                if (myPiece.getColor() == turnColor) {
                    if (canPieceMoveToRange(myPiece, oldPos, newPos)) {
                        sortMovement(oldPos, newPos, myPiece, enemyPiece);
                        switchTurn();
                    }
                }
                else {
                    application.sendMessageNotYourTurn("It's not your turn ",turnColor,this.key);
                }
            }
        }
    }

    private void switchTurn() {
        turnColor = turnColor == Color.BLUE ? turnColor.RED : turnColor.BLUE;
    }

    private void sortMovement(Point oldPos, Point newPos, Piece myPiece, Piece enemyPiece) {
        if (enemyPiece != null) {
            if (enemyPiece.getColor() != myPiece.getColor()) {
                settleFightResult(myPiece.winFight(enemyPiece), myPiece, oldPos, newPos);
            } else {
                //do nothing or send message idk.
            }
        } else {
            movePiece(myPiece, oldPos, newPos);
            application.moveUnit(oldPos, newPos, this.key);
            //Move In UI
        }
    }

    private void movePiece(Piece myPiece, Point oldPos, Point newPos) {
        if (canPieceMoveToRange(myPiece, oldPos, newPos)) {
            board.setPiece(null, oldPos);
            board.setPiece(myPiece, newPos);
        }
    }

    private boolean canPieceMoveToRange(Piece myPiece, Point oldPos, Point newPos) {
        switch (myPiece.getActualRank()) {
            case SCOUT:
                return canScoutMoveToRange(oldPos, newPos);
            case BOMB:
            case FLAG:
                return false;
            default:
                return canRegularPieceMoveToRange(oldPos, newPos);
        }

    }

    private boolean canRegularPieceMoveToRange(Point oldPos, Point newPos) {

        return Math.abs(oldPos.x) + Math.abs(oldPos.y) + 1 == Math.abs(newPos.x) + Math.abs(newPos.y) || Math.abs(oldPos.x) + Math.abs(oldPos.y) - 1 == Math.abs(newPos.x) + Math.abs(newPos.y);


    }

    private boolean canScoutMoveToRange(Point oldPos, Point newPos) {

        return oldPos.y == newPos.y || oldPos.x == newPos.x; // Horizontal or vertical move;


    }

    private void settleFightResult(BattleOutcome winFight, Piece myPiece, Point oldPos, Point newPos) {
        switch (winFight) {
            case GAMEDONE:
                endGame();
                //SEND MESSAGE to end game.
                application.endGame(myPiece.getColor(),this.key);
                break;
            case WIN:
                board.removePiece(newPos);
                application.deleteUnit(newPos, this.key);

                movePiece(myPiece, oldPos, newPos);
                application.moveUnit(oldPos, newPos, this.key);

                break;
            case LOSE:
                board.removePiece(oldPos);
                application.deleteUnit(oldPos, this.key);
                //GUI.REMOVEPIECE
                break;
            case TIE:
                board.removePiece(oldPos);
                board.removePiece(newPos);
                application.deleteUnit(oldPos, this.key);
                application.deleteUnit(newPos, this.key);
                //GUI.REMOVEPIECES
                break;

        }
    }

    private Piece checkForPiece(Point position) throws NullPointerException {
        return board.tilesInGame[position.x][position.y].getPiece();
    }


    @Override
    public boolean haveBothPlayersPlacedAllUnits() {
        return true;
    }


}
