package interfaces;

import enums.Color;
import enums.Rank;

import javax.swing.text.Position;
import java.awt.Point;

public interface StrategoServer {
    /**
     * Move a unit from the oldPosition to a newPosition in a specific game.
     * @param oldPos
     * @param newPos
     * @param gameId
     */
    void moveUnit(Point oldPos,Point newPos, int gameId);

    /**
     * Delete a unit at a certain position in a specific game.
     * @param Pos
     * @param gameId
     */
    void deleteUnit(Point Pos, int gameId);

    /**
     * Send a message to the user, in a specific game, stating that the current game has ended.
     * @param teamColor
     * @param gameId
     */
    void endGame(Color teamColor, int gameId);

    /**
     * Send an informational message to the user in a specific game.
     * @param messagestring
     * @param turnColor
     * @param gameId
     */

    void sendMessageNotYourTurn(String messagestring, Color turnColor, int gameId);
}
