package com.example.finalproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerController implements Initializable {

    @FXML
    private ListView<Pane> channelsListView;

    private ObservableList<Pane> channels = FXCollections.observableArrayList();

    @FXML
    private Pane contentPane;

    @FXML
    private Circle profileCircle;

    @FXML
    private Label serverName;

    @FXML
    private Circle statusCircle;

    @FXML
    private Label usernameLabel;

    @FXML
    void addChannel(ActionEvent event) {

    }

    @FXML
    void changeStatus(ActionEvent event) {

    }

    @FXML
    void settingButtonPressed(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Pane pane = FXMLLoader.load(getClass().getResource("ChannelNameView.fxml"));
            Label channelName = (Label) ((HBox)pane.getChildren().get(0)).getChildren().get(0);
            channelName.setText("# General");
            channels.add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

        channelsListView.setItems(channels);
    }
}
