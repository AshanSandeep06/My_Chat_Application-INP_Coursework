package lk.ijse.socket.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.socket.util.Delta;
import lk.ijse.socket.util.OptionUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author : Ashan Sandeep
 * @since : 0.1.0
 **/

public class ClientFormController {
    public static String clientUserName = "";
    final Delta dragDelta = new Delta();
    private final int PORT = 5000;
    public AnchorPane mainContext;
    public AnchorPane context;
    public Label closeLabel;
    public ImageView imgClose;
    public Label minimizeLable;
    public Label lblClientName;
    public TextArea txtMessageArea;
    public TextField txtMessage;
    public ImageView imgCameraIcon;
    public ImageView imgEmojiIcon;
    Socket remoteSocket;
    String message = "";
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public void initialize() {
        lblClientName.setText(LoginFormController.clientUserName);
        clientUserName = LoginFormController.clientUserName;
        new Thread(() -> {
            try {
                remoteSocket = new Socket("localhost", PORT);

                dataInputStream = new DataInputStream(remoteSocket.getInputStream());
                dataOutputStream = new DataOutputStream(remoteSocket.getOutputStream());

                dataOutputStream.writeUTF(clientUserName);

                /* !message.equalsIgnoreCase("finish")  ---> While loop condition */
                while (true) {
                    message = dataInputStream.readUTF();
                    txtMessageArea.appendText(message + "\n\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
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

    public void closeMouseEnteredOnAction(MouseEvent mouseEvent) {
        OptionUtil.closeMouseEnter(closeLabel, imgClose);
    }

    public void closeMouseExitedOnAction(MouseEvent mouseEvent) {
        OptionUtil.closeMouseExit(closeLabel, imgClose);
    }

    public void minimizeMouseEnteredOnAction(MouseEvent mouseEvent) {
        OptionUtil.minimizeMouseEnter(minimizeLable);
    }

    public void minimizeMouseExitedOnAction(MouseEvent mouseEvent) {
        OptionUtil.minimizeMouseExit(minimizeLable);
    }

    public void minimizeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) context.getScene().getWindow();
        OptionUtil.minimizeOnAction(stage);
    }

    public void closeOnAction(MouseEvent mouseEvent) throws IOException {
        // To disconnect the client and will inform it to server
        try {
            dataOutputStream.writeUTF(LoginFormController.clientUserName + " : finish");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Stage stage = (Stage) context.getScene().getWindow();
            OptionUtil.closeOnAction(stage);
        }
    }

    public void txtMessagesSendOnAction(ActionEvent event) {
        try {
            if (!txtMessage.getText().isEmpty()) {
                dataOutputStream.writeUTF(LoginFormController.clientUserName + " : " + txtMessage.getText().trim());
                if (txtMessage.getText().equalsIgnoreCase("bye") || txtMessage.getText().equalsIgnoreCase("finish")) {
                    txtMessageArea.appendText(txtMessage.getText().trim());
                    System.exit(0);
                }
            }
            txtMessage.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void messagesSendOnAction(ActionEvent event) {
        try {
            if (!txtMessage.getText().isEmpty()) {
                dataOutputStream.writeUTF(LoginFormController.clientUserName + " : " + txtMessage.getText().trim());
                if (txtMessage.getText().equalsIgnoreCase("bye") || txtMessage.getText().equalsIgnoreCase("finish")) {
                    txtMessageArea.appendText(txtMessage.getText().trim());
                    System.exit(0);
                }
            }
            txtMessage.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendImagesOnAction(MouseEvent mouseEvent) {

    }

    public void sendEmojisOnAction(MouseEvent mouseEvent) {

    }
}
