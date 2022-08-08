package lk.ijse.socket.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

/**
 * @author : Ashan Sandeep
 * @since : 0.1.0
 **/

public class LoginFormController {
    public TextField txtUserName;
    public Button btnLogin;

    public static ObservableList<String> usersList = FXCollections.observableArrayList();

    public void initialize() {
        usersList.addAll("Sameera Gunawardena", "Nimal Perera", "Imali Nethushi");
    }

    public void keyReleasedOnAction(KeyEvent keyEvent) {
        if (!txtUserName.getText().isEmpty()) {
            if (txtUserName.getText().matches("^[A-z ]{3,25}$")) {
                txtUserName.setStyle("-fx-text-fill: BLACK");
                btnLogin.setDisable(false);
            }else{
                if (txtUserName.getText().length() > 0) {
                    txtUserName.setStyle("-fx-text-fill: RED");
                }else{
                    txtUserName.setStyle("-fx-text-fill: BLACK");
                }
                btnLogin.setDisable(true);
            }
        }else{
            txtUserName.setStyle("-fx-text-fill: BLACK");
            btnLogin.setDisable(true);
        }
    }

    public void chatLoginOnAction(ActionEvent event) {
        if (!txtUserName.getText().isEmpty()) {
            if (txtUserName.getText().matches("^[A-z ]{3,25}$")) {
                for (String userName : usersList) {
                    if (userName.equalsIgnoreCase(txtUserName.getText())) {
                        System.out.println("Login Successfully..!");
                        new Alert(Alert.AlertType.CONFIRMATION, "Successfully logged to the Group Chat ✔").show();
                        return;
                    }
                }
                new Alert(Alert.AlertType.WARNING, "This User doesn't exist").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid User name, Please try again.!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty field, Please enter a user name to proceed login..!").show();
        }
        clear();
    }

    public void cancelOnAction(ActionEvent event) {
        clear();
    }

    private void clear(){
        txtUserName.clear();
        btnLogin.setDisable(true);
    }
}
