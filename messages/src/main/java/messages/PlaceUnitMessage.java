package messages;

import java.awt.*;

public class PlaceUnitMessage extends Message {
    private Point coordsToPlace;
    private int color;
    private String rank;


    public PlaceUnitMessage(Point coordsToPlace,int color,String rank) {
        this.coordsToPlace = coordsToPlace;
        this.color = color;
        this.rank = rank;

    }

    public Point getCoordsToPlace() {
        return coordsToPlace;
    }

    public int getColor() {
        return this.color;
    }

    public String getRank() {
        return this.rank;
    }
}

