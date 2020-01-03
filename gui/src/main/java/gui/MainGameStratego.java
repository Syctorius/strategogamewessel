package gui;

import controllers.RegisterController;
import controllers.StrategoControllerWessel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import restclient.RestClient;

public class MainGameStratego extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {




        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/register.fxml"));
    fxmlLoader.setControllerFactory(c ->{
        return new RegisterController(new RestClient()) ;
    });
    Parent root = fxmlLoader.load();
        primaryStage.setTitle("Test");
        primaryStage.setScene(new Scene(root, screen.getWidth(), screen.getHeight()));
        primaryStage.setFullScreen(false);
        primaryStage.setMaximized(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
