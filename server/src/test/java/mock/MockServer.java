package mock;

import enums.Color;
import enums.Rank;
import helpers.Piece;
import interfaces.StrategoServer;

import java.awt.*;
import java.util.List;

public class MockServer implements StrategoServer {
    @Override
    public void moveUnit(Point oldPos, Point newPos, int gameId) {
        System.out.println("" +oldPos +newPos +gameId);

    }

    @Override
    public void deleteUnit(Point Pos, int gameId) {
        System.out.println("" +Pos +gameId);
    }

    @Override
    public void endGame(Color color, int gameId) {
        System.out.println("" + color +gameId);
    }

    @Override
    public void sendMessageNotYourTurn(String messageString, int playerId, int gameId) {
        System.out.println("" +messageString + playerId +gameId);
    }

    @Override
    public void placeAllPieces(List<Piece> pieces, List<Point> points, Color color, int gameId) {
        System.out.println("" +pieces +points+color+gameId);
    }

    @Override
    public void updateFrequencyUI(List<Rank> ranks, int gameId, int playerId) {
        System.out.println("" +ranks+gameId+playerId);
    }

    @Override
    public void logBattleResult(Rank attackRank, Rank defenderRank, boolean winsFight, int gameId) {
        System.out.println("" +attackRank+"wins ="+winsFight+"VS" +defenderRank +gameId);
    }

    @Override
    public void placePiece(Integer key, Rank rank, int x, int y, Color color) {

    }
}
