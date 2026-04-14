package com.mycompany.desktophotelreservationsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class HADI_CONTROLLER {

    @FXML
    private Label recepname;
    // Added 'private' and ensured it matches fx:id


    @FXML
    public void initialize() {
        // Now Adminid will not be null
        if ( recepname!= null) {
           recepname.setText(DataBase.people.get(DataBase.people.size()).getUserName());
        }
    }
}