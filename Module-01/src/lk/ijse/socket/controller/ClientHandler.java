package lk.ijse.socket.controller;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author : Ashan Sandeep
 * @since : 0.1.0
 **/

public class ClientHandler extends Thread {
    String msg = "";
    private final Socket localSocket;
    private final ArrayList<ClientHandler> clientHandlersList;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    /*// for, client input message
    private String input = "";*/
    // This is for, send messages to all the clients
    private String output = "";


    public ClientHandler(Socket localSocket, ArrayList<ClientHandler> clientHandlersList) {
        this.localSocket = localSocket;
        this.clientHandlersList = clientHandlersList;
    }

    @Override
    public void run() {
        try {
            dataInputStream = new DataInputStream(localSocket.getInputStream());
            dataOutputStream = new DataOutputStream(localSocket.getOutputStream());

            while (true) {
                output = dataInputStream.readUTF();
                String[] split = output.split(":");
                msg = split[1].trim();
                /*System.out.println("MSG : "+msg);*/

                if(msg.equalsIgnoreCase("finish") || msg.equalsIgnoreCase("bye")){
                    for (ClientHandler c1 : clientHandlersList) {
                        if(c1.localSocket == this.localSocket){
                            /* ---- To notified to the server which client was disconnected ---- */
                            ServerFormController.staticTextArea.appendText("\n"+split[0]+" "+"has left the chat\n");
                            clientHandlersList.remove(c1);
                            break;
                        }
                    }

                    displayMessagesToAllClients(output);
                    break;
                }

                displayMessagesToAllClients(output);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayMessagesToAllClients(String outputMessage) throws IOException {
        for (ClientHandler c1 : clientHandlersList) {
            c1.dataOutputStream.writeUTF(outputMessage);
        }
    }
}
