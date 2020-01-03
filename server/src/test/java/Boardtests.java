import enums.Color;
import enums.Rank;
import helpers.Board;
import helpers.Piece;
import helpers.StrategoGame;
import interfaces.IGame;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Boardtests {
  static Board board;
  static IGame game;

    @BeforeAll
    public static void init() {
        board = new Board(10,10);
        game = new StrategoGame();
    }

    @Test
    public void placeSuccessfulTest() {
        //Arrange
        int x = 5;
        int y = 3;
        Color c = Color.BLUE;
        Rank rank = Rank.SPY;
        Piece pieceToPlace = new Piece(rank,c);
        
        //Act
        //Assert
        assertEquals(true,board.checkIfPieceCanBePlaced(x,y, pieceToPlace));


    }
    @Test
    public void placeNotSuccessfulTest() {
        //Arrange
        int x = 3223;
        int y = 3223;
        Color c = Color.BLUE;
        Rank rank = Rank.SPY;
        Piece pieceToPlace = new Piece(rank,c);
        //Act
        //Assert
        assertEquals(false,board.checkIfPieceCanBePlaced(x,y, pieceToPlace));


    }
    @Test
    public void placePieceOnOccupiedPosition() {
        //Arrange
        int x = 4;
        int y = 3;
        Color c = Color.BLUE;
        Rank rank = Rank.SPY;
        Piece p = new Piece(rank,c);

        //Act
        game.startGamePlanningPhase();
        game.placePiece(0, rank,x,y,c);
        //Assert
        assertEquals(false,board.checkIfPieceCanBePlaced(x,y,p));



    }

}

