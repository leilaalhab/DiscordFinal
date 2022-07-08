package com.example.finalproject.myAccount;

import Client.Client;
import UserFeatures.Status;
import com.example.finalproject.DiscordApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyAccountController implements Initializable {


    @FXML private Circle statusCircle;
    @FXML private Button PhoneNumberButton;
    @FXML private Button changePasswordButton;
    @FXML private Circle circle;
    @FXML private Button editEmailButton;
    @FXML private Button editUsernameButton;
    @FXML private Label emailLabel;
    @FXML private Button escButton;
    @FXML private Button logOutButton;
    @FXML private Label phoneNumberLabel;
    @FXML private Label profileUsernameLabel;
    @FXML private Label usernameLabel;
    private static Client client;

    private String profilePhotoPath = "/src/main/resources/com/example/finalproject/discordLogo.png";
    private DiscordApplication application = new DiscordApplication();



    public static void setClient(Client client) {
        MyAccountController.client = client;
    }

    private void setStatusPhoto(){
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


    public void updateEmail(String changeEmail) {
        if (changeEmail != null)
            emailLabel.setText(changeEmail);
    }

    public void updatePhone(String phone) {
        if (phone != null)
            phoneNumberLabel.setText(phone);
    }

    public void updateUsername(String username) {
        if (username != null)
            usernameLabel.setText(username);
    }

    private void setPhoneNumberLabel(){
        String phoneNumber = client.getPhone();
        if (phoneNumber != null) {
            phoneNumberLabel.setText(phoneNumber);
        }
    }

    private void setEmail() {
        emailLabel.setText(client.getEmail());
    }

    private void setUsernameLabel(){
       usernameLabel.setText(client.getUsername());
    }

    private void setProfileUsernameLabel() {
       profileUsernameLabel.setText(client.getUsername());
    }

    private void setProfilePhoto() throws URISyntaxException {
        if (client.getPFP(client.getUsername()) == null) {
            Image image = new Image(new File(System.getProperty("user.dir") + profilePhotoPath).toURI().toString());
            circle.setFill(new ImagePattern(image));
            //client.getUser().setPfp(new File(profilePhotoPath));
        } else {
            setProfilePhoto(client.getPFP(client.getUsername()));
        }
    }

    private void setProfilePhoto(File file) throws URISyntaxException {
        Image image = new Image(file.toURI().toString());
        circle.setFill(new ImagePattern(image));
    }

    @FXML
    void editUsernameButtonPressed(ActionEvent event) {
        ChangeUsernameController.setClient(client);
        ChangeUsernameController.setOrigin(this);
        application.showPopUp(event, "ChangeUsernameView.fxml");
    }

    @FXML
    void editEmailButtonPressed(ActionEvent event) {
        ChangeEmailController.setClient(client);
        ChangeEmailController.setOrigin(this);
        application.showPopUp(event, "ChangeEmailView.fxml");
    }

    @FXML
    void PhoneNumberButtonPressed(ActionEvent event) {
        ChangePhoneNumberController.setClient(client);
        ChangePhoneNumberController.setOrigin(this);
        application.showPopUp(event, "ChangePhoneNumberView.fxml");
    }

    @FXML
    void changePasswordButtonPressed(ActionEvent event) {
        ChangePasswordController.setClient(client);
        ChangePasswordController.setOrigin(this);
        application.showPopUp(event, "ChangePasswordView.fxml");
    }

    @FXML
    void editProfileButtonPressed(ActionEvent event) throws URISyntaxException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("image", "*.png", "*.jpg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Profile Photo");
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            client.setPFP(file);
            setProfilePhoto(file);
        }
    }

    @FXML
    void escButtonPressed(ActionEvent event) throws IOException {
      application.changeScene("welcomeView.fxml");
    }

    @FXML
    void logOutButtonPressed(ActionEvent event) throws IOException{
       application.changeScene("SignInView.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPhoneNumberLabel();
        setUsernameLabel();
        setProfileUsernameLabel();
        setEmail();
        setStatusPhoto();
        try {
            setProfilePhoto();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
