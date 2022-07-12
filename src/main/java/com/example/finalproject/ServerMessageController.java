package com.example.finalproject;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;

import java.io.IOException;

import static com.example.finalproject.ChatController.client;

public class ServerMessageController extends MessageController{

    @Override
    public void showDownloadPopUp(MouseEvent mouseEvent) throws IOException {
        String msg = ((Label)mouseEvent.getSource()).getText();
        System.out.println(msg);

        if (!msg.endsWith("DOWNLOAD"))
            return;
        HBox hBox = FXMLLoader.load(getClass().getResource("downloadPopUpView.fxml"));
        RingProgressIndicator rpi = new RingProgressIndicator();

        //از لیلا بگیرم
        String channelIndex = "";
        String serverIndex = "";
        long size = client.downloadFile(msg.substring(msg.indexOf("sends")+6,msg.lastIndexOf("DOWNLOAD")-1),channelIndex,serverIndex);

        download(mouseEvent, hBox, rpi, size);
    }
}
