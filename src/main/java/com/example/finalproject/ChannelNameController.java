package com.example.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ChannelNameController {

    public Label channelIndex;
    @FXML
    private Label channelNameLabel;

    private static ServerController origin;

    public static void setOrigin(ServerController origin) {
        ChannelNameController.origin = origin;
    }

    @FXML
    void channelSettingButtonPressed(ActionEvent event) {

    }

    public void channelChosen(MouseEvent mouseEvent) throws IOException {
        origin.openChannel(channelIndex.getText());
    }
}