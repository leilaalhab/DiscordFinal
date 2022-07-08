package com.example.finalproject.myAccount;

import Client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Popup;

public class ChangePhoneNumberController {

    @FXML
    private Button cancelButton;

    @FXML
    private Label changePhoneNumberLabel;

    @FXML
    private Button doneButton;

    @FXML
    private TextField phoneNumberTf;

    @FXML
    private Label phoneStatus;

    private static Client client;
    private static MyAccountController origin;

    public static void setOrigin(MyAccountController origin) {
        ChangePhoneNumberController.origin = origin;
    }

    public static void setClient(Client client) {
        ChangePhoneNumberController.client = client;
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Popup popup = (Popup) ((Node) event.getSource()).getScene().getWindow();
        popup.hide();
    }

    @FXML
    void doneButtonPressed(ActionEvent event) {
        if(checkPhone()) {
            if (client.changePhone(phoneNumberTf.getText())) {
                origin.updatePhone(phoneNumberTf.getText());
                cancelButtonPressed(event);
            }
        }
    }

    public boolean checkPhone() {
        if (phoneNumberTf.getText().equals("")) {
            phoneStatus.textFillProperty().set(Paint.valueOf("#c94141"));
            phoneStatus.setText("You must enter a phone number.");
            return false;
        }
        else if (phoneNumberTf.getText().matches("(^\\d{10,11}+$|^$|^\\s$)")) {
            phoneStatus.textFillProperty().set(Paint.valueOf("#2f9e35"));
            phoneStatus.setText("Valid Phone Number.");
            return true;
        } else {
            phoneStatus.textFillProperty().set(Paint.valueOf("#c94141"));
            phoneStatus.setText("Invalid Phone Number.");
            return false;
        }
    }

}
