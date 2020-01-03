import enums.GameStatus;
import helpers.StrategoGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class playerTests {
   static StrategoGame game;
   static StrategoGame game2;

    @BeforeAll
    public static void init()
    {
        game = new StrategoGame();
        game2 = new StrategoGame();
    }
    @Test() // expected=IllegalArgumentException.class
    void testRegisterPlayerNameNull() {

        // Register player with parameter name null in single-player mode
        String name = null;
        String password = "password";
        boolean singlePlayerMode = true;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            game.registerPlayer(name, password, singlePlayerMode);
        });
    }
    @Test() // expected=IllegalArgumentException.class
    void testRegisterPlayerNameEmpty() {

        // Register player with parameter name null in single-player mode
        String name = "";
        String password = "password";
        boolean singlePlayerMode = true;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            game.registerPlayer(name, password, singlePlayerMode);
        });
    }

    @Test
    void testRegisterPlayerPasswordNull(){
        String name = "name";
        String password = null;
        boolean singlePlayerMode = true;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            game.registerPlayer(name, password, singlePlayerMode);
        });
    }

    @Test
    void testRegisterPlayerPasswordEmpty(){
        String name = "name";
        String password = "";
        boolean singlePlayerMode = true;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            game.registerPlayer(name, password, singlePlayerMode);
        });
    }

    @Test
    void testApplicationIsNull(){
        String name = "name";
        String password = "password";
        boolean singlePlayerMode = true;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            game.registerPlayer(name, password, singlePlayerMode);
        });
    }

    @Test
    void testNumberExceedsOneSinglePlayerMode(){
        String name = "name";
        String password = "password";

        String name2 = "name2";
        String password2 = "password2";

        boolean singlePlayerMode = true;

        game.registerPlayer(name, password, singlePlayerMode);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            game.registerPlayer(name2, password2, singlePlayerMode);
        });
    }

    @Test
    void testSinglePlayerMode(){
        String name = "name";
        String password = "password";
        boolean singlePlayerMode = true;

        game.registerPlayer(name, password, singlePlayerMode);

        Assertions.assertEquals(GameStatus.SETUP,game.getStatus());
    }

    @Test
    void testMultiPlayerMode(){
        String name = "name";
        String password = "password";

        String name2 = "name2";
        String password2 = "password2";

        boolean singlePlayerMode = false;

        game.registerPlayer(name, password, singlePlayerMode);
        game.registerPlayer(name2, password2, singlePlayerMode);

        Assertions.assertEquals(GameStatus.SETUP,game.getStatus());
        Assertions.assertEquals(GameStatus.SETUP,game.getStatus());
    }

    @Test
    void testNumberExceedsTwoMultiPlayerMode(){
        String name = "name";
        String password = "password";

        String name2 = "name2";
        String password2 = "password2";

        boolean singlePlayerMode = false;



        game.registerPlayer(name, password,singlePlayerMode);
        game.registerPlayer(name2, password2,singlePlayerMode);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            game.registerPlayer("test", "pswd", singlePlayerMode);
        });
    }

    @Test
    void testNameAlreadyRegistered(){
        String name = "name";
        String password = "password";

        boolean singlePlayerMode = false;

        game.registerPlayer(name, password, singlePlayerMode);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            game.registerPlayer(name, password, singlePlayerMode);
        });
    }

}
