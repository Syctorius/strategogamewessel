package messages;

public class ResetUiMessage extends Message {
    private int teamcolor;

    public ResetUiMessage(int teamcolor) {
        this.teamcolor = teamcolor;
    }

    public int getTeamcolor() {
        return teamcolor;
    }
}
