package lk.ijse.socket.controller;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lk.ijse.socket.util.Delta;
import lk.ijse.socket.util.OptionUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : Ashan Sandeep
 * @since : 0.1.0
 **/

public class ServerFormController {
    public Label closeLabel;
    public ImageView imgClose;
    public Label minimizeLable;
    public AnchorPane context;
    public AnchorPane mainContext;
    public TextArea textArea;

    final Delta dragDelta = new Delta();

    private ServerSocket serverSocket;
    private final int PORT = 5000;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket localSocket;
    private String clientMessage = "";

    public void initialize(){
        Thread t1 = new Thread(() -> {
            try{
                serverSocket = new ServerSocket(PORT);
                textArea.appendText("Server Started\n");

                localSocket = serverSocket.accept();
                textArea.appendText("Client Accepted..!\n");

                dataInputStream = new DataInputStream(localSocket.getInputStream());
                dataOutputStream = new DataOutputStream(localSocket.getOutputStream());

                while(!clientMessage.equalsIgnoreCase("finish")){
                    clientMessage = dataInputStream.readUTF();
                    textArea.appendText(clientMessage+"\n");
                }

            }catch (IOException e){
                e.printStackTrace();
            }
        });
        t1.start();
    }

    public void closeMouseEnteredOnAction(MouseEvent mouseEvent) {
        OptionUtil.closeMouseEnter(closeLabel, imgClose);
    }

    public void minimizeMouseEnteredOnAction(MouseEvent mouseEvent) {
        OptionUtil.minimizeMouseEnter(minimizeLable);
    }

    public void closeMouseExitedOnAction(MouseEvent mouseEvent) {
        OptionUtil.closeMouseExit(closeLabel, imgClose);
    }

    public void minimizeMouseExitedOnAction(MouseEvent mouseEvent) {
        OptionUtil.minimizeMouseExit(minimizeLable);
    }

    public void closeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) context.getScene().getWindow();
        OptionUtil.closeOnAction(stage);
    }

    public void minimizeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) context.getScene().getWindow();
        OptionUtil.minimizeOnAction(stage);
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
