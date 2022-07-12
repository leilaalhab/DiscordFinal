package com.example.finalproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateRoleController implements Initializable {

    @FXML
    public VBox vBox;

    @FXML
    private Label alertLabel;

    @FXML
    private TextField roleNameTf;

    @FXML
    private VBox permissionsVBox;

    private SwitchButton changeServerNameSB = new SwitchButton();
    private SwitchButton addChannelSB = new SwitchButton();
    private SwitchButton removeChannelSB = new SwitchButton();
    private SwitchButton banMembersSB = new SwitchButton();
    private SwitchButton pinMessagesSB = new SwitchButton();
    private SwitchButton removeMemberSB = new SwitchButton();

    private void addSB(int index, SwitchButton switchButton){
        ((HBox) vBox.getChildren().get(index)).getChildren().add(switchButton);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addSB(4, changeServerNameSB);
        addSB(6, addChannelSB);
        addSB(8, removeChannelSB);
        addSB(10, banMembersSB);
        addSB(12, pinMessagesSB);
        addSB(14, removeMemberSB);
    }
}
