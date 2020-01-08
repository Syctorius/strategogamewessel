package messages;

public class BattleResultMessage extends Message {
    private String attackerRank;
    private String defenderRank;
    private boolean attackerWins;

    public BattleResultMessage(String attackerRank, String defenderRank, boolean attackerWins) {
        this.attackerRank = attackerRank;
        this.defenderRank = defenderRank;
        this.attackerWins = attackerWins;
    }

    public String getAttackerRank() {
        return attackerRank;
    }

    public String getDefenderRank() {
        return defenderRank;
    }

    public boolean doesAttackerWin() {
        return attackerWins;
    }
}
