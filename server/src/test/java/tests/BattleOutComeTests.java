package tests;

import enums.BattleOutcome;
import enums.Rank;
import helpers.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleOutComeTests {
    @Test
    public void battleOutcomeWinTest()
    {
        //Arrange
        Piece piece = new Piece(Rank.MARSHAL);
        Piece piece2 = new Piece(Rank.CAPTAIN);
        //Act
        //Assert
       assertEquals(BattleOutcome.WIN,piece.winFight(piece2));

    }
    @Test
    public void battleOutcomeLoseTest()
    {
        //Arrange
        Piece piece = new Piece(Rank.MARSHAL);
        Piece piece2 = new Piece(Rank.CAPTAIN);
        //Act
        //Assert
        assertEquals(BattleOutcome.LOSE,piece2.winFight(piece));

    }
    @Test
    public void battleOutcomeTieTest()
    {
        //Arrange
        Piece piece = new Piece(Rank.CAPTAIN);
        Piece piece2 = new Piece(Rank.CAPTAIN);
        //Act
        //Assert
        assertEquals(BattleOutcome.TIE,piece2.winFight(piece));

    }
    @Test
    public void battleOutcomeWincondition1()
    {
        //Arrange
        Piece piece = new Piece(Rank.SPY);
        Piece piece2 = new Piece(Rank.MARSHAL);
        //Act
        //Assert
        assertEquals(BattleOutcome.WIN,piece.winFight(piece2));

    }  @Test
    public void battleOutcomeLoseCondition()
    {
        //Arrange
        Piece piece = new Piece(Rank.LIEUTENANT);
        Piece piece2 = new Piece(Rank.BOMB);
        //Act
        //Assert
        assertEquals(BattleOutcome.LOSE,piece.winFight(piece2));

    }
    @Test
    public void battleOutcomeWincondition2()
    {
        //Arrange
        Piece piece2 = new Piece(Rank.BOMB);
        Piece piece = new Piece(Rank.MINER);
        //Act
        //Assert
        assertEquals(BattleOutcome.WIN,piece.winFight(piece2));

    }
    @Test
    public void battleOutcomeWinconditionFlag()
    {
        //Arrange
        Piece piece2 = new Piece(Rank.BOMB);
        Piece piece3 = new Piece(Rank.CAPTAIN);
        Piece piece4 = new Piece(Rank.SCOUT);
        Piece piece1 = new Piece(Rank.MINER);
        Piece piece5 = new Piece(Rank.MAJOR);
        Piece piece6 = new Piece(Rank.GENERAL);
        Piece piece7 = new Piece(Rank.COLONEL);
        Piece piece8 = new Piece(Rank.LIEUTENANT);
        Piece piece9 = new Piece(Rank.SPY);
        Piece piece10 = new Piece(Rank.MARSHAL);
        Piece piece11 = new Piece(Rank.SERGEANT);
        Piece piece12 = new Piece(Rank.FLAG);

        //Act
        //Assert
        assertEquals(BattleOutcome.GAMEDONE,piece1.winFight(piece12));
        assertEquals(BattleOutcome.GAMEDONE,piece2.winFight(piece12));
        assertEquals(BattleOutcome.GAMEDONE,piece3.winFight(piece12));
        assertEquals(BattleOutcome.GAMEDONE,piece4.winFight(piece12));
        assertEquals(BattleOutcome.GAMEDONE,piece5.winFight(piece12));
        assertEquals(BattleOutcome.GAMEDONE,piece6.winFight(piece12));
        assertEquals(BattleOutcome.GAMEDONE,piece7.winFight(piece12));
        assertEquals(BattleOutcome.GAMEDONE,piece8.winFight(piece12));
        assertEquals(BattleOutcome.GAMEDONE,piece9.winFight(piece12));
        assertEquals(BattleOutcome.GAMEDONE,piece10.winFight(piece12));
        assertEquals(BattleOutcome.GAMEDONE,piece11.winFight(piece12));
        assertEquals(BattleOutcome.GAMEDONE,piece12.winFight(piece12));


    }

}
