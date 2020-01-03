package messages;

import messageenum.MessageType;

public class MatchFoundMessage extends Message {
    private String opponent;
    private Integer teamcolor;
    private MessageType mt;

    public MatchFoundMessage(String opponent,Integer teamcolor ,MessageType mt) {
        this.opponent = opponent;
        this.teamcolor = teamcolor;
        this.mt = mt;
    }

    public String getOpponent() {
        return opponent;
    }

    public Integer getTeamcolor() {
        return this.teamcolor;
    }
}
