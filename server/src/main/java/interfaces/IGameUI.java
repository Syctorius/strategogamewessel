package interfaces;

import enums.TileType;

public interface IGameUI {


   /* *//**
     *  //Uses MultiThreading to Draw the field TilesInGame Can't be null.
     * @param tilesInGame
     * @param width
     * @param length
     *//*
    void drawField(TileType[][] tilesInGame, int width, int length);

    *//**
     *
     * @param playerNr
     * @param errorMessage
     */
    public void showErrorMessage(int playerNr, String errorMessage);

    /**
     * Show state of a square in the target area.
     * The new state of the square will be shown in the targeted area
     * this is based on movement/battleresults
     * @param playerNr
     * @param posX
     * @param posY
     * @param tileType
     */
    public void showSquarePlayer(int playerNr, int posX, int posY, TileType tileType);

    /**
     * Show state of a square in the target area.
     * The new state of the square will be shown in the targeted area
     * this is based on movement/battleresults
     * @param playerNr
     * @param posX
     * @param posY
     * @param tileType
     */
    public void showSquareOpponent(int playerNr, int posX, int posY, TileType tileType);

}
