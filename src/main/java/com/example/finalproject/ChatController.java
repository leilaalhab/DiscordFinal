package com.example.finalproject;

import Client.Client;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private ListView<Pane> listView;

    @FXML
    private TextField messageTf;



    public static ObservableList<Pane> messages;
    public static Client client;
    public static String username;
    public static HomeController origin;
    public static File myPfp;
    public static File chatPfp;

    public static void setChatPfp(File chatPfp) {
        ChatController.chatPfp = chatPfp;
    }

    public static void setMyPfp(File myPfp) {
        ChatController.myPfp = myPfp;
    }

    public static void setUsername(String username) {
        ChatController.username = username;
    }

    public static void setClient(Client client) {
        ChatController.client = client;
    }

    public static void setOrigin(HomeController origin) {
        ChatController.origin = origin;
    }

    @FXML
    void sendFileButtonPressed(ActionEvent event) {

    }

    @FXML
    void sendMessageButtonPressed(ActionEvent event) throws IOException {
        client.sendPrivateChatMessage(username, messageTf.getText());
        messageTf.setText("");
        origin.openChat(client.getUsername(), username, myPfp, chatPfp);
    }

    public static void addMessage(Pane pane) {
        messages.add(pane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(messages);
    }
}
