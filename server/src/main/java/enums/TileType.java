package enums;

import helpers.Piece;

public enum TileType {
    //Tiles can be subdivided into water redland, blueland, Neutral, and water tiles,
    // it's also possible it could have a unit standing on the tile
    WATER,
    REDLAND,
    BLUELAND,
    NEUTRAL;

    private Piece piece = null;

    // the unit that is possibly standing on the tile get's returned, but only if this tiletype is a unit.
    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece pieceToPlace) {
        piece = pieceToPlace;
    }
}


