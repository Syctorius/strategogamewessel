package tests;

import enums.Color;
import enums.Rank;
import helpers.Board;
import helpers.Piece;
import helpers.StrategoGame;
import interfaces.IGame;
import interfaces.StrategoServer;
import junit.framework.Assert;
import mock.MockServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Boardtests {
  static Board board;
  static IGame game;

    @BeforeEach
    public  void init() {
        board = new Board(10,10);
        game = new StrategoGame(0, new MockServer(), 0, 1);
        board.removeAllPieces(Color.BLUE);
        board.removeAllPieces(Color.RED);
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
    public void placePiecesIncorrect() {
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
      game.placePiecesAutomatically(2,Color.BLUE);
        Assert.assertEquals(false,game.haveBothPlayersPlacedAllUnits());




    }
    @Test
    public void placeAllPiecesCorrect() {
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
        game.placePiecesAutomatically(2,Color.BLUE);
        Assert.assertEquals(false,game.haveBothPlayersPlacedAllUnits());
        game.placePiecesAutomatically(2,Color.RED);
        Assert.assertEquals(true,game.haveBothPlayersPlacedAllUnits());

    }
    @Test
    public void removeAllCorrect() {
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
        game.placePiecesAutomatically(2,Color.BLUE);
        Assert.assertEquals(false,game.haveBothPlayersPlacedAllUnits());
        game.placePiecesAutomatically(2,Color.RED);
        game.removeAllPieces(0,Color.BLUE);
        Assert.assertEquals(false,game.haveBothPlayersPlacedAllUnits());

    }
    @Test
    public void removeAllInCorrect() {
        //Arrange
        int x = 4;
        int y = 3;
        Color c = Color.BLUE;
        Rank rank = Rank.SPY;
        Piece p = new Piece(rank,c);
        game.removeAllPieces(0,Color.BLUE);
        Assert.assertEquals(false,game.haveBothPlayersPlacedAllUnits());

    }

}

