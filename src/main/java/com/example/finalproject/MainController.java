package com.example.finalproject;

import Client.Client;
import UserFeatures.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private Pane borderPane;

    @FXML
    private ListView<?> serversListView;

    private ObservableList<Pane> servers = FXCollections.observableArrayList();

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
        CreateServerController.setOrigin(this);
        DiscordApplication.showPopUp(getClass(), event, "CreateServerView.fxml");
    }

    public void update() {
            serversListView.getItems().clear();
            String allServers = client.printServers();
            System.out.println(allServers);
            if (allServers.equals("Empty\n"))
                return;

//            String[] serverNames = allServers.split("\n");
//
//            for (String server : serverNames) {
//                server = server.stripTrailing();
//                server = server.substring(server.indexOf("]"));
//                try {
//                    Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UserView.fxml")));
//                    pane.setPrefWidth(225);
//                    Circle profileCircle = (Circle) pane.getChildren().get(0);
//                    if (client.getPFP(server) == null) {
//                        Image image = new Image(new File(System.getProperty("user.dir") + "/src/main/resources/com/example/finalproject/discordLogo.png").toURI().toString());
//                        profileCircle.setFill(new ImagePattern(image));
//                    } else {
//                        Image image = new Image(client.getPFP(server).toURI().toString());
//                        profileCircle.setFill(new ImagePattern(image));
//                    }
//
//                    Circle statusCircle = (Circle) pane.getChildren().get(1);
//                    Status status = client.getStatus(server);
//                    String path = "";
//                    if (status == Status.ONLINE) {
//                        statusCircle.setFill(Color.GREEN);
//                    }
//                    else if (status == Status.OFFLINE)
//                        path = "/src/main/resources/com/example/finalproject/invisibleStatus.png";
//                    else if (status == Status.IDLE)
//                        path = "/src/main/resources/com/example/finalproject/idleStatus.png";
//                    else if (status == Status.DND)
//                        path = "/src/main/resources/com/example/finalproject/dndStatus.png";
//
//                    if (!path.equals("")) {
//                        Image image = new Image(new File(System.getProperty("user.dir") + path).toURI().toString());
//                        statusCircle.setFill(new ImagePattern(image));
//                    }
//
//                    Label usernamel = (Label) pane.getChildren().get(2);
//                    usernamel.setText(server);
//                    servers.add(pane);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

        }


}
