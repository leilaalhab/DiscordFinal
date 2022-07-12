package com.example.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

import static com.example.finalproject.ChatController.client;

public class ServerChatController {

    @FXML
    private ListView<?> listView;

    @FXML
    private TextField messageTf;

    @FXML
    void sendChannelFileButtonPressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Send File");
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            //اینارو باید ببینم لیلا چجوری برای پیام زده
            String channelIndex = "";
            String serverIndex = "";
            client.sendFile(channelIndex, serverIndex, file);
            client.sendChannelMessage(channelIndex, serverIndex, client.getUsername() + " sends " + file.getName() + " DOWNLOAD");
        }
    }

    @FXML
    void sendChannelMessageButtonPressed(ActionEvent event) {

    }

    @FXML
    void showChannelPinnedMessages(ActionEvent event) {

    }

}
