package tests;

import enums.GameStatus;
import helpers.StrategoGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.LoginService;
import user.User;

import javax.ws.rs.core.Response;

public class playerTests {
    private static LoginService loginService;


    @BeforeAll
    public static void init()
    {
        loginService = new LoginService(true);
        loginService.register(new User("wesss","wesss"));
    }
    @Test() // expected=IllegalArgumentException.class
    void testRegisterPlayerNameNull() {

        // Register player with parameter name null in single-player mode
        //Arrange
        String name = null;
        String password = "password";
        int responseStatus = 400;
        boolean singlePlayerMode = true;
        //Act
       Response response =  loginService.register(new User(name,password));
       //Assert
        Assertions.assertEquals(responseStatus,response.getStatus());
    }
    @Test() // expected=IllegalArgumentException.class
    void testRegisterPlayerNameEmpty() {

        // Register player with parameter name null in single-player mode
        String name = "";
        String password = "password";
        boolean singlePlayerMode = true;
        int responseStatus = 400;
        Response response =  loginService.register(new User(name,password));
        //Assert
        Assertions.assertEquals(responseStatus,response.getStatus());
    }

    @Test
    void testRegisterPlayerPasswordNull(){
        String name = "name";
        String password = null;
        boolean singlePlayerMode = true;
        int responseStatus = 400;
        Response response =  loginService.register(new User(name,password));
        //Assert
        Assertions.assertEquals(responseStatus,response.getStatus());
    }

    @Test
    void testRegisterPlayerPasswordEmpty(){
        String name = "name";
        String password = "";
        boolean singlePlayerMode = true;

        int responseStatus = 400;
        Response response =  loginService.register(new User(name,password));
        //Assert
        Assertions.assertEquals(responseStatus,response.getStatus());
    }


    @Test
    void successfulLogin(){
        String name = "nameSuccess";
        String password = "password";

        int responseStatus = 200;
        Response response =  loginService.register(new User(name,password));
        //Assert
        Assertions.assertEquals(responseStatus,response.getStatus());
    }

    @Test
    void testSinglePlayerBadLogin(){
        String name = "nameSingle";
        String password = "password";
        boolean singlePlayerMode = true;

        int responseStatus = 400;
        Response response =  loginService.login(new User(name,password));
        //Assert
        Assertions.assertEquals(responseStatus,response.getStatus());
     //   Assertions.assertEquals(GameStatus.SETUP,game.getStatus());
    }

    @Test
    void testRegisterAndLogin(){
        String name = "namePie";
        String password = "password";


        boolean singlePlayerMode = false;

        int responseStatus = 200;
        Response response =  loginService.register(new User(name,password));
        Assertions.assertEquals(responseStatus,response.getStatus());
         response =  loginService.login(new User(name,password));
        Assertions.assertEquals(responseStatus,response.getStatus());

       // Assertions.assertEquals(GameStatus.SETUP,game.getStatus());
      //  Assertions.assertEquals(GameStatus.SETUP,game.getStatus());
    }



    @Test
    void testNameAlreadyRegistered(){
        String name = "namePie2";
        String password = "password";


        boolean singlePlayerMode = false;

        int responseStatus = 200;
        Response response =  loginService.register(new User(name,password));
        Assertions.assertEquals(responseStatus,response.getStatus());
        response =  loginService.register(new User(name,password));
        responseStatus = 400;
        Assertions.assertEquals(responseStatus,response.getStatus());
    }

}
