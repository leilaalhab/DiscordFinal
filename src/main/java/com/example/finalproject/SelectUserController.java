package com.example.finalproject;

import Client.Client;
import Handler.ResponseStatus;
import Model.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Popup;

//import javax.swing.*;
//import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class SelectUserController {

    @FXML
    private Label alertLabel;

    @FXML
    private TextField usernameTf;

    private static Client client;
    private static HomeController origin;

    public static void setOrigin(HomeController origin) {
        SelectUserController.origin = origin;
    }

    public static void setClient(Client client) {
        SelectUserController.client = client;
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        javafx.stage.Popup popup = (Popup) ((Node) event.getSource()).getScene().getWindow();
        popup.hide();
    }

    @FXML
    void doneButtonPressed(ActionEvent event) throws IOException {

        Response response = client.getPrivateChat(usernameTf.getText());
        if (response.getResponseStatus() == ResponseStatus.INVALID_USERNAME) {
            alertLabel.textFillProperty().set(Paint.valueOf("#c94141"));
            alertLabel.setText("This user does not exist.");
        }
        else if (response.getResponseStatus() == ResponseStatus.BLOCKED_USERNAME){
            alertLabel.textFillProperty().set(Paint.valueOf("#c94141"));
            alertLabel.setText("You cannot message this user.");
        }
        else {
            alertLabel.textFillProperty().set(Paint.valueOf("#2f9e35"));
            alertLabel.setText("Chat Successfully created.");
            client.enterPrivateChat(usernameTf.getText());
            origin.pending();
            cancelButtonPressed(event);
        }

    }

}
