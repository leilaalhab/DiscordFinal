package com.example.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

import static com.example.finalproject.welcome.SignInController.client;


public class ServerChatController {

    public Label serverIndex;
    public Label channelIndex;

    @FXML
    private ListView<?> listView;

    @FXML
    private TextField messageTf;

    private static ServerController origin;


    public static void setOrigin(ServerController origin) {
        ServerChatController.origin = origin;
    }

    @FXML
    void sendChannelFileButtonPressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Send File");
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            client.sendFile(channelIndex.getText(), serverIndex.getText(), file);
            client.sendChannelMessage(channelIndex.getText(), serverIndex.getText(), client.getUsername()+ ":" + client.getUsername()+ " sends " + file.getName() + " DOWNLOAD");
        }
    }

    @FXML
    void sendChannelMessageButtonPressed(ActionEvent event) throws IOException {
        client.sendChannelMessage(channelIndex.getText(), serverIndex.getText(), client.getUsername() + ":" +messageTf.getText());
        origin.openChannel( channelIndex.getText());
    }

    @FXML
    void showChannelPinnedMessages(ActionEvent event) {

    }

}
