package helpers;

import enums.Color;
import enums.Rank;
import enums.Tile;
import enums.TileType;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class Board {
    private int length;
    private int width;
    private ArrayList<Rank> bluePieces;
    private ArrayList<Rank> redPieces;
    private ArrayList<Piece> toPlacePieces = new ArrayList<>();
    private ArrayList<Point> toPlacePiecesCoords = new ArrayList<>();
    //ArrayList<Piece> trayOfPieces = new ArrayList<>();
    Tile[][] tilesInGame;
    private int redWidth;
    private int redLength;
    private int blueWidth;
    private int blueLength;
    private List<Integer> positions = new ArrayList<>();
    private List<Rank> ranks = new ArrayList<>();


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
    public ArrayList<Piece> getToPlacePieces() {
        return toPlacePieces;
    }
    public ArrayList<Point> getToPlacePiecesCoords() {
        return toPlacePiecesCoords;
    }
    public ArrayList<Rank> getBluePieces() {
        return bluePieces;
    }

    public ArrayList<Rank> getRedPieces() {
        return redPieces;
    }

    public boolean checkIfPieceCanBePlaced(int x, int y, Piece pieceToPlace) {
        if (isInBounds(x, y, pieceToPlace.getColor()) && getPieceOnPosition(x, y) == null) {
            return true;
        }
        return false;
    }

    public Piece getPieceOnPosition(int posX, int posY) {

        return tilesInGame[posY][posX].getPiece();

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
                    setPiece(null, new Point(x, y));
                }
            }
        }
    }

    public boolean PlacePiece(Piece pieceToPlace, int x, int y, Color color) {
        if (checkIfPieceCanBePlaced(x, y, pieceToPlace)) {
            //  redPieces.remove(pieceToPlace);
            setPiece(pieceToPlace, new Point(x, y));
            if (color == Color.RED) {
                redPieces.add(pieceToPlace.getActualRank());

            } else {
                bluePieces.add(pieceToPlace.getActualRank());
            }
            return true;
        }

        return false;
    }

    public void removePiece(Point point) {
        setPiece(null, point);
    }

    private void addRankToList(Rank r, int times) {
        for (int i = 0; i < times; i++) {
            ranks.add(r);
        }
    }

    public synchronized void PlacePiecesAutomatically(Color color) {
        Random r = new Random();
        fillListWithRankAndRankCount();
        switch (color) {
            case RED:
                fillBoard(color, r, 60, 100);
                break;
            case BLUE:
                fillBoard(color,r,0,40);
                break;
        }

    }

    private void fillListWithRankAndRankCount() {
        addRankToList(Rank.MINER,5);
        addRankToList(Rank.FLAG,1);
        addRankToList(Rank.SPY,1);
        addRankToList(Rank.BOMB,6);
        addRankToList(Rank.SCOUT,8);
        addRankToList(Rank.SERGEANT,4);
        addRankToList(Rank.LIEUTENANT,4);
        addRankToList(Rank.CAPTAIN,4);
        addRankToList(Rank.MAJOR,3);
        addRankToList(Rank.COLONEL,2);
        addRankToList(Rank.GENERAL,1);
        addRankToList(Rank.MARSHAL,1);
    }
    private void fillListWithPositions(int a, int b)
    {
        for(int i = a; i < b; i++)
        {
            positions.add(i);
        }
    }

    private synchronized void fillBoard(Color color, Random r, int a, int b) {
        fillListWithPositions(a,b);


            Collections.shuffle(positions);
            while(!positions.isEmpty()) {
                Piece pieceToPlace = new Piece(ranks.get(0), color);
               int x = (positions.get(0)% 10);
               int y = (positions.get(0) / 10);
                if (checkIfPieceCanBePlaced(x, y, pieceToPlace)) {
                    tilesInGame[y][x].setPiece(pieceToPlace);
                    toPlacePieces.add(pieceToPlace);
                    toPlacePiecesCoords.add(new Point(y,x));
                }
                ranks.remove(0);
                positions.remove(0);


        }
        ranks = new ArrayList<>();
        positions = new ArrayList<>();
    }

    public ArrayList<Point> getEmptyFields(Color color) {
        ArrayList<Point> emptyFields = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (tilesInGame[i][j].getType() != TileType.WATER && getPieceOnPosition(i, j).getColor() != color) {
                    Point p = new Point();
                    p.setLocation(i, j);
                    emptyFields.add(p);
                }
            }
        }
        return emptyFields;
    }

    public void setPiece(Piece myPiece, Point newPos) {
        tilesInGame[newPos.y][newPos.x].setPiece(myPiece);
    }

    public synchronized void removeAllPieces(Color color) {
        switch (color) {
            case RED:
                fillListWithPositions(60, 100);

                break;
            case BLUE:
                fillListWithPositions(0,40);
        }

        while(!positions.isEmpty()) {

            int x = (positions.get(0)% 10);
            int y = (positions.get(0) / 10);
            tilesInGame[y][x].setPiece(null);
            positions.remove(0);


        }
        positions = new ArrayList<>();

    }
}
