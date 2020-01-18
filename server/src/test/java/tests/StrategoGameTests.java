package tests;

import enums.Color;
import enums.GameStatus;
import enums.Rank;
import helpers.Board;
import helpers.Piece;
import helpers.StrategoGame;
import junit.framework.Assert;
import mock.MockServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrategoGameTests {
    static StrategoGame game;
    static Board board = new Board(10,10);

    @BeforeAll
    public static void init() {
        game = new StrategoGame(0, new MockServer(), 0, 1,board);
    }

    @Test
    public void GameSetupStatusTest() {
        //Arrange
        //Act
        game.startGamePlanningPhase();
        //Assert
        assertEquals(GameStatus.SETUP, game.getStatus());
    }

    @Test
    public void GameStatusPlayingTest() {
        //Arrange
        //Act
        game.startGamePlayingPhase();
        //Assert
        assertEquals(GameStatus.PLAYING, game.getStatus());
    }

    @Test
    public void GameStatusEndGameTest() {
        //Arrange

        //Act

        game.endGame();

        //Assert

        assertEquals(GameStatus.STOPPED, game.getStatus());
    }

    @Test
    public void placeAutomaticallyTest() {

        //Arrange

        int playerNr = 0;
        Color color1 = Color.RED;
        Color color2 = Color.BLUE;

        //Act

     //   game.registerPlayer("Some Name", "Some Password", true);
        game.startGamePlanningPhase();
        game.placePiecesAutomatically(playerNr,color1);
        game.placePiecesAutomatically(playerNr + 1,color2);

        //Assert
        Assertions.assertTrue(game.haveBothPlayersPlacedAllUnits());

     //   assertEquals(true,game.areAllPiecesPlaced(game));

    }
    @Test
    public void placePieceTest() {
        //Arrange
        int x = 4;
        int y = 3;
        Color c = Color.BLUE;
        Rank rank = Rank.SPY;
        //Act
        game.placePiece(0, rank,x,y,c);
        //Assert
        assertEquals(rank,board.getBluePieces());
    }
@Test
    public void removeAllInCorrect() {
        //Arrange
        int x = 4;
        int y = 3;
        Color c = Color.BLUE;
        Rank rank = Rank.SPY;
        Piece p = new Piece(rank,c);
        //Act
        game.removeAllPieces(0,Color.BLUE);
        //Assert
        Assert.assertEquals(false,game.haveBothPlayersPlacedAllUnits());

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
    public void placeNotSuccessfulTest() {
        //Arrange
        int x = 3223;
        int y = 3223;
        Color c = Color.BLUE;
        Rank rank = Rank.SPY;
        Piece pieceToPlace = new Piece(rank,c);
        //Act
        //Assert
        assertEquals(false,game.placePiece(0,rank,x,y,c));


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

}
