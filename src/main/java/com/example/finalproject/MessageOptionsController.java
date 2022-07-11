package com.example.finalproject;

import Client.Client;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Popup;

import static com.example.finalproject.ChatController.username;

public class MessageOptionsController {
    private static Client client;
    private static String messageIndex;

    public static void setClient(Client client) {
        MessageOptionsController.client = client;
    }

    public static void setMessageIndex(String messageIndex) {
        MessageOptionsController.messageIndex = messageIndex;
    }

    public void replyMessage(ActionEvent actionEvent) {
        goBack(actionEvent);
    }

    public void laughMessage(ActionEvent actionEvent) {
        client.reactToMessage("1", messageIndex, username);
        goBack(actionEvent);
    }

    public void dislikeMessage(ActionEvent actionEvent) {
        client.reactToMessage("2", messageIndex, username);
        goBack(actionEvent);
    }

    public void likeMessage(ActionEvent actionEvent) {
        client.reactToMessage("3", messageIndex, username);
        goBack(actionEvent);
    }

    public void goBack(ActionEvent actionEvent) {
        Popup popup = (Popup) ((Node) actionEvent.getSource()).getScene().getWindow();
        popup.hide();
    }
}
