package lk.ijse.socket.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;

/**
 * @author : Ashan Sandeep
 * @since : 0.1.0
 **/

public class LoginFormController {
    public TextField txtUserName;
    public Button btnLogin;

    public static ObservableList<String> usersList = FXCollections.observableArrayList();
    public Label closeLabel;
    public Label minimizeLable;
    public AnchorPane context;
    public ImageView imgClose;

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

    public void minimizeMouseEnteredOnAction(MouseEvent mouseEvent) {
        minimizeLable.setStyle("-fx-background-color: #E5E5E5");
    }

    public void minimizeMouseExitedOnAction(MouseEvent mouseEvent) {
        minimizeLable.setStyle("-fx-background-color: #FFFF");
    }

    public void closeMouseEnteredOnAction(MouseEvent mouseEvent) {
        closeLabel.setStyle("-fx-background-color: #E81123");
        imgClose.setImage(new Image("lk/ijse/socket/assets/images/close2.png"));
    }

    public void closeMouseExitedOnAction(MouseEvent mouseEvent) {
        closeLabel.setStyle("-fx-background-color: #FFFF");
        imgClose.setImage(new Image("lk/ijse/socket/assets/images/close.png"));
    }

    public void minimizeOnAction(MouseEvent mouseEvent) {
        Stage window = (Stage) context.getScene().getWindow();
        window.setIconified(true);
    }

    public void closeOnAction(MouseEvent mouseEvent) {
        Stage window = (Stage) context.getScene().getWindow();
        //window.close();
        System.exit(0);
    }
}
