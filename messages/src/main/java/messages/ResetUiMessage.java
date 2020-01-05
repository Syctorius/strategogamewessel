package messages;

public class ResetUiMessage {
    private int teamcolor;

    public ResetUiMessage(int teamcolor) {
        this.teamcolor = teamcolor;
    }

    public int getTeamcolor() {
        return teamcolor;
    }
}
