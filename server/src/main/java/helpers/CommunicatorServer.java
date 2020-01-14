package helpers;

import enums.Color;
import enums.Rank;
import interfaces.StrategoServer;

import java.awt.*;
import java.util.List;

public class CommunicatorServer implements StrategoServer {
    StrategoServer st;

    public CommunicatorServer(StrategoServer st) {
        this.st = st;

    }

    @Override
    public void moveUnit(Point oldPos, Point newPos, int gameId) {
        st.moveUnit(oldPos, newPos, gameId);
    }

    @Override
    public void deleteUnit(Point Pos, int gameId) {
        st.deleteUnit(Pos, gameId);
    }

    @Override
    public void endGame(Color color, int gameId) {
        st.endGame(color, gameId);
    }

    @Override
    public void sendMessageNotYourTurn(String messagestring, int playerId, int gameId) {
        st.sendMessageNotYourTurn(messagestring, playerId, gameId);

    }

    @Override
    public void placeAllPieces(List<Piece> pieces, List<Point> points, Color color, int gameId) {
        st.placeAllPieces(pieces, points, color, gameId);

    }

    @Override
    public void updateFrequencyUI(List<Rank> ranks, int gameId, int playerId) {
        st.updateFrequencyUI(ranks, gameId, playerId);

    }

    @Override
    public void logBattleResult(Rank attackRank, Rank defenderRank, boolean winsFight, int gameId) {
        st.logBattleResult(attackRank, defenderRank, winsFight, gameId);
    }
}
