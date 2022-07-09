package com.example.finalproject;

import Client.Client;
import com.example.finalproject.DiscordApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;


import java.io.IOException;
import java.net.Socket;

public class WelcomeController {

    private static Client client;
    private DiscordApplication application = new DiscordApplication();

    @FXML
    private Button signIn;
    @FXML
    private Label wrongLogIn;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public void connectClient() {
        if (client == null) {
            Socket socket;
            try {
                socket = new Socket("localhost", 8888);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Client client = new Client(socket);
            this.client = client;
        }
    }

    public void signUpButtonPressed(ActionEvent event) throws IOException {
        connectClient();
        SignUpController.setClient(client);
        application.changeScene("SignUpView.fxml");
    }

    public void signInButtonPressed(ActionEvent event) throws IOException {
        connectClient();
        SignInController.setClient(client);
        application.changeScene("SignInView.fxml");
    }

    public void aboutButtonPressed(ActionEvent event) throws IOException {
    }


    public void exitButtonPressed(ActionEvent event) throws IOException {
        System.exit(0);
    }

}