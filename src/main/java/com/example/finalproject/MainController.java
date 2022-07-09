package com.example.finalproject;

import Client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private Pane borderPane;

    @FXML
    private ListView<?> serversListView;

    private ObservableList<?> servers = FXCollections.observableArrayList();

    private DiscordApplication discordApplication = new DiscordApplication();

    private static Client client;

    public static void setClient(Client client) {
        MainController.client = client;
    }

    @FXML
    void homeButtonPressed(ActionEvent event) throws IOException {

        HomeController.setClient(client);
        BorderPane.setAlignment(borderPane, Pos.TOP_LEFT);
        BorderPane newPane = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
        borderPane.getChildren().add(newPane);
    }

    @FXML
    public void addServerButtonPressed(ActionEvent event) {
        //add server
        DiscordApplication.showPopUp(getClass(), event, "CreateServerView.fxml");
    }
}
