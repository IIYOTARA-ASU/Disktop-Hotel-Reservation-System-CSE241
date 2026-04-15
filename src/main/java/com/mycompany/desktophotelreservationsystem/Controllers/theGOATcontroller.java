package com.mycompany.desktophotelreservationsystem.Controllers; 

import java.io.IOException;

import com.mycompany.desktophotelreservationsystem.Admin;
import com.mycompany.desktophotelreservationsystem.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class theGOATcontroller {

    private Admin admin;
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label dynamicText; 
    boolean amenities = false;
    public theGOATcontroller() {}

    String s = "Baller";

    @FXML
    public void initialize() {
    	if (dynamicText != null) {
            dynamicText.setText("Welcome, " + s);
        } else {
            System.out.println("No dynamicText label found on this screen. Skipping text update.");
        }
    }
    
    @FXML
    public void toAmenities(ActionEvent e) {
    	  try {
			root = FXMLLoader.load(getClass().getResource("/adminAmenities.fxml"));
		  } catch (IOException e1) {
			// TODO Auto-generated catch block
			  System.out.println("Ballersssss");
			e1.printStackTrace();
		  }
    	  stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	  scene = new Scene(root);
    	  stage.setScene(scene);
    	  stage.show();    
    }
	
    @FXML
    public void toAdmin(ActionEvent e) {
    	  try {
			root = FXMLLoader.load(getClass().getResource("/theGoat.fxml"));
		  } catch (IOException e1) {
			// TODO Auto-generated catch block
			  System.out.println("Ballersssss");
			e1.printStackTrace();
		  }
    	  stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	  scene = new Scene(root);
    	  stage.setScene(scene);
    	  stage.show();    
    }
    @FXML
    public void up(ActionEvent e) {
        System.out.println("UP");
    }
}