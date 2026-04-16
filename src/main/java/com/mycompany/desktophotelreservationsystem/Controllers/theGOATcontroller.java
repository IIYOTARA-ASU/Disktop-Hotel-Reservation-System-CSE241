package com.mycompany.desktophotelreservationsystem.Controllers; 

import java.io.IOException;
import java.util.InputMismatchException;

import com.mycompany.desktophotelreservationsystem.Admin;
import com.mycompany.desktophotelreservationsystem.Amenity;
import com.mycompany.desktophotelreservationsystem.DataBase;
import com.mycompany.desktophotelreservationsystem.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
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
    @FXML
    private Label amenityAddSuccess;
    @FXML
    private Label amenityDeleteMessage;
    @FXML
    private TextField amenityName;
    @FXML
    private TextField amenityPrice;
    @FXML
    private TextField deleteID;
    @FXML
    private FlowPane amenityContainer;
    
    boolean amenities = false;
    public theGOATcontroller() {}

    
    String s = "Baller";

    @FXML
    public void displayAmenities() {
    	int amenityID = 0;
    	amenityContainer.getChildren().clear();
    	
    	amenityContainer.setHgap(20); 
        amenityContainer.setVgap(20);
        amenityContainer.setPadding(new Insets(20));
        
    	for(Amenity a : DataBase.amenities) {
    		String amenityInfo = "ID : "+ amenityID + "\nName : "+a.getName() + "\nPrice : $"+a.getPrice();
    	
    		Label amenityLabel = new Label(amenityInfo);
    		
    		amenityLabel.setStyle("-fx-text-fill : beige; -fx-font-size : 16px; -fx-font-weight : bolder; -fx-padding : 10px; -fx-text-alignment : CENTER;");
    		
    		amenityContainer.getChildren().add(amenityLabel);
    		
    		amenityID++;
    	}
    }
    
    @FXML
    public void initialize() {
    	if (dynamicText != null) {
            dynamicText.setText("Welcome, " + s);
        } else {
            System.out.println("No dynamicText label found on this screen. Skipping text update.");
        }
    	if(amenityContainer != null) {
    	displayAmenities();
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
    public void toViewAmenities(ActionEvent e) {
    	loadScreen("/adminAmenitiesView.fxml",e);
    }
    public void toDeleteAmenities(ActionEvent e) {
    	loadScreen("/adminAmenitiesDelete.fxml",e);
    }
    @FXML
    public void addAmenity() {	
    	amenityAddSuccess.setText("");
    	String name = amenityName.getText().trim();
    	String priceString = amenityPrice.getText().trim();
    	int price = -1;
    	if(name.equals("")) {
    		amenityName.setStyle("-fx-border-color : red;");
    		amenityNameError.setText("You must enter a name!");
    	}else {
    		amenityName.setStyle("-fx-border-color : darkSlateGray;");
    		amenityNameError.setText("");
    	}
    	
    	if(priceString.equals("")) {
    		amenityPrice.setStyle("-fx-border-color : red;");
    		amenityPriceError.setText("You must enter a price!");    		
    	}
    	else {
	    	try {
	    		for (int i = 0; i < DataBase.amenities.size(); i++) {
	    			if (DataBase.amenities.get(i).getName().equalsIgnoreCase(name)) {
	    	    		amenityName.setStyle("-fx-border-color : red;");
	    	    		amenityNameError.setText("This amenity already exists!");
	    	    		amenityName.setText("");
	    				return;
	    			}
	    		}
	    		
	    		if(Integer.parseInt(priceString) < 0) {
		    		amenityPrice.setText("");
		    		amenityPriceError.setText("You must enter a valid number!");
		    		amenityPrice.setStyle("-fx-border-color : red;");
		    		return;
	    		}
	    		
	    		price = Integer.parseInt(priceString);
	 			
	 			
	    	}catch(Exception e) {
	    		if(!priceString.equals("")) {
	    		amenityPrice.setText("");
	    		amenityPriceError.setText("You must enter a valid number!");
	    		amenityPrice.setStyle("-fx-border-color : red;");
	    		}
	    		return;
	    	}
    		amenityPrice.setStyle("-fx-border-color : darkSlateGray;");
    		amenityPriceError.setText("");
    		if(!name.equals("") && !priceString.equals("") && price >= 0) {
 			DataBase.amenities.add(new Amenity(name, price));
 			amenityAddSuccess.setText(name + " Added Successfully!");
 			amenityPrice.setText("");
 			amenityName.setText("");
    		}
    	}

    	
    }
    
    @FXML
    public void deleteAmenity() {
    	int deletedId = -1;
    	
    	try {
    		deletedId = Integer.parseInt(deleteID.getText().trim());
    	}catch(Exception e){
    		deleteID.setStyle("-fx-border-color : red;");
    		amenityDeleteMessage.setText("Amenity not found. Please enter a valid ID.");
    		amenityDeleteMessage.setStyle("-fx-text-fill : red;");
    		deleteID.setText("");
    		return;
    	}
		amenityDeleteMessage.setText("");
		deleteID.setStyle("-fx-border-color : darkSlateGray;");

		if (deletedId < 0 || deletedId >= DataBase.amenities.size()) {
			deleteID.setStyle("-fx-border-color : red;");
    		amenityDeleteMessage.setText("Amenity not found. Please enter a valid ID.");
    		amenityDeleteMessage.setStyle("-fx-text-fill : red;");
    		deleteID.setText("");
    		return;
		}
		
		Amenity toDelete = DataBase.amenities.get(deletedId);
		for (int i = 0; i < DataBase.rooms.size(); i++) {
			DataBase.rooms.get(i).getAmenities().removeIf(a -> a.equals(toDelete));
		}
		DataBase.amenities.remove(toDelete);
		System.out.println("   [OK] Amenity deleted and removed from all rooms.");
		amenityDeleteMessage.setStyle("-fx-text-fill: green;");
		amenityDeleteMessage.setText("Amenity deleted and removed from all rooms!");
		deleteID.setStyle("-fx-border-color : darkSlateGray");
		deleteID.setText("");
		
		displayAmenities();
    }
}