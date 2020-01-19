package interfaces;

import enums.Color;
import enums.Rank;
import helpers.Piece;
import messages.Message;
import messages.PlaceUnitForOpponentMessage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface IServerEndpoint {
        /**
         * Move a unit from the oldPosition to a newPosition in a specific game.
         * @param oldPos
         * @param newPos
         * @param gameId
         */
        void moveUnit(Point oldPos, Point newPos, int gameId);

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
        void endGame(int  color, int gameId);

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
        void placeAllPieces(ArrayList<String> pieces, java.util.List<Point> points , int color, int gameId);

        /**
         *
         * @param ranks
         * @param gameId
         * @param playerId
         */
        void updateFrequencyUI(ArrayList<String> ranks, int gameId, int playerId);

        /**
         * Send the battle result to the UI
         * may be null, gameId and playerId cannot be null
         * @param attackRank
         * @param defenderRank
         * @param winsFight
         * @param gameId
         */
        void logBattleResult(String attackRank, String defenderRank,boolean winsFight, int gameId );


    void placePiece(Integer key, PlaceUnitForOpponentMessage createMessage);
}
