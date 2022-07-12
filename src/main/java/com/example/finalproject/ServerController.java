package com.example.finalproject;

import UserFeatures.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.finalproject.welcome.SignInController.client;

public class ServerController implements Initializable {

    public ListView<Pane> UsersListView;
    @FXML
    private ListView<Pane> channelsListView;

    private ObservableList<Pane> channels = FXCollections.observableArrayList();
    private ObservableList<Pane> members = FXCollections.observableArrayList();

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

    public static String serverIndex;
    public static String name;

    public static void setName(String name) {
        ServerController.name = name;
    }

    public static void setServerIndex(String serverIndex) {
        ServerController.serverIndex = serverIndex;
    }

    @FXML
    void addChannel(ActionEvent event) {
        System.out.println(serverIndex);
    }

    @FXML
    void changeStatus(ActionEvent event) {

    }

    @FXML
    void settingButtonPressed(ActionEvent event) {

    }

    public void openChannel(String channelIndex) throws IOException {
        Pane chatPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ServerChatView.fxml")));

        ((Label)chatPane.getChildren().get(1)).setText(serverIndex);
        ((Label)chatPane.getChildren().get(2)).setText(channelIndex);

        String allMessages = client.printChannelMessages(serverIndex, channelIndex);
        System.out.println(allMessages);
        if (allMessages.equals("Empty")) {
            contentPane.getChildren().setAll(chatPane);
            return;
        }
        String[] messages = allMessages.split("\n");
        ListView<Pane> panes = (ListView<Pane>)((VBox)chatPane.getChildren().get(0)).getChildren().get(0);
        ObservableList<Pane> observableList = FXCollections.observableArrayList();

        panes.setItems(null);
        for (String message : messages) {
            String userString = message.substring(0, message.indexOf(":"));

            Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MessageServerView.fxml")));

            Circle profileCircle = (Circle) pane.getChildren().get(0);
            String messageIndex = userString.substring(0, userString.indexOf("]"));
            String chatUsername = userString.substring(userString.indexOf("]") + 1);
            ((Label)((VBox)pane.getChildren().get(1)).getChildren().get(0)).setText(messageIndex);

            String laughs = client.getServerLaughs(messageIndex, serverIndex, channelIndex);
            String likes = client.getServerLikes(messageIndex, serverIndex, channelIndex);
            String dislikes = client.getServerDislikes(messageIndex, serverIndex, channelIndex);
            int numLaugh = laughs.split("\n").length;
            int numLike = likes.split("\n").length;
            int numDislike = dislikes.split("\n").length;

            if (laughs.equals(""))
                numLaugh--;
            if (likes.equals(""))
                numLike--;
            if (dislikes.equals(""))
                numDislike--;

            ((Button)((HBox)pane.getChildren().get(2)).getChildren().get(0)).setText("😂 " + numLaugh);
            ((Button)((HBox)pane.getChildren().get(2)).getChildren().get(1)).setText("👍 " + numLike);
            ((Button)((HBox)pane.getChildren().get(2)).getChildren().get(2)).setText("👎 " + numDislike);

            File chatPFP = client.getPFP(chatUsername);

            if (chatPFP == null){
                Image image = new Image(new File(System.getProperty("user.dir") + "/src/main/resources/com/example/finalproject/discordLogo.png").toURI().toString());
                profileCircle.setFill(new ImagePattern(image));
            } else {
                Image image = new Image(chatPFP.toURI().toString());
                profileCircle.setFill(new ImagePattern(image));
            }
            Label usernamel = (Label)(((VBox)pane.getChildren().get(1)).getChildren().get(0));
            usernamel.setText(chatUsername);


            message = message.substring(message.indexOf(":") + 1);
            Label text = ((Label)((VBox)pane.getChildren().get(1)).getChildren().get(1));
            ((Label)((VBox)pane.getChildren().get(1)).getChildren().get(2)).setText(messageIndex);
            text.setText(message);
            ((Label)(pane.getChildren().get(3))).setText(serverIndex);
            ((Label)(pane.getChildren().get(4))).setText(channelIndex);
            observableList.add(0,pane);
        }
        panes.setItems(observableList);
        contentPane.getChildren().setAll(chatPane);
    }

    private void updateChannels() {
        channelsListView.getItems().clear();
        String allChannels = client.viewChannels(serverIndex);
        System.out.println(allChannels);
        if (allChannels.equals("This server has been deleted."))
            return;
        String[] allChannelsSplit = allChannels.split("\n");
        for (String serverName : allChannelsSplit) {
            try {
                Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ChannelNameView.fxml")));
                Label channelName = (Label)((HBox) pane.getChildren().get(0)).getChildren().get(0);
                Label label = (Label) pane.getChildren().get(1);
                channelName.setText("#" + (serverName.substring(serverName.indexOf("]") + 1)));
                String index = serverName.substring(0, serverName.indexOf("]"));
                label.setText(index);
                channels.add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateMembers() {
        UsersListView.getItems().clear();
        String allMembers = client.getMembers(serverIndex);
        System.out.println(allMembers);
        String[] allMembersSplit = allMembers.split("\n");
        for (String username : allMembersSplit) {
            username = username.stripTrailing();
            try {
                Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ServerMemberView.fxml")));
                pane.setPrefWidth(225);
                Circle profileCircle = (Circle) pane.getChildren().get(0);
                if (client.getPFP(username) == null) {
                    Image image = new Image(new File(System.getProperty("user.dir") + "/src/main/resources/com/example/finalproject/discordLogo.png").toURI().toString());
                    profileCircle.setFill(new ImagePattern(image));
                } else {
                    Image image = new Image(client.getPFP(username).toURI().toString());
                    profileCircle.setFill(new ImagePattern(image));
                }
                Circle statusCircle = (Circle) pane.getChildren().get(1);
                Status status = client.getStatus(username);
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
                usernamel.setText(username);
                members.add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            HomeController.setProfilePhoto(profileCircle);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ChannelNameController.setOrigin(this);
        HomeController.setStatusPhoto(statusCircle);
        HomeController.setUsernameLabel(usernameLabel);
        ServerChatController.setOrigin(this);
        ServerMessageOptionsController.setClient(client);
        ServerMessageOptionsController.setOrigin(this);
        serverName.setText(name);
        updateChannels();
        updateMembers();

        channelsListView.setItems(channels);
        UsersListView.setItems(members);
    }

    public void serverSettingButtonPressed(ActionEvent actionEvent) {

    }
}