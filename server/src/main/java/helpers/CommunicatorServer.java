package helpers;

import enums.Color;
import enums.Rank;
import interfaces.IServerEndpoint;
import interfaces.StrategoServer;

import javax.websocket.server.ServerEndpoint;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CommunicatorServer implements StrategoServer {
    IServerEndpoint st;

    public CommunicatorServer(IServerEndpoint st) {
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
        int teamcolor = color == Color.RED ? 1 : 2;
        st.endGame(teamcolor, gameId);
    }

    @Override
    public void sendMessageNotYourTurn(String messagestring, int playerId, int gameId) {
        st.sendMessageNotYourTurn(messagestring, playerId, gameId);

    }

    @Override
    public void placeAllPieces(List<Piece> pieces, List<Point> points, Color color, int gameId) {
        int teamcolor = color == Color.RED ? 1 : 2;
        ArrayList<String> stringpieces = new ArrayList<>();
        for(Piece piece : pieces)
        {
            stringpieces.add(piece.getActualRank().toString());
        }
        st.placeAllPieces(stringpieces, points, teamcolor, gameId);

    }

    @Override
    public void updateFrequencyUI(List<Rank> ranks, int gameId, int playerId) {
        ArrayList<String> stringRanks = new ArrayList<>();
        for(Rank rank : ranks)
        {
            stringRanks.add(rank.toString());
        }
        st.updateFrequencyUI(stringRanks, gameId, playerId);

    }

    @Override
    public void logBattleResult(Rank attackRank, Rank defenderRank, boolean winsFight, int gameId) {
        st.logBattleResult(attackRank.toString(), defenderRank.toString(), winsFight, gameId);
    }
}
