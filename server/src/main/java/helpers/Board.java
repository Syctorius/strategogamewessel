package helpers;

import enums.Color;
import enums.Rank;
import enums.Tile;
import enums.TileType;

import java.awt.*;
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
   private Tile[][] tilesInGame;


    private ArrayList<Rank> redRanks = new ArrayList<>();
    private ArrayList<Rank> blueRanks = new ArrayList<>();
    private ArrayList<Integer> redPositions= new ArrayList<>();
    private ArrayList<Integer> bluePositions= new ArrayList<>();

    public Board(int width, int length) {
        bluePieces = new ArrayList<>();
        redPieces = new ArrayList<>();
        BoardGenerationMultiThreading bgmt = new BoardGenerationMultiThreading();
        tilesInGame = bgmt.createField(width, length);
        this.length = length;
        this.width = width;


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
        return isInBounds(x, y, pieceToPlace.getColor()) && getPieceOnPosition(x, y) == null;
    }

    public Piece getPieceOnPosition(int posX, int posY) {

        return tilesInGame[posY][posX].getPiece();

    }

    private boolean isInBounds(int x, int y, Color color) {
        return x >= 0  && x <= 9 && y >= 0 && x <= 9;

    /*    if(color == Color.RED) {
           return  (x > 0 && y > 0 && x < redWidth && y < redLength);
        }
        return  (x > 0 && y > 0 && x < blueWidth && y < blueLength);*/


    }




    public boolean PlacePiece(Piece pieceToPlace, int x, int y, Color color) {
        if (isInBounds(x,y,color)) {
            // check for unit on position?
            //  redPieces.remove(pieceToPlace);
            setPiece(pieceToPlace, new Point(x, y));
            addToPiecesList(pieceToPlace, color);
            addToPointsList(x,y,color);
            return true;
        }

        return false;
    }

    private void addToPointsList(int x, int y,Color color) {
       if(color == Color.RED){
           redPositions.add(getConcatValue(x, y));
       }
       else {
           bluePositions.add(getConcatValue(x, y));
       }
    }

    private int getConcatValue(int x, int y) {
        return y * 10 + x;
    }

    private void addToPiecesList(Piece pieceToPlace, Color color) {
        if (color == Color.RED) {
            redPieces.add(pieceToPlace.getActualRank());

        } else {
            bluePieces.add(pieceToPlace.getActualRank());
        }
    }

    private void removeFromPiecesList(Piece pieceToPlace, Color color) {
        if (color == Color.RED) {
            redPieces.remove(pieceToPlace.getActualRank());

        } else {
            bluePieces.remove(pieceToPlace.getActualRank());
        }
    }

    public void removePiece(Point point) {
        Piece piece = getPieceOnPosition(point.x, point.y);
        if(piece!= null) {
            Color color = piece.getColor();
            removeFromPiecesList(piece, color);
            removeFromRankList(piece.getActualRank(),point);
           removeFromPositionList(getConcatValue(point.x,point.y),color);
            setPiece(null, point);
        }
    }

    private void removeFromPositionList(int concatValue,Color color) {
        if(color.equals(Color.RED)) {
            if(redPositions.contains(concatValue)) {
                redPositions.remove(redPositions.indexOf(concatValue));
            }
        }
        else {
            if(bluePositions.contains(concatValue)) {
                bluePositions.remove(bluePositions.indexOf(concatValue));
            }
        }

    }

    private void removeFromRankList(Rank actualRank,Point point) {
        if ((getConcatValue(point.x,point.y)) >= 40) {
            redRanks.remove(actualRank);
        } else {
            blueRanks.remove(actualRank);
        }
    }

    private void addRankToList(Rank r, int times, List<Rank> ranks) {
        for (int i = 0; i < times; i++) {
            ranks.add(r);
        }
    }

    public synchronized void PlacePiecesAutomatically(Color color) {
        Random r = new Random();

        if (color == Color.RED) {
            fillListWithRankAndRankCount(redRanks);
            fillBoard(color, r, 60, 100,redPositions,redRanks);
        } else if (color == Color.BLUE) {
            fillListWithRankAndRankCount(blueRanks);
            fillBoard(color, r, 0, 40,bluePositions,blueRanks);
        }

    }

    private void fillListWithRankAndRankCount(List<Rank> ranks) {
        addRankToList(Rank.MINER, 5,ranks);
        addRankToList(Rank.FLAG, 1,ranks);
        addRankToList(Rank.SPY, 1,ranks);
        addRankToList(Rank.BOMB, 6,ranks);
        addRankToList(Rank.SCOUT, 8,ranks);
        addRankToList(Rank.SERGEANT, 4,ranks);
        addRankToList(Rank.LIEUTENANT, 4,ranks);
        addRankToList(Rank.CAPTAIN, 4,ranks);
        addRankToList(Rank.MAJOR, 3,ranks);
        addRankToList(Rank.COLONEL, 2,ranks);
        addRankToList(Rank.GENERAL, 1,ranks);
        addRankToList(Rank.MARSHAL, 1,ranks);
    }

    private void fillListWithPositions(int a, int b,List<Integer> positions) {

        for (int i = a; i < b; i++) {

                positions.add(i);

        }
    }
    private void fillListWithPositionsPlaceAutomatically(int a, int b, List<Integer> positions, ArrayList<Rank> ranks) {
        HashMap<Integer,Rank> hashMap = new HashMap<>();
        for (int i = a; i < b; i++) {
            if (!positions.contains(i)) {
                positions.add(i);
            } else {
                // if pieces have been placed already don't add them again.
                addReOcurringPositionsToHashmap(positions, ranks, hashMap, i);


            }

        }
        Collections.shuffle(positions);
        for (Map.Entry<Integer, Rank> gameSession :hashMap.entrySet())
        {
            positions.add(gameSession.getKey());
            ranks.add(gameSession.getValue());
        }
    }

    private void addReOcurringPositionsToHashmap(List<Integer> positions, ArrayList<Rank> ranks, HashMap<Integer, Rank> hashMap, int i) {
        Piece piece =  getPieceOnPosition(getAX(i), getY(i));

        hashMap.put(positions.get(positions.indexOf(i)), getPieceOnPosition((getAX(i)), getY(i)).getActualRank());
        positions.remove(positions.indexOf(i));

        ranks.remove(getPieceOnPosition((getAX(i)), getY(i)).getActualRank());
        removeFromPiecesList(piece, piece.getColor());
    }

    private int getY(int i) {
        return i / 10;
    }

    private int getAX(int i) {
        return i % 10;
    }


    private synchronized void fillBoard(Color color, Random r, int a, int b, ArrayList<Integer> positions, ArrayList<Rank> ranks) {
        fillListWithPositionsPlaceAutomatically(a, b,positions,ranks);
        removeAllFromPiecesList(color);


        while (!positions.isEmpty() && !ranks.isEmpty()) {
            Piece pieceToPlace = new Piece(ranks.get(0), color);
            int x = calculateX(positions);
            int y = calculateY(positions);
            if (isInBounds(x, y, color)) {
                tilesInGame[y][x].setPiece(pieceToPlace);
                addToToPlacePieces(color, pieceToPlace, x, y);
            }
            ranks.remove(0);
            positions.remove(0);


        }
    }

    private void addToToPlacePieces(Color color, Piece pieceToPlace, int x, int y) {
        toPlacePieces.add(pieceToPlace);
        addToPiecesList(pieceToPlace, color);
        toPlacePiecesCoords.add(new Point(x, y));
    }

    private void removeAllFromPiecesList(Color color) {
        if (color == Color.RED) {
            redPieces.clear();

        } else {
            bluePieces.clear();
        }
    }

    private int calculateY(List<Integer> positions) {
        return (positions.get(0) / 10);
    }

    private int calculateX(List<Integer> positions) {
        return (getAX(positions.get(0)));
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
                redPositions.clear();
                redPieces.clear();
                fillListWithPositions(60, 100,redPositions);
                removeAll(redPositions);
                redRanks.clear();

                break;
            case BLUE:
                bluePieces.clear();
                bluePositions.clear();
                fillListWithPositions(0, 40,bluePositions);
                removeAll(bluePositions);
                redRanks.clear();
        }




    }

    private void removeAll(List<Integer> positions) {
        while (!positions.isEmpty()) {

            int x = calculateX(positions);
            int y = calculateY(positions);
            tilesInGame[y][x].setPiece(null);
            positions.remove(0);


        }
        positions = new ArrayList<>();
    }

    public Tile[][] getTilesInGame() {
        return tilesInGame;
    }
}
