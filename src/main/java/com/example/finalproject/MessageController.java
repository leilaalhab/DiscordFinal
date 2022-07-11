package com.example.finalproject;

import com.example.finalproject.DiscordApplication;
import com.example.finalproject.MessageOptionsController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.io.File;

import static com.example.finalproject.ChatController.client;

public class MessageController {

    public Label index;
    public Button laugh;
    public Button like;
    public Button dislike;

    @FXML
    private Circle profileCircle;

    @FXML
    private Label text;

    @FXML
    private Label usernameLabel;


    public void setAll(String username, String textField, File pfp) {
        usernameLabel.setText(username);
        text.setText(textField);
    }

    public void options(MouseEvent mouseEvent) {
        MessageOptionsController.setClient(client);
        MessageOptionsController.setMessageIndex(index.getText());
        DiscordApplication.showPopUp(getClass(), mouseEvent, "MessageOptionsView.fxml");
    }
}
