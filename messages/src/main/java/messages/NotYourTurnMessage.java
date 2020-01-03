package messages;

public class NotYourTurnMessage {
    private int teamcolor;

    public NotYourTurnMessage(int teamcolor) {
        this.teamcolor = teamcolor;
    }

    public int getTeamcolor() {
        return teamcolor;
    }
}
