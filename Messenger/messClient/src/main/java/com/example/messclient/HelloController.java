package com.example.messclient;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Button sendButton;

    @FXML
    private TextField textFieldMassage;
    @FXML
    private VBox vBoxMassages;
    @FXML
    private ScrollPane spMain;
    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            client = new Client(new Socket("localhost", 1234));
            System.out.println("Connected to server.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        vBoxMassages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                spMain.setVvalue((double) t1);
            }
        });
        client.recieveMessageFromServer(vBoxMassages);
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String messageToSend = textFieldMassage.getText();
                if (!messageToSend.isEmpty()) {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle("-fx-color: rgb(239,242,255); " + "-fx-background-color: rgb(15,125,242);" + "-fx-background-radius: 20px;");
                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.color(0.934, 0.945, 0.96));
                    hBox.getChildren().add(textFlow);
                    vBoxMassages.getChildren().add(hBox);

                    client.sendMessageToServer(messageToSend);
                    textFieldMassage.clear();
                }
            }
        });
    }
    public static void addLabel(String messageFromClient,VBox vbox){
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5,5,10));

        Text text = new Text(messageFromClient);
        TextFlow textFlow=new TextFlow(text);

        textFlow.setStyle("-fx-background-color: rgb(233,233,235);"+"-fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(5, 10,5,10));
        hBox.getChildren().add(textFlow);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hBox);
            }
        });
    }}