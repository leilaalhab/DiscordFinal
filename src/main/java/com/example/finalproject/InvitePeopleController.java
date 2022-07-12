package com.example.finalproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class InvitePeopleController implements Initializable {

    @FXML
    private ListView<Pane> friendsListView;

    private ObservableList<Pane> friends = FXCollections.observableArrayList();

    @FXML
    void close(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //add friends to list if the friend is in the server make disable its invite button and set its text to "sent"
    }
}
