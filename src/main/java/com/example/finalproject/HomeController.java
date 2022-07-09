package com.example.finalproject;

import Client.Client;
import UserFeatures.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Circle profileCircle;

    @FXML
    private Circle statusCircle;

    @FXML
    private Label usernameLabel;

    @FXML
    private Pane contentPane;

    DiscordApplication discordApplication = new DiscordApplication();

    private static Client client;

    public static void setClient(Client client) {
        HomeController.client = client;
    }

    @FXML
    void addPrivateChatPressed(ActionEvent event) {
        DiscordApplication.showPopUp(getClass(), event, "AddPrivateChatView.fxml");
    }

    @FXML
    void changeStatus(ActionEvent event) {
        StatusController.setClient(client);
        StatusController.setOrigin(this);
        DiscordApplication.showPopUp(getClass(), event, "StatusView.fxml");
    }

    @FXML
    void settingButtonPressed(ActionEvent event) throws IOException {
        MyAccountController.setClient(client);
        discordApplication.changeScene("MyAccountView.fxml");
    }

    @FXML
    void friendsButtonPressed(ActionEvent event) {
        Pane newPane = null;
        FriendsController.setClient(client);
//        FriendsController.setOrigin(this);
        try {
            FriendsController.setClient(client);
            newPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FriendsView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        contentPane.getChildren().add(newPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUsernameLabel();
        setStatusPhoto();
        ReceivedFriendRequest.setClient(client);
        try {
            setProfilePhoto();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void setUsernameLabel(){
        usernameLabel.setText(client.getUsername());
    }


    private void setProfilePhoto() throws URISyntaxException {
        if (client.getPFP(client.getUsername()) == null) {
            Image image = new Image(new File(System.getProperty("user.dir") + "/src/main/resources/com/example/finalproject/discordLogo.png").toURI().toString());
            profileCircle.setFill(new ImagePattern(image));
        } else {
            setProfilePhoto(client.getPFP(client.getUsername()));
        }
    }

    public void setStatusPhoto(){
        Status status = client.getStatus();
        String path = "";
        path = "/src/main/resources/com/example/finalproject/invisibleStatus.png";
        if (status == Status.ONLINE) {
            statusCircle.setFill(Color.GREEN);
            return;
        }
        else if (status == Status.OFFLINE)
            path = "/src/main/resources/com/example/finalproject/invisibleStatus.png";
        else if (status == Status.IDLE)
            path = "/src/main/resources/com/example/finalproject/idleStatus.png";
        else if (status == Status.DND)
            path = "/src/main/resources/com/example/finalproject/dndStatus.png";

        Image image = new Image(new File(System.getProperty("user.dir") + path).toURI().toString());
        statusCircle.setFill(new ImagePattern(image));
    }

    private void setProfilePhoto(File file) throws URISyntaxException {
        Image image = new Image(file.toURI().toString());
        profileCircle.setFill(new ImagePattern(image));
    }
}
