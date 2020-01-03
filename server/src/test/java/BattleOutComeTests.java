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

}
