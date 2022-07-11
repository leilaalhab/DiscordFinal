package com.example.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Popup;

public class ServerSettingMenuController {

    @FXML
    void createChannel(ActionEvent event) {

    }

    @FXML
    void invitePeople(ActionEvent event) {

    }

    @FXML
    void serverSettings(ActionEvent event) {

    }


    public void goBack(ActionEvent event) {
        Popup popup = (Popup) ((Node) event.getSource()).getScene().getWindow();
        popup.hide();
    }
}
