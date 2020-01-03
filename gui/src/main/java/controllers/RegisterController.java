package controllers;

import interfaces.IUserLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import user.User;


import java.io.IOException;

public class RegisterController {
    @FXML
    public TextField tfRegisterUsername;
    public PasswordField tfRegisterPassword;
    public PasswordField tfRegisterConfirmPassword;
    public Button btnRegisterUser;
    public Button btnRegisterBack;


    private IUserLogic userLogic;

    public RegisterController(IUserLogic userLogic) {
        this.userLogic = userLogic;
    }


    public void Register(ActionEvent actionEvent) {
        String username = tfRegisterUsername.getText();
        String password = tfRegisterPassword.getText();

        if (username.equals("") || password.equals("") || username.equals(null) || password.equals(null)) {
            ShowUnsuccesfulRegisterMessage();
            ResetFields();

        } else if (tfRegisterPassword.getLength() < 5 || tfRegisterUsername.getLength() < 5) {
            ShowToShortMessage();
            ResetFields();
        } else if (!tfRegisterPassword.getText().equals(tfRegisterConfirmPassword.getText())) {
            ShowUnsuccesfulRegisterMessage();
            ResetFields();
        } else if (userLogic.register(username, password)) {
            ShowSuccesfulRegisterMessage(username);
          BackToLogin();
        } else {
            ShowUnsuccesfulRegisterMessage();
        }


    }

    private void ResetFields() {
        tfRegisterUsername.clear();
        tfRegisterPassword.clear();
        tfRegisterConfirmPassword.clear();
    }

    private void ShowSuccesfulRegisterMessage(String username) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration successful");
        alert.setHeaderText("Welcome " + username);
        alert.setContentText("Register successful, enjoy the game!");
        alert.showAndWait();
    }

    private void ShowUnsuccesfulRegisterMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Register unsuccessful");
        alert.setHeaderText("Register unsuccessful!");
        alert.setContentText("Register not successful, try another username!");
        alert.showAndWait();
    }

    private void ShowToShortMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Short info");
        alert.setHeaderText("Register unsuccessful!");
        alert.setContentText("Register not successful, try a longer username or password with atleast 5 characters");
        alert.showAndWait();
    }

    private void BackToLogin() {

            Stage currentStage = (Stage) tfRegisterConfirmPassword.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/strategoboard.fxml"));
                fxmlLoader.setControllerFactory(c -> {
                    return new StrategoControllerWessel();
                });
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Lobby list");
                stage.setScene(new Scene(root, 450, 450));
                stage.setMaximized(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentStage.close();
        }



    public void BackToLoginAction(ActionEvent actionEvent) {

      /*  Stage currentStage = (Stage) tfRegisterConfirmPassword.getScene().getWindow();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
            fxmlLoader.setControllerFactory(c -> {
                return new Controller((IUserLogic) Factory.createInstance(new CreateUserLiveInstance()));
            });
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("login");
            stage.setScene(new Scene(root, 450, 450));
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentStage.close();*/
    }
}