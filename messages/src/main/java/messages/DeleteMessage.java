package messages;

import java.awt.*;

public class DeleteMessage extends Message {
    private Point coords;

    public DeleteMessage(Point coords) {
        this.coords = coords;
    }

    public Point getCoords() {
        return coords;
    }
}
