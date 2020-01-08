package interfaces;

import enums.Color;
import enums.Rank;
import helpers.Piece;

import javax.swing.text.Position;
import java.awt.Point;
import java.util.List;

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
     * @param color
     * @param gameId
     */
    void endGame(Color color, int gameId);

    /**
     * Send an informational message to the user in a specific game.
     * @param messagestring
     * @param playerId
     * @param gameId
     */

    void sendMessageNotYourTurn(String messagestring, int playerId, int gameId);

    /**
     *
     * @param pieces
     * @param points
     * @param color
     * @param gameId
     */
    void placeAllPieces(List<Piece> pieces,List<Point> points ,Color color, int gameId);

    /**
     *
     * @param ranks
     * @param gameId
     * @param playerId
     */
    void updateFrequencyUI(List<Rank> ranks, int gameId, int playerId);

    /**
     *
     * @param attackRank
     * @param defenderRank
     * @param winsFight
     * @param gameId
     */
    void logBattleResult(Rank attackRank, Rank defenderRank,boolean winsFight, int gameId );

}
