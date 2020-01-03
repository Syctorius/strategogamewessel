package frontendenums;

public enum MoveStatus {
    OPP_TURN, // Opponents turn
    NONE_SELECTED, // Player hasn't selected a tile or a piece/
    PIECE_SELECTED, // Player hasn't selected a tile
    TILE_SELECTED, //Player hasn't selected a piece
    VALID_SELECTION // Valid Selection
}
