package gui;

import controllers.StrategoControllerWessel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainGameStratego extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {




        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/strategoboard.fxml"));
    fxmlLoader.setControllerFactory(c ->{
        return new StrategoControllerWessel() ;
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
