package messages;

public class EndGameMessage {
    private int teamcolor;

    public EndGameMessage(int teamcolor) {
        this.teamcolor = teamcolor;
    }

    public int getTeamcolor() {
        return teamcolor;
    }
}
