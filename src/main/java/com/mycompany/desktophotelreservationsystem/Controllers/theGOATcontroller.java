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
    @FXML
    private Label amenityNameError;
    @FXML
    private Label amenityPriceError;
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
    	
    	if(amenityNameError != null) {
    		amenityNameError.setText("Enter name");
    	}
    	
    	if(amenityPriceError != null) {
    		amenityPriceError.setText("Enter price");
    	}
    }
    
    @FXML void loadScreen(String path, ActionEvent e) {
  	  try {
			root = FXMLLoader.load(getClass().getResource(path));
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
    public void toAmenities(ActionEvent e) {
    	loadScreen("/adminAmenities.fxml",e);
    }
	
    @FXML
    public void toAdmin(ActionEvent e) {
    	loadScreen("/theGoat.fxml",e);
    }
    
    @FXML
    public void toRooms(ActionEvent e) {
    	loadScreen("/adminRooms.fxml",e);
    }
    
    @FXML
    public void toRoomTypes(ActionEvent e) {
    	loadScreen("/adminRoomTypes.fxml",e);
    }
    
    @FXML
    public void toAddAmenities(ActionEvent e) {
    	loadScreen("/adminAmenitiesAdd.fxml",e);
    }
    @FXML
    public void up(ActionEvent e) {
        System.out.println("UP");
    }
    @FXML
    public void addAmenity() {
    	
    }
}