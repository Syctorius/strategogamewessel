package messages;

import java.awt.*;

public class MovementMessage extends Message {
private Point oldCoords;
private Point newCoords;

    public MovementMessage(Point oldCoords, Point newCoords) {
        this.oldCoords = oldCoords;
        this.newCoords = newCoords;
    }

    public Point getOldCoords() {
        return oldCoords;
    }

    public Point getNewCoords() {
        return newCoords;
    }
}
