package com.example.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Popup;

public class CreateServerController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button doneButton;

    @FXML
    private TextField serverNameTf;

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Popup popup = (Popup) ((Node) event.getSource()).getScene().getWindow();
        popup.hide();
    }

    @FXML
    void doneButtonPressed(ActionEvent event) {

    }

}
