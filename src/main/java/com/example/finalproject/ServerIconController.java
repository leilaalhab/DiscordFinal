package com.example.finalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ServerIconController implements Initializable {

    @FXML
    private Circle serverCircle;

    @FXML
    void settingButtonPressed(ActionEvent event) {
        DiscordApplication.showPopUp(getClass(), event, "ServerSettingMenuView.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Random random = new Random();
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        serverCircle.setFill(Color.rgb(red, green, blue));
    }
}
