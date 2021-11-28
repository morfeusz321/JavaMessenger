package com.example.messclient;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket socket) {
        try{
            this.socket = socket;
            bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (IOException e){
            System.out.println("Error while creating a Client");
            e.printStackTrace();
            closeEverything(socket,bufferedWriter,bufferedReader);
        }
    }
    public void sendMessageToServer(String messageToServer) {
        try {
            bufferedWriter.write(messageToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while sending message to Client.");
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    public void recieveMessageFromServer(VBox vBox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String messageFromServer = bufferedReader.readLine();
                        HelloController.addLabel(messageFromServer, vBox);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error while recieving message form Client.");
                        closeEverything(socket, bufferedWriter, bufferedReader);
                        break;
                    }
                }
            }
        }).start();
    }
    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }

        } catch (IOException e) {
            System.out.println("Error while closing server.");
            e.printStackTrace();
        }
    }
}
