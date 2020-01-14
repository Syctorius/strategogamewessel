package controllers;

import interfaces.IUserLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.User;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField textboxPassword;

    @FXML
    private TextField textboxUsername;

    private IUserLogic userLogic;

    public LoginController(IUserLogic userLogic){
        this.userLogic = userLogic;
    }

    public void login(ActionEvent actionEvent) {
        String username = textboxUsername.getText();
        String password = textboxPassword.getText();


        if (userLogic.login(username, password)) {
            Stage currentStage = (Stage) textboxUsername.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/strategoboard.fxml"));
                fxmlLoader.setControllerFactory(c -> new StrategoControllerWessel(textboxUsername.getText()));
                loadFxml(fxmlLoader,"register");
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect username or password");
            alert.show();
        }
    }


    public void toRegister(ActionEvent actionEvent) {
        Stage currentStage = (Stage) textboxUsername.getScene().getWindow();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/register.fxml"));
            fxmlLoader.setControllerFactory(c -> {
                return new RegisterController(userLogic);
            });
           loadFxml(fxmlLoader,"register");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentStage.close();
    }
    private void loadFxml(FXMLLoader fxmlLoader, String title) throws IOException {
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 450, 450));
        stage.setMaximized(true);
        stage.show();
    }
}
