package helpers;

import enums.Color;
import enums.Rank;
import enums.Tile;
import enums.TileType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Board {
    private int length;
    private int width;
    ArrayList<Piece> bluePieces;
    ArrayList<Piece> redPieces;
    //ArrayList<Piece> trayOfPieces = new ArrayList<>();
    Tile[][] tilesInGame;
    private int redWidth;
    private int redLength;
    private int blueWidth;
    private int blueLength;


    public Board(int width, int length) {
        bluePieces = new ArrayList<>();
        redPieces = new ArrayList<>();
        BoardGenerationMultiThreading bgmt = new BoardGenerationMultiThreading();
        tilesInGame = bgmt.createField(width, length);
        this.length = length;
        this.width = width;
        this.blueLength = 4;
        this.blueWidth = 10;
        this.redLength = 4;
        this.redWidth = 10;

    }

    public ArrayList<Piece> getBluePieces() {
        return bluePieces;
    }

    public ArrayList<Piece> getRedPieces() {
        return redPieces;
    }

    public boolean checkIfPieceCanBePlaced(int x, int y, Piece pieceToPlace) {
        if (isInBounds(x, y,pieceToPlace.getColor()) && getPieceOnPosition(x, y) == null) {
            return true;
        }
        return false;
    }

    public Piece getPieceOnPosition(int posX, int posY) {

       return tilesInGame[posX][posY].getPiece();

    }

    private boolean isInBounds(int x, int y, Color color) {
        return true;
    /*    if(color == Color.RED) {
           return  (x > 0 && y > 0 && x < redWidth && y < redLength);
        }
        return  (x > 0 && y > 0 && x < blueWidth && y < blueLength);*/



    }


    public void RemoveAllPieces() {
        if (tilesInGame[0].length > 0) {
            for (int x = 0; x < tilesInGame[0].length; x++) {
                for (int y = 0; y < tilesInGame[1].length; y++) {
                    setPiece(null, new Point(x,y));
                }
            }
        }
    }

    public boolean PlacePiece(Piece pieceToPlace, int x, int y, Color color) {
        if (checkIfPieceCanBePlaced(x, y, pieceToPlace)) {
          //  redPieces.remove(pieceToPlace);
            setPiece(pieceToPlace, new Point(x,y));
            return true;
        }

        return false;
    }

    public void removePiece(Point point)
    {
        setPiece(null, point);
    }
    public void PlacePiecesAutomatically(Color color) {

        Random r = new Random();
// make functional
//@TODO rank values needs to be amount for each rank
        for (int x = 0; x < Rank.values().length; x++) {

            int X = r.nextInt(10);
            int Y = r.nextInt(10);

            Piece pieceToPlace = new Piece(Rank.values()[x]);

            if (checkIfPieceCanBePlaced(X,Y,pieceToPlace)) {
                tilesInGame[X][Y].setPiece(pieceToPlace);


            }
        }
    }

    public ArrayList<Point> getEmptyFields(Color color) {
        ArrayList<Point> emptyFields = new ArrayList<>();
        for (int i = 0; i < width; i ++){
            for (int j = 0; j < length; j++){
                if (tilesInGame[i][j].getType() != TileType.WATER && getPieceOnPosition(i,j).getColor() != color) {
                    Point p = new Point();
                    p.setLocation(i,j);
                    emptyFields.add(p);
                }
            }
        }
        return emptyFields;
    }

    public void setPiece(Piece myPiece, Point newPos) {
        tilesInGame[newPos.x][newPos.y].setPiece(myPiece);
    }
}
