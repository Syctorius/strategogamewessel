package interfaces;

import frontendenums.GameStatus;
import frontendenums.Rank;

import java.awt.*;
import java.util.ArrayList;

public interface ICommunicatorClient {
    void deleteUnitAtPosition(Point postion);

    void matchFound(int teamcolor);

    void setGameStatus(GameStatus gameStatus);

    void moveUnitToPosition(Point oldPos, Point newPos);

    void placeUnit(int x, int y, String rank);

    void disableButtons(boolean isEnabled);

    void endGame(int teamcolor);

    void showNotYourTurn();

    void placeUnitForOpponent(int x, int y);

    void resetUI(int teamcolor);

    void updateFrequencyUI(ArrayList<Rank> ranks, int color);
    void logBattleResult(String attackerRank,String defenderRank,boolean doesAttackerWin);

}
