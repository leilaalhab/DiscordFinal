package com.example.finalproject;

import Client.Client;
import UserFeatures.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Popup;

public class StatusController {

    @FXML
    private Button dndButton;

    @FXML
    private Button idleButton;

    @FXML
    private Button invisibleButton;

    @FXML
    private Button onlineButton;

    private Popup popup;

    private static Client client;
    private static HomeController origin;

    public static void setOrigin(HomeController origin) {
        StatusController.origin = origin;
    }

    public static void setClient(Client client) {
        StatusController.client = client;
    }
    @FXML
    void dndButtonPressed(ActionEvent event) {
        client.setStatus(3);
        origin.setStatusPhoto();
        popup = (Popup) ((Node) event.getSource()).getScene().getWindow();
        popup.hide();
    }

    @FXML
    void idleButtonPressed(ActionEvent event) {
        client.setStatus(2);
        origin.setStatusPhoto();
        popup = (Popup) ((Node) event.getSource()).getScene().getWindow();
        popup.hide();
    }

    @FXML
    void invisibleButtonPressed(ActionEvent event) {
        client.setStatus(5);
        System.out.println(Status.values()[4]);
        origin.setStatusPhoto();
        popup = (Popup) ((Node) event.getSource()).getScene().getWindow();
        popup.hide();
    }

    @FXML
    void onlineButtonPressed(ActionEvent event) {
        client.setStatus(1);
        origin.setStatusPhoto();
        popup = (Popup) ((Node) event.getSource()).getScene().getWindow();
        popup.hide();
    }

}