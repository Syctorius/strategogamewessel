import enums.Color;
import enums.GameStatus;
import helpers.StrategoGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrategoGameTests {
    static StrategoGame game;

    @BeforeAll
    public static void init() {
        game = new StrategoGame();
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



}
