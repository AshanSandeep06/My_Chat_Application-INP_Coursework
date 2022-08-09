package lk.ijse.socket.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
import lk.ijse.socket.util.Delta;
import lk.ijse.socket.util.OptionUtil;

import java.io.IOException;
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
    public AnchorPane mainContext;

    final Delta dragDelta = new Delta();

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

    public void chatLoginOnAction(ActionEvent event) throws IOException {
        if (!txtUserName.getText().isEmpty()) {
            if (txtUserName.getText().matches("^[A-z ]{3,25}$")) {
                for (String userName : usersList) {
                    if (userName.equalsIgnoreCase(txtUserName.getText())) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Successfully logged to the Group Chat ✔").show();
                        Stage stage = (Stage) mainContext.getScene().getWindow();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ClientForm.fxml"))));
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
        OptionUtil.minimizeMouseEnter(minimizeLable);
    }

    public void minimizeMouseExitedOnAction(MouseEvent mouseEvent) {
        OptionUtil.minimizeMouseExit(minimizeLable);
    }

    public void closeMouseEnteredOnAction(MouseEvent mouseEvent) {
        OptionUtil.closeMouseEnter(closeLabel, imgClose);
    }

    public void closeMouseExitedOnAction(MouseEvent mouseEvent) {
        OptionUtil.closeMouseExit(closeLabel, imgClose);
    }

    public void minimizeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) context.getScene().getWindow();
        OptionUtil.minimizeOnAction(stage);
    }

    public void closeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) context.getScene().getWindow();
        OptionUtil.closeOnAction(stage);
    }

    public void mouseDraggedOnAction(MouseEvent mouseEvent) {
        Stage window = (Stage) mainContext.getScene().getWindow();
        window.setX(mouseEvent.getScreenX() + dragDelta.x);
        window.setY(mouseEvent.getScreenY() + dragDelta.y);
    }

    public void mousePressedOnAction(MouseEvent mouseEvent) {
        Stage window = (Stage) mainContext.getScene().getWindow();
        dragDelta.x = window.getX() - mouseEvent.getScreenX();
        dragDelta.y = window.getY() - mouseEvent.getScreenY();
    }
}
