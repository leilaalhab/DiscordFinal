package com.example.finalproject;

import Client.Client;
import Handler.ResponseStatus;
import UserFeatures.Status;
import UserFeatures.User;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class FriendsController implements Initializable {

    @FXML
    private ListView<Pane> blockedListView;

    @FXML
    private ListView<Pane> onlineListView;

    @FXML
    private ListView<Pane> pendingListView;

    @FXML
    private ListView<Pane> allListView;

    @FXML
    private Label status;

    @FXML
    private TextField usernameTf;

    private static Client client;

    private ObservableList<Pane> friendRequests = FXCollections.observableArrayList();
    private ObservableList<Pane> allFriends = FXCollections.observableArrayList();
    private ObservableList<Pane> onlineFriends = FXCollections.observableArrayList();
    private ObservableList<Pane> blockedFriends = FXCollections.observableArrayList();

    private String profilePhotoPath = "/src/main/resources/com/example/finalproject/discordLogo.png";

    public static void setClient(Client client) {
        FriendsController.client = client;
    }

    @FXML
    void sendFriendRequestButtonPressed(ActionEvent event) {
        ResponseStatus response = client.sendFriendRequest(usernameTf.getText());
        if (response == ResponseStatus.FRIEND_REQUEST_TO_FRIEND)
            DiscordApplication.showPopUp(getClass(), event, "DuplicateFriendView.fxml");
        else if (response == ResponseStatus.INVALID_USERNAME)
            DiscordApplication.showPopUp(getClass() ,event, "FriendRequestFailedView.fxml");
        else if (response == ResponseStatus.DUPLICATE_FRIEND_REQUEST)
            DiscordApplication.showPopUp(getClass() ,event, "DuplicateRequestView.fxml");
        else if (response == ResponseStatus.VALID_FRIEND_REQUEST) {
            DiscordApplication.showPopUp(getClass(), event, "FriendRequestSuccessfulView.fxml");
        }
    }

//    private void showFriendRequests(Label usernameLabel) throws URISyntaxException {
//        String requests = client.getFriendRequests();
//        System.out.println(requests);
//        if (requests.equals("Empty\n"))
//            return;
//        String[] usernames = requests.split(" ");
//        for (String username : usernames) {
////            System.out.println("[" +username+ "]");
//
//            username = username.stripTrailing();
//
//            usernameLabel.setText(username);
//            User user = client.getUser(username);
//
//        }
//    }


    public void pending(){
        pendingListView.getItems().clear();
        String requests = client.getFriendRequests();
        if (requests.equals("Empty\n"))
            return;

        String[] usernames = requests.split(" ");

        for (String username : usernames) {
            username = username.stripTrailing();
            System.out.println("[" +username+ "]");
            User user = client.getUser(username);
            try {
                Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ReceivedFriendRequestView.fxml")));
                Circle profileCircle = (Circle) pane.getChildren().get(0);
                if (user.getPfp() == null) {
                    Image image = new Image(new File(System.getProperty("user.dir") + profilePhotoPath).toURI().toString());
                    profileCircle.setFill(new ImagePattern(image));
                    //client.getUser().setPfp(new File(profilePhotoPath));
                } else {
                    profileCircle.setFill(new ImagePattern(new Image(user.getPfp().getAbsolutePath())));
                }

                Circle statusCircle = (Circle) pane.getChildren().get(1);
                Status status = client.getStatus();
                String path = "";
                if (status == Status.ONLINE) {
                    statusCircle.setFill(Color.GREEN);
                }
                else if (status == Status.OFFLINE)
                    path = "/src/main/resources/com/example/finalproject/invisibleStatus.png";
                else if (status == Status.IDLE)
                    path = "/src/main/resources/com/example/finalproject/idleStatus.png";
                else if (status == Status.DND)
                    path = "/src/main/resources/com/example/finalproject/dndStatus.png";

                if (!path.equals("")) {
                    Image image = new Image(new File(System.getProperty("user.dir") + path).toURI().toString());
                    statusCircle.setFill(new ImagePattern(image));
                }

                Label usernamel = (Label) pane.getChildren().get(2);
                usernamel.setText(user.getUsername());
                friendRequests.add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void friends(){
        for (User user : client.getFriendsArrayList()) {
            boolean isOnline = false;
            try {
                Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UserView.fxml")));
                pane.setPrefWidth(640);
                Circle profileCircle = (Circle) pane.getChildren().get(0);
                if (user.getPfp() == null) {
                    Image image = new Image(new File(System.getProperty("user.dir") + profilePhotoPath).toURI().toString());
                    profileCircle.setFill(new ImagePattern(image));
                    //client.getUser().setPfp(new File(profilePhotoPath));
                } else {
                    profileCircle.setFill(new ImagePattern(new Image(user.getPfp().getAbsolutePath())));
                }

                Circle statusCircle = (Circle) pane.getChildren().get(1);
                Status status = client.getStatus();
                String path = "";
                if (status == Status.ONLINE) {
                    statusCircle.setFill(Color.GREEN);
                    isOnline = true;
                }
                else if (status == Status.OFFLINE)
                    path = "/src/main/resources/com/example/finalproject/invisibleStatus.png";
                else if (status == Status.IDLE)
                    path = "/src/main/resources/com/example/finalproject/idleStatus.png";
                else if (status == Status.DND)
                    path = "/src/main/resources/com/example/finalproject/dndStatus.png";

                if (!path.equals("")) {
                    Image image = new Image(new File(System.getProperty("user.dir") + path).toURI().toString());
                    statusCircle.setFill(new ImagePattern(image));
                }

                Label username = (Label) pane.getChildren().get(2);
                username.setText(user.getUsername());
                allFriends.add(pane);
                if (isOnline)
                    onlineFriends.add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void blocked(){
        for (User user : client.getBlockedHashSet()) {
            try {
                Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UserView.fxml")));
                Circle profileCircle = (Circle) pane.getChildren().get(0);
                if (user.getPfp() == null) {
                    Image image = new Image(new File(System.getProperty("user.dir") + profilePhotoPath).toURI().toString());
                    profileCircle.setFill(new ImagePattern(image));
                    //client.getUser().setPfp(new File(profilePhotoPath));
                } else {
                    profileCircle.setFill(new ImagePattern(new Image(user.getPfp().getAbsolutePath())));
                }

                Circle statusCircle = (Circle) pane.getChildren().get(1);
                Status status = client.getStatus();
                String path = "";
                if (status == Status.ONLINE) {
                    statusCircle.setFill(Color.GREEN);
                }
                else if (status == Status.OFFLINE)
                    path = "/src/main/resources/com/example/finalproject/invisibleStatus.png";
                else if (status == Status.IDLE)
                    path = "/src/main/resources/com/example/finalproject/idleStatus.png";
                else if (status == Status.DND)
                    path = "/src/main/resources/com/example/finalproject/dndStatus.png";

                if (!path.equals("")) {
                    Image image = new Image(new File(System.getProperty("user.dir") + path).toURI().toString());
                    statusCircle.setFill(new ImagePattern(image));
                }

                Label username = (Label) pane.getChildren().get(2);
                username.setText(user.getUsername());
                blockedFriends.add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReceivedFriendRequest.setClient(client);
        ReceivedFriendRequest.setOrigin(this);
//        client.acceptFriendRequest("melika");
        pending();
//        friends();
//        blocked();


//        friendRequests.addListener(new InvalidationListener() {
//            @Override
//            public void invalidated(Observable observable) {
//                pending();
//                System.out.println("yes");
//            }
//        });


        pendingListView.setItems(friendRequests);
        allListView.setItems(allFriends);
        onlineListView.setItems(onlineFriends);
        blockedListView.setItems(blockedFriends);
    }
}
