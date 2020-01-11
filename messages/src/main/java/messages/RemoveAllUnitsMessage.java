package messages;

public class RemoveAllUnitsMessage extends Message {
    private int teamcolor;

    public RemoveAllUnitsMessage(int teamcolor) {
        this.teamcolor = teamcolor;
    }

    public int getTeamcolor() {
        return teamcolor;
    }
}

