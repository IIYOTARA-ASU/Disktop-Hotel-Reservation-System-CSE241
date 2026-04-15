package com.mycompany.desktophotelreservationsystem;

import Screens.LoginPage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class HADI_CONTROLLER {

    @FXML
    private Label recepname;
    public void setUsername(String recepname)
    {
        this.recepname.setText(recepname);
        System.out.println("recepname is copied in controller");
    }


    @FXML
    public void initialize() {
        // Now recepname will not be null
        if ( recepname!= null) {

        }
    }
}