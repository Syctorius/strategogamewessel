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
    public void endGame(int teamcolor) {
        WebSocketGui.getGameController().endGame(teamcolor);
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
    public void resetUI(int teamcolor) {
        WebSocketGui.getGameController().resetUI(teamcolor);
    }

    @Override
    public void updateFrequencyUI(ArrayList<Rank> ranks) {
        WebSocketGui.getGameController().updateFrequencyUI(ranks);
    }

    @Override
    public void logBattleResult(String attackerRank, String defenderRank, boolean doesAttackerWin) {
        WebSocketGui.getGameController().logBattleResult(attackerRank,defenderRank,doesAttackerWin);
    }
}
