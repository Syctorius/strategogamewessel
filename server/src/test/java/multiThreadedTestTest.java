import enums.Tile;
import enums.TileType;
import helpers.BoardGenerationMultiThreading;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class multiThreadedTest {
    @Test
    public void TestpoorMultithreading()
    {


        //Arrange
        Tile[][] tilesInGame;
        int width = 10;
        int length = 10;
        BoardGenerationMultiThreading bgmt = new BoardGenerationMultiThreading();

        //Act
        tilesInGame = bgmt.createField(width, length);
        //Assert
        assertEquals(100,tilesInGame[1].length * tilesInGame[0].length);
    }

}