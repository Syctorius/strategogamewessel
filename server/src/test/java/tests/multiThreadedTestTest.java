package tests;

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
    @Test
    public void TestpoorMultithreading2()
    {


        //Arrange
        Tile[][] tilesInGame;
        int width = 10;
        int length = 10;
        BoardGenerationMultiThreading bgmt = new BoardGenerationMultiThreading();

        //Act
        tilesInGame = bgmt.createField(width, length);
        //Assert
        assertEquals(TileType.BLUELAND,tilesInGame[1][0].getType());

    }
    @Test
    public void TestpoorMultithreading3()
    {


        //Arrange
        Tile[][] tilesInGame;
        int width = 10;
        int length = 10;
        BoardGenerationMultiThreading bgmt = new BoardGenerationMultiThreading();

        //Act
        tilesInGame = bgmt.createField(width, length);
        //Assert

        assertEquals(TileType.NEUTRAL,tilesInGame[5][0].getType());

    }
    @Test
    public void TestpoorMultithreading5()
    {


        //Arrange
        Tile[][] tilesInGame;
        int width = 10;
        int length = 10;
        BoardGenerationMultiThreading bgmt = new BoardGenerationMultiThreading();

        //Act
        tilesInGame = bgmt.createField(width, length);
        //Assert

        assertEquals(TileType.WATER,tilesInGame[5][3].getType());

    }
    @Test

    public void TestpoorMultithreading4()
    {

        //Arrange
        Tile[][] tilesInGame;
        int width = 10;
        int length = 10;
        BoardGenerationMultiThreading bgmt = new BoardGenerationMultiThreading();

        //Act
        tilesInGame = bgmt.createField(width, length);
        //Assert
        assertEquals(TileType.REDLAND,tilesInGame[8][0].getType());
    }
    @Test
    public void testTiming()
    {

        //Arrange
        Tile[][] tilesInGame;
        int width = 10;
        int length = 10;
        BoardGenerationMultiThreading bgmt = new BoardGenerationMultiThreading();

        //Act
        long startTime = System.nanoTime();
        tilesInGame = bgmt.createField(width, length);
        long endTime = System.nanoTime();
        while(tilesInGame[6][9] == null)
        {

        }
        long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.

        //Assert
        assertEquals(true, duration < 100 );
    }
}