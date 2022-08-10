package lk.ijse.socket.controller;

import javafx.scene.control.Alert;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

                if(msg.equalsIgnoreCase("finish")){
                    break;
                }
                displayMessagesToAllClients(output);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void displayMessagesToAllClients(String outputMessage) throws IOException {
        for (ClientHandler c1 : clientHandlersList) {
            if (!String.valueOf(c1.localSocket.getPort()).equals(String.valueOf(localSocket.getPort()))) {
                c1.dataOutputStream.writeUTF(outputMessage);
            }
        }
    }
}
