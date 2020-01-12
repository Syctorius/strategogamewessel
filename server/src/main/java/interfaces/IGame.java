package interfaces;

import enums.Color;
import enums.Rank;

import java.awt.*;

public interface IGame {


    /**
     * StartGame Starts a new game and inits the board, Sets gameStatus to Setup
     */
    public void startGamePlanningPhase();

    /**
     * Sets gameStatus to playing, If SinglePlayer calls placePiecesAutomatically, if Multiplayer, gets opponents Pieces.
     */

    public void startGamePlayingPhase();

    /**
     * EndGame clears the board, calls the UI to show the result of the game, GameStatus must be set to stopped, the application can not be null,
     */

    public void endGame();

    /**
     * // The username and password cannot be null,
     * // there can't be two players when singleplayermode is enabled.
     * // The name has to be unique,
     * // the username and password can not be empty.
     * else -> the application throws @throws IllegalArgumentException
     *
     * @param name
     * @param password
     * @param singlePlayerMode
     */


    public void registerPlayer(String name, String password, boolean singlePlayerMode);

    /**
     * // The username and password cannot be null,
     * // there can't be two players when singleplayermode is enabled.
     * // The name has to exist in our current db.
     * // the username and password can not be empty.
     * // the username has to match the username listed in the db.
     * *     else -> the application throws @throws IllegalArgumentException
     *
     * @param name
     * @param password
     * @param singlePlayerMode
     * @return
     */

    public boolean loginPlayer(String name, String password, boolean singlePlayerMode);


    /**
     * //the piece can only be placed if there is no other piece at the current location
     * // and the piece is in bounds of the array
     * // and in bounds of the players territory.
     * // Will talk to the Ui and tell it to place a certain unit.
     *
     * @param playerNr
     */
    public void placePiecesAutomatically(int playerNr, Color color);


    /**
     * //Remove the piece that is placed at the square with coordinates (X, Y).
     * // Call the function that removes the piece in the UI
     * // Place a piece at the current location.
     * // Set Tiletype to unit.
     * // Set it's rank
     *
     * @param playerNr
     * @param pieceType
     * @param X
     * @param Y
     * @param color
     */
    public boolean placePiece(int playerNr, Rank pieceType, int X, int Y, Color color);

    /**
     * Remove all pieces that are placed.
     *
     * @param playerNr
     */
    public void removeAllPieces(int playerNr, Color color);

    public void movePiece(Point oldPos, Point newPos, int i);

    boolean haveBothPlayersPlacedAllUnits();

    void removePiece(Point coords);
}
