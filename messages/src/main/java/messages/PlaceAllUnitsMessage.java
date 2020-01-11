package messages;

public class PlaceAllUnitsMessage extends Message {
    private int teamcolor;

    public PlaceAllUnitsMessage(int teamcolor) {
        this.teamcolor = teamcolor;
    }

    public int getTeamcolor() {
        return teamcolor;
    }
}
