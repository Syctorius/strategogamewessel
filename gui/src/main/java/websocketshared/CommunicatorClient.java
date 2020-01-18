package websocketshared;

import frontendenums.GameStatus;
import frontendenums.Rank;
import interfaces.ICommunicatorClient;

import java.awt.*;
import java.util.ArrayList;

public class CommunicatorClient implements ICommunicatorClient {
    @Override
    public void deleteUnitAtPosition(Point postion) {
    WebSocketGui.getGameController().deleteUnitAtPosition(postion);
    }

    @Override
    public void matchFound(int teamcolor) {
        WebSocketGui.getGameController().matchFound(teamcolor);
    }

    @Override
    public void setGameStatus(GameStatus gameStatus) {
        WebSocketGui.getGameController().setGameStatus(gameStatus);
    }

    @Override
    public void moveUnitToPosition(Point oldPos, Point newPos) {
        WebSocketGui.getGameController().moveUnitToPosition(oldPos,newPos);
    }

    @Override
    public void placeUnit(int x, int y, String rank) {
        WebSocketGui.getGameController().placeUnit(x,y,rank);
    }

    @Override
    public void disableButtons(boolean isEnabled) {
        WebSocketGui.getGameController().disableButtons(isEnabled);
    }

    @Override
    public void endGame(int teamColor) {
        WebSocketGui.getGameController().endGame(teamColor);
    }

    @Override
    public void showNotYourTurn() {
        WebSocketGui.getGameController().showNotYourTurn();
    }

    @Override
    public void placeUnitForOpponent(int x, int y) {
        WebSocketGui.getGameController().placeUnitForOpponent(x,y);
    }

    @Override
    public void resetUI(int teamColor) {
        WebSocketGui.getGameController().resetUI(teamColor);
    }

    @Override
    public void updateFrequencyUI(ArrayList<String> ranksString) {
        ArrayList<Rank> ranks = new ArrayList<>();
        for (String s : ranksString)
        {
            ranks.add(Rank.valueOf(s));
        }
        WebSocketGui.getGameController().updateFrequencyUI(ranks);
    }

    @Override
    public void logBattleResult(String attackerRank, String defenderRank, boolean doesAttackerWin) {
        WebSocketGui.getGameController().logBattleResult(attackerRank,defenderRank,doesAttackerWin);
    }
}
