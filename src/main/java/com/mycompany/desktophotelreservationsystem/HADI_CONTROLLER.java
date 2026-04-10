package com.mycompany.desktophotelreservationsystem;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class HADI_CONTROLLER {

    @FXML
    private ImageView myImageView; // Ensure fx:id="myImageView" is set in Scene Builder

    @FXML
    public void initialize() {
        if (myImageView != null) {
            // This allows the image to stretch to the corners
            myImageView.setPreserveRatio(false);

            // Manually set the size to match your GUI_testing math (x=70)
            myImageView.setFitWidth(1120);
            myImageView.setFitHeight(628);
        }
    }
}
