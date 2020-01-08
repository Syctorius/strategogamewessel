package helpers;

import enums.BattleOutcome;
import enums.Color;
import enums.GameStatus;
import enums.Rank;
import interfaces.IGame;
import interfaces.StrategoServer;
import server.ServerEndPoint;
import user.User;

import java.awt.*;
import java.util.*;

public class StrategoGame implements IGame {
    GameStatus status;
    User user;
    User opponent;
    Color turnColor = Color.RED;
    Board board;
    private static StrategoServer application;
    private static Boolean singlePlayerMode;
    private Integer key;
    private ArrayList<Rank> allCheckableRanks = new ArrayList<Rank>(Arrays.asList(Rank.FLAG,Rank.BOMB,Rank.CAPTAIN,Rank.COLONEL,Rank.GENERAL,Rank.LIEUTENANT,Rank.MAJOR,Rank.MARSHAL,Rank.MINER,Rank.SCOUT,Rank.SERGEANT,Rank.SPY));
    private int redPlayerId;
    private int bluePlayerId;
    public StrategoGame(User user, User opponent, StrategoServer serverEndPoint) {
        this.user = user;
        this.opponent = opponent;
        this.application = serverEndPoint;
    }


    public StrategoGame(Integer key, StrategoServer serverEndPoint, int redPlayerId, int bluePlayerId) {
        this.key = key;
        this.application = serverEndPoint;
        this.redPlayerId = redPlayerId;
        this.bluePlayerId = bluePlayerId;
    }

    public StrategoGame(Integer key, StrategoServer serverEndPoint) {
        this.key = key;
        this.application = serverEndPoint;
    }

    public StrategoGame() {

    }

    // Starts the game


    public GameStatus getStatus() {
        return status;
    }

    public boolean areAllPiecesPlaced(ArrayList<Rank> list) {
        return countFrequencies(list, allCheckableRanks.get(1)) && allCheckableRanks.stream().allMatch(r -> countFrequencies(list, r));
        // if everything is true;

    }

    public static boolean countFrequencies(ArrayList<Rank> list, Rank r) {

        // hash set is created and elements of
        // arraylist are insertd into it

        switch (r) {
            case FLAG:
            case SPY:
            case GENERAL:
            case MARSHAL:
                return Collections.frequency(list, r) == 1;
            case BOMB:
                return Collections.frequency(list, r) == 6;
            case MINER:
                return Collections.frequency(list, r) == 5;
            case COLONEL:
                return Collections.frequency(list, r) == 2;
            case SERGEANT:
            case CAPTAIN:
            case LIEUTENANT:
                return Collections.frequency(list, r) == 4;
            case SCOUT:
                return Collections.frequency(list, r) == 8;
            case MAJOR:
                return Collections.frequency(list, r) == 3;

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
        removeAllPieces(playerNr,color);

        // Board playerBoard = user.getBoard();
        this.board.PlacePiecesAutomatically(color);
        application.placeAllPieces(board.getToPlacePieces(),board.getToPlacePiecesCoords(), color,this.key);
        updateFrequencyUI();
    }


    @Override
    public boolean placePiece(int playerNr, Rank rank, int X, int Y, Color color) {

        Piece pieceToPlace = new Piece(rank, color);

        if (board.PlacePiece(pieceToPlace, X, Y, color)) {
            updateFrequencyUI();
            return true;

        }
        return false;

    }

    @Override
    public void removeAllPieces(int playerNr, Color color) {
board.removeAllPieces(color);
        updateFrequencyUI();
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
                        updateFrequencyUI();
                        switchTurn();
                    }
                } else {
                    application.sendMessageNotYourTurn("It's not your turn ", turnColor == Color.BLUE ? bluePlayerId : redPlayerId, this.key);
                }
            }
        }
    }

    private void updateFrequencyUI() {
        if(status == GameStatus.PLAYING) {
            if (turnColor == Color.BLUE) {
                application.updateFrequencyUI(board.getBluePieces(), this.key, bluePlayerId);
            } else {
                application.updateFrequencyUI(board.getRedPieces(), this.key, redPlayerId);
            }
        }
        else {
            application.updateFrequencyUI(board.getBluePieces(), this.key, bluePlayerId);
            application.updateFrequencyUI(board.getRedPieces(), this.key, redPlayerId);
        }
    }

    private void switchTurn() {
        turnColor = turnColor == Color.BLUE ? turnColor.RED : turnColor.BLUE;
    }

    private void sortMovement(Point oldPos, Point newPos, Piece myPiece, Piece enemyPiece) {
        if (enemyPiece != null) {
            if (enemyPiece.getColor() != myPiece.getColor()) {
                settleFightResult(myPiece.winFight(enemyPiece), myPiece, oldPos, newPos, enemyPiece);
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

    private void settleFightResult(BattleOutcome winFight, Piece myPiece, Point oldPos, Point newPos, Piece enemyPiece) {
        switch (winFight) {
            case GAMEDONE:
                endGame();
                //SEND MESSAGE to end game.
                application.logBattleResult(myPiece.getActualRank(),enemyPiece.getActualRank(),true,this.key);
                application.endGame(myPiece.getColor(), this.key);

                break;
            case WIN:
                board.removePiece(newPos);
                application.deleteUnit(newPos, this.key);

                movePiece(myPiece, oldPos, newPos);
                application.moveUnit(oldPos, newPos, this.key);
                application.logBattleResult(myPiece.getActualRank(),enemyPiece.getActualRank(),true,this.key);
                updateFrequencyUI();

                break;
            case LOSE:
                board.removePiece(oldPos);
                application.deleteUnit(oldPos, this.key);
                application.logBattleResult(myPiece.getActualRank(),enemyPiece.getActualRank(),false,this.key);
                updateFrequencyUI();
                //GUI.REMOVEPIECE
                break;
            case TIE:
                board.removePiece(oldPos);
                board.removePiece(newPos);
                application.deleteUnit(oldPos, this.key);
                application.deleteUnit(newPos, this.key);
                application.logBattleResult(myPiece.getActualRank(),enemyPiece.getActualRank(),false,this.key);
                updateFrequencyUI();
                //GUI.REMOVEPIECES
                break;


        }
    }

    private Piece checkForPiece(Point position) throws NullPointerException {
        return board.tilesInGame[position.y][position.x].getPiece();
    }


    @Override
    public boolean haveBothPlayersPlacedAllUnits() {
        return areAllPiecesPlaced(board.getBluePieces()) &&  areAllPiecesPlaced(board.getRedPieces());
    }

    @Override
    public void removePiece(Point coords) {
        board.removePiece(coords);
        updateFrequencyUI();

    }







}
