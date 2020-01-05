package messages;

public class RemoveAllUnitsMessage {
    private int teamcolor;

    public RemoveAllUnitsMessage(int teamcolor) {
        this.teamcolor = teamcolor;
    }

    public int getTeamcolor() {
        return teamcolor;
    }
}

