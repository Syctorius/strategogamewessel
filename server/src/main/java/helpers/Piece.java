package helpers;

import enums.BattleOutcome;
import enums.Color;
import enums.Rank;

public class Piece {


    private Rank actualRank;

    private Color color;


    public Piece(Rank actualRank) {
        this.actualRank = actualRank;
    }

    public Piece() {

    }

    public Rank getActualRank() {
        if(this != null) {
            return actualRank;
        }
        return null;
    }

    public Color getColor() {
        return color;
    }

    public Piece(Rank actualRank, Color color) {
        this.actualRank = actualRank;
        this.color = color;
    }

    public BattleOutcome winFight(Piece defender) {
        if(defender.actualRank == Rank.FLAG) return BattleOutcome.GAMEDONE;
        if (this.actualRank == Rank.SPY && defender.actualRank == Rank.MARSHAL) return BattleOutcome.WIN;
        if (this.actualRank == Rank.MINER && defender.actualRank == Rank.BOMB) return BattleOutcome.WIN;
            if (actualRank.getLevelCode() == defender.actualRank.getLevelCode()) {
                return BattleOutcome.TIE;
            }
        if (actualRank.getLevelCode() > defender.actualRank.getLevelCode()) {
            return BattleOutcome.WIN;
        }
        return BattleOutcome.LOSE;


    }



    public String toString() {
        return actualRank.toString();
    }

}
