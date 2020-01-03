package enums;

import helpers.Piece;

public class Tile {

    private TileType type;
    private Piece piece = null;

    public Tile(TileType type) {
    this.type = type;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
