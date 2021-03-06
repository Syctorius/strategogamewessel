package helpers;

import enums.*;
import enums.Color;
import interfaces.IGame;
import interfaces.StrategoServer;
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
    private ArrayList<Rank> allCheckableRanks = new ArrayList<Rank>(Arrays.asList(Rank.FLAG, Rank.BOMB, Rank.CAPTAIN, Rank.COLONEL, Rank.GENERAL, Rank.LIEUTENANT, Rank.MAJOR, Rank.MARSHAL, Rank.MINER, Rank.SCOUT, Rank.SERGEANT, Rank.SPY));
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

    public StrategoGame(Integer key, StrategoServer serverEndPoint, int redPlayerId, int bluePlayerId, Board board) {
        this.key = key;
        this.application = serverEndPoint;
        this.board = board;
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
    public synchronized void placePiecesAutomatically(int playerNr, Color color) {
        // Board playerBoard = user.getBoard();
        this.board.PlacePiecesAutomatically(color);
        application.placeAllPieces(board.getToPlacePieces(), board.getToPlacePiecesCoords(), color, this.key);
        updateFrequencyUI();
    }


    @Override
    public void placePiece(int playerNr, Rank rank, int X, int Y, Color color) {

        Piece pieceToPlace = new Piece(rank, color);

        if(board.PlacePiece(pieceToPlace, X, Y, color))
        {
            application.placePiece(this.key, rank, X, Y, color);
            updateFrequencyUI();
        }

    }

    @Override
    public void removeAllPieces(int playerNr, Color color) {
        board.removeAllPieces(color);
        updateFrequencyUI();
    }


    @Override
    public void movePiece(Point oldPos, Point newPos, int id) {
        Piece myPiece = checkForPiece(oldPos);
        Piece enemyPiece = checkForPiece(newPos);
        if (oldPos.getLocation() != newPos.getLocation() && getTileType(newPos) != TileType.WATER) {
            if (myPiece != null) {
                if (correctPlayerTurn(myPiece, id)) {
                    if (canPieceMoveToRange(myPiece, oldPos, newPos)) {
                        sortMovement(oldPos, newPos, myPiece, enemyPiece);
                        updateFrequencyUI();
                        switchTurn();
                    }
                }
            }
        }
    }

    private TileType getTileType(Point newPos) {
        return board.getTilesInGame()[newPos.y][newPos.x].getType();
    }

    private boolean correctPlayerTurn(Piece myPiece, int id) {
        return getCorrectPlayerTurn(myPiece, id);

    }

    private Color getColorBasedOnId(int id) {
        return id == bluePlayerId ? Color.BLUE : Color.RED;
    }

    private boolean getCorrectPlayerTurn(Piece myPiece, int id) {
        Color color = getColorBasedOnId(id);
        if (myPiece.getColor() == color) {
            if (turnColor == color) {
                return true;
            } else {
                application.sendMessageNotYourTurn("it's not your turn", id, this.key);
            }
        }
        else {
            application.sendMessageNotYourTurn("That's not your Piece ", id, this.key);
        }
        return false;
    }

    private void updateFrequencyUI() {

        application.updateFrequencyUI(board.getBluePieces(), this.key, bluePlayerId);

        application.updateFrequencyUI(board.getRedPieces(), this.key, redPlayerId);

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

        board.setPiece(null, oldPos);
        board.setPiece(myPiece, newPos);

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

        if (oldPos.y == newPos.y) {
            // Horizontal move
            if (oldPos.x < newPos.x) {
                // Move right

                if (oldPos.x + 1 != newPos.x) {

                    return false;
                }

            } else {
                // Move left

                if (oldPos.x - 1 != newPos.x) {

                    return false;
                }
            }
        } else if (oldPos.x == newPos.x) {
            // Vertical move
            if (oldPos.y < newPos.y) {
                // Move down
                if (oldPos.y + 1 != newPos.y)
                    return false;
            } else {
                // Move up
                if (oldPos.y - 1 != newPos.y)

                    return false;

            }
        } else {
            // Not a valid  move (neither horizontal nor vertical)
            return false;
        }
        return true;


    }

    private boolean canScoutMoveToRange(Point oldPos, Point newPos) {
        int i;

        if (oldPos.y == newPos.y) {
            // Horizontal move
            if (oldPos.x < newPos.x) {
                // Move right
                for (i = oldPos.x + 1; i <= newPos.x; ++i) {
                    if (checkForPiece(new Point(i, oldPos.y)) != null)
                        return false;
                }

            } else {
                // Move left

                for (i = oldPos.x - 1; i >= newPos.x; --i) {
                    if (checkForPiece(new Point(i, oldPos.y)) != null)
                        return false;
                }
            }
        } else if (oldPos.x == newPos.x) {
            // Vertical move
            if (oldPos.y < newPos.y) {
                // Move down
                for (i = oldPos.y - 1; i >= newPos.y; --i)
                    if (checkForPiece(new Point(oldPos.x, i)) != null)
                        return false;
            } else {
                // Move up
                for (i = oldPos.y + 1; i <= newPos.y; ++i)
                    if (checkForPiece(new Point(oldPos.x, i)) != null)
                        return false;

            }
        } else {
            // Not a valid Scout move (neither horizontal nor vertical)
            return false;
        }
        return true;


    }

    private void settleFightResult(BattleOutcome winFight, Piece myPiece, Point oldPos, Point newPos, Piece enemyPiece) {
        switch (winFight) {
            case GAMEDONE:
                endGame();
                //SEND MESSAGE to end game.
                application.logBattleResult(myPiece.getActualRank(), enemyPiece.getActualRank(), true, this.key);
                application.endGame(myPiece.getColor(), this.key);

                break;
            case WIN:
                board.removePiece(newPos);
                application.deleteUnit(newPos, this.key);

                movePiece(myPiece, oldPos, newPos);
                application.moveUnit(oldPos, newPos, this.key);
                application.logBattleResult(myPiece.getActualRank(), enemyPiece.getActualRank(), true, this.key);
                updateFrequencyUI();

                break;
            case LOSE:
                board.removePiece(oldPos);
                application.deleteUnit(oldPos, this.key);
                application.logBattleResult(myPiece.getActualRank(), enemyPiece.getActualRank(), false, this.key);
                updateFrequencyUI();
                //GUI.REMOVEPIECE
                break;
            case TIE:
                board.removePiece(oldPos);
                board.removePiece(newPos);
                application.deleteUnit(oldPos, this.key);
                application.deleteUnit(newPos, this.key);
                application.logBattleResult(myPiece.getActualRank(), enemyPiece.getActualRank(), false, this.key);
                updateFrequencyUI();
                //GUI.REMOVEPIECES
                break;


        }
    }

    private Piece checkForPiece(Point position) throws NullPointerException {
        return board.getTilesInGame()[position.y][position.x].getPiece();
    }


    @Override
    public boolean haveBothPlayersPlacedAllUnits() {
        return areAllPiecesPlaced(board.getBluePieces()) && areAllPiecesPlaced(board.getRedPieces());
    }

    @Override
    public void removePiece(Point coords) {
        board.removePiece(coords);
        updateFrequencyUI();

    }


}
