package com.example.finalproject;

import Client.Client;
import UserFeatures.Status;
import com.example.finalproject.account.MyAccountController;
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

    @FXML
    public ListView<Pane> privateChatsListView;

    private ObservableList<Pane> privateChats = FXCollections.observableArrayList();

    DiscordApplication discordApplication = new DiscordApplication();

    private static Client client;

    public static void setClient(Client client) {
        HomeController.client = client;
    }

    public void pending(){
        privateChatsListView.getItems().clear();
        String requests = client.PrivateChatUsernames();
        if (requests.equals("Empty\n"))
            return;

        String[] usernames = requests.split("\n");

        for (String username : usernames) {
            username = username.stripTrailing();
            try {
                Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UserView.fxml")));
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
                privateChats.add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void addPrivateChatPressed(ActionEvent event) {
        SelectUserController.setClient(client);
        SelectUserController.setOrigin(this);
        DiscordApplication.showPopUp(getClass(), event, "SelectUserView.fxml");
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
        try {
            FriendsController.setClient(client);
            newPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FriendsView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        contentPane.getChildren().setAll(newPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUsernameLabel();
        setStatusPhoto();
        pending();
        UserController.setOrigin(this);
        ChatController.setClient(client);
        ChatController.setOrigin(this);

        privateChatsListView.setItems(privateChats);
        ReceivedFriendRequest.setClient(client);
        try {
            setProfilePhoto();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void openChat(String myUsername, String chatUsername, File myPFP, File chatPFP) throws IOException {
        String allMessages = (String) client.enterPrivateChat(chatUsername).getData();
        ChatController.setUsername(chatUsername);
        if (allMessages.equals("Empty")) {
            contentPane.getChildren().setAll((Pane)FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ChatView.fxml"))));
            return;
        }

        Pane chatPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ChatView.fxml")));
        String[] messages = allMessages.split("\n");
        ListView<Pane> panes = (ListView<Pane>)((VBox)chatPane.getChildren().get(0)).getChildren().get(0);
        ObservableList<Pane> observableList = FXCollections.observableArrayList();

        panes.setItems(null);
        for (String message : messages) {
            System.out.println(message);

            Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MessageView.fxml")));
            Circle profileCircle = (Circle) pane.getChildren().get(0);
            char messageIndex = message.charAt(0);

            ((Label)((VBox)pane.getChildren().get(1)).getChildren().get(0)).setText(Character.toString(messageIndex));

            MessageOptionsController.setOrigin(this);
            String laughs = client.getLaughs(Character.toString(messageIndex), chatUsername);
            String likes = client.getLikes(Character.toString(messageIndex), chatUsername);
            String dislikes = client.getDislikes(Character.toString(messageIndex), chatUsername);
            int numLaugh = laughs.split("\n").length;
            int numLike = likes.split("\n").length;
            int numDislike = dislikes.split("\n").length;

            if (laughs.equals(""))
                numLaugh--;
            if (likes.equals(""))
                numLike--;
            if (dislikes.equals(""))
                numDislike--;

            System.out.println(laughs);
            System.out.println(numLaugh);

            ((Button)((HBox)pane.getChildren().get(2)).getChildren().get(0)).setText("😂 " + numLaugh);
            ((Button)((HBox)pane.getChildren().get(2)).getChildren().get(1)).setText("👍 " + numLike);
            ((Button)((HBox)pane.getChildren().get(2)).getChildren().get(2)).setText("👎 " + numDislike);
            String userString = message.substring(0, message.indexOf(":"));

            if (userString.contains(chatUsername))  {
                if (chatPFP == null){
                    Image image = new Image(new File(System.getProperty("user.dir") + "/src/main/resources/com/example/finalproject/discordLogo.png").toURI().toString());
                    profileCircle.setFill(new ImagePattern(image));
                } else {
                    Image image = new Image(chatPFP.toURI().toString());
                    profileCircle.setFill(new ImagePattern(image));
                }
                Label usernamel = (Label) ((Label)((VBox)pane.getChildren().get(1)).getChildren().get(0));
                usernamel.setText(chatUsername);
            } else {
                if (myPFP == null) {
                    Image image = new Image(new File(System.getProperty("user.dir") + "/src/main/resources/com/example/finalproject/discordLogo.png").toURI().toString());
                    profileCircle.setFill(new ImagePattern(image));
                } else {
                    Image image = new Image(myPFP.toURI().toString());
                    profileCircle.setFill(new ImagePattern(image));
                }
                Label usernamel = ((Label)((VBox)pane.getChildren().get(1)).getChildren().get(0));
                usernamel.setText(myUsername);
            }
            message = message.substring(message.indexOf(":") + 1);
            Label text = ((Label)((VBox)pane.getChildren().get(1)).getChildren().get(1));
            ((Label)((VBox)pane.getChildren().get(1)).getChildren().get(2)).setText(Character.toString(messageIndex));
            text.setText(message);
            observableList.add(pane);
        }
        panes.setItems(observableList);
        contentPane.getChildren().setAll(chatPane);
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
