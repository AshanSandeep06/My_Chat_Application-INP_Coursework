package lk.ijse.socket.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.socket.util.Delta;
import lk.ijse.socket.util.OptionUtil;

import java.io.*;
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

    /*public TextArea txtMessageArea;*/

    public TextField txtMessage;
    public ImageView imgCameraIcon;
    public ImageView imgEmojiIcon;
    public VBox vBox;
    public Pane pane;
    Socket remoteSocket;
    String message = "";
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    /* 2022-08-14 -----> Added this codes */
    private FileChooser fileChooser;
    private File filePath;

    public void initialize() {
        lblClientName.setText(LoginFormController.clientUserName);
        clientUserName = LoginFormController.clientUserName;

        pane.setVisible(false);

        new Thread(() -> {
            try {
                remoteSocket = new Socket("localhost", PORT);

                dataInputStream = new DataInputStream(remoteSocket.getInputStream());
                dataOutputStream = new DataOutputStream(remoteSocket.getOutputStream());

                dataOutputStream.writeUTF(clientUserName);

                /* !message.equalsIgnoreCase("finish")  ---> While loop condition */
                while (true) {
                    message = dataInputStream.readUTF();

                    String[] ar = message.split(":-");
                    String s = ar[1].trim();
                    String firstThreeChars = "";
                    if(s.length()>3){
                        firstThreeChars = s.substring(0,3);
                    }

                    if(firstThreeChars.equalsIgnoreCase("img")){
                        String[] split = message.split(":");
                        String client1 = split[0].trim();

                        File file = new File(s.substring(3));
                        Image image = new Image(file.toURI().toString());
                        ImageView imageView = new ImageView(image);

                        imageView.setFitHeight(350);
                        imageView.setFitWidth(350);

                        HBox hBox = new HBox(10);
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);

                        if(clientUserName.equalsIgnoreCase(client1)){
                            hBox.setAlignment(Pos.BOTTOM_RIGHT);

                            Text text1 = new Text("Me :- ");
                            text1.setFont(new Font("BOLD",22));
                            hBox.getChildren().add(text1);
                            hBox.getChildren().add(imageView);

                        }else{
                            String sender = ar[0].trim();

                            vBox.setAlignment(Pos.TOP_LEFT);
                            hBox.setAlignment(Pos.TOP_LEFT);


                            Text text1=new Text(sender+" :- ");
                            text1.setFont(new Font("BOLD",22));
                            hBox.getChildren().add(text1);
                            hBox.getChildren().add(imageView);
                        }

                        Platform.runLater(() -> {
                            vBox.getChildren().add(hBox);
                        });

                    }else{
                        String[] split = message.split(":");
                        String client2 = split[0].trim();

                        if(clientUserName.equalsIgnoreCase(client2)){
                            HBox hBox = new HBox();
                            hBox.setAlignment(Pos.CENTER_RIGHT);

                            hBox.setPadding(new Insets(5, 5, 5, 10));
                            Text text = new Text("Me : "+split[1].trim());
                            text.setStyle("-fx-font-weight: bold");
                            text.setStyle("-fx-font-size: 20");
                            TextFlow textFlow = new TextFlow(text);
                            textFlow.setStyle("-fx-color: rgb(192,192,192);" + "-fx-background-color: rgb(15,125,242);" + " -fx-background-radius: 20px");

                            textFlow.setPadding(new Insets(5, 10, 5, 10));
                            text.setFill(Color.color(0.934, 0.945, 0.996));

                            hBox.getChildren().add(textFlow);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    vBox.getChildren().add(hBox);

                                    /*For take a space between messages*/
                                    HBox hBox1 = new HBox();
                                    hBox1.setAlignment(Pos.CENTER_LEFT);
                                    hBox1.setPadding(new Insets(5, 5, 5, 10));
                                    vBox.getChildren().add(hBox1);
                                }
                            });
                        }else{
                            HBox hBox = new HBox();
                            hBox.setAlignment(Pos.CENTER_LEFT);
                            hBox.setPadding(new Insets(5, 5, 5, 10));

                            Text text = new Text(message);
                            text.setStyle("-fx-font-weight: bold");
                            text.setStyle("-fx-font-size: 20");
                            TextFlow textFlow = new TextFlow(text);
                            textFlow.setStyle(" -fx-background-color: rgb(192,192,192);" + " -fx-background-radius: 22px");
                            textFlow.setPadding(new Insets(5, 10, 5, 10));
                            hBox.getChildren().add(textFlow);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    vBox.getChildren().add(hBox);

                                    /*For take a space between messages*/
                                    HBox hBox1 = new HBox();
                                    hBox1.setAlignment(Pos.CENTER_LEFT);
                                    hBox1.setPadding(new Insets(5, 5, 5, 10));
                                    vBox.getChildren().add(hBox1);
                                }
                            });
                        }
                    }

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
            dataOutputStream.writeUTF(LoginFormController.clientUserName + " :- finish");
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
                dataOutputStream.writeUTF(LoginFormController.clientUserName + " :- " + txtMessage.getText().trim());
                if (txtMessage.getText().equalsIgnoreCase("bye") || txtMessage.getText().equalsIgnoreCase("finish")) {
//                    txtMessageArea.appendText(txtMessage.getText().trim());
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
                dataOutputStream.writeUTF(LoginFormController.clientUserName + " :- " + txtMessage.getText().trim());
                if (txtMessage.getText().equalsIgnoreCase("bye") || txtMessage.getText().equalsIgnoreCase("finish")) {
//                    txtMessageArea.appendText(txtMessage.getText().trim());
                    System.exit(0);
                }
            }
            txtMessage.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendImagesOnAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);
//        System.out.println(filePath.getName());
        if(filePath!=null){
            dataOutputStream.writeUTF(LoginFormController.clientUserName+ " :- "+"img" + filePath.getPath());
        }
    }

    public void openEmojiPaneOnAction(MouseEvent mouseEvent) {
        pane.setVisible(!pane.isVisible());
    }

    public void emoji1OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\u1f642");
    }

    public void emoji2OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\u1f602");
    }

    public void emoji3OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\u1f60d");
    }

    public void emoji4OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\u1f61d");
    }

    public void emoji5OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\u1f634");
    }
}
