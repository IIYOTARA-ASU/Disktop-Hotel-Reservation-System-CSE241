package com.mycompany.desktophotelreservationsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class HADI_CONTROLLER {

    @FXML
    private Label Adminid; // Added 'private' and ensured it matches fx:id

    User user = new User("hadi", "123");

    @FXML
    public void initialize() {
        // Now Adminid will not be null
        if (Adminid != null) {
            Adminid.setText(user.getUserName());
        }
    }
}