package com.mycompany.desktophotelreservationsystem.Controllers; 

import java.io.IOException;
import java.util.InputMismatchException;

import com.mycompany.desktophotelreservationsystem.Admin;
import com.mycompany.desktophotelreservationsystem.Amenity;
import com.mycompany.desktophotelreservationsystem.DataBase;
import com.mycompany.desktophotelreservationsystem.RoomType;
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
    private Label roomTypeDeleteMessage;
    @FXML
    private Label amenityDeleteMessage;
    @FXML
    private Label updateIDError;
    @FXML
    private Label updateSuccessMessage;
    @FXML
    private Label updatePriceError;
    @FXML
    private Label updateNameError;
    @FXML
    private Label roomTypeNameMessage;
    @FXML
    private TextField amenityName;
    @FXML
    private TextField roomTypeName;
    @FXML
    private TextField amenityPrice;
    @FXML
    private TextField deleteID;
    @FXML
    private TextField updateID;
    @FXML
    private TextField updatePrice;
    @FXML
    private TextField updateName;
    @FXML
    private FlowPane amenityContainer;
    @FXML
    private FlowPane roomTypeContainer;
    
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
    		
    		amenityLabel.
    		setStyle("-fx-text-fill: beige; -fx-font-size: 15px; -fx-font-weight: bold; " +
                    "-fx-background-color: #333; -fx-padding: 10; -fx-background-radius: 10; " +
                    "-fx-text-alignment: center; -fx-min-width: 120;");
    		amenityContainer.getChildren().add(amenityLabel);
    		
    		amenityID++;
    	}
    }
    
    @FXML
    public void displayRoomTypes() {
    	int roomTypeID = 0;
    	roomTypeContainer.getChildren().clear();
    	
    	roomTypeContainer.setHgap(20); 
        roomTypeContainer.setVgap(20);
        roomTypeContainer.setPadding(new Insets(20));
        
    	for(RoomType r : DataBase.roomTypes) {
    		String roomTypeInfo = "ID : "+ roomTypeID + "\nName : "+r.getRoomType();
    	
    		Label roomTypeLabel = new Label(roomTypeInfo);
    		
    		roomTypeLabel.
    		setStyle("-fx-text-fill: beige; -fx-font-size: 15px; -fx-font-weight: bold; " +
                    "-fx-background-color: #333; -fx-padding: 10; -fx-background-radius: 10; " +
                    "-fx-text-alignment: center; -fx-min-width: 120;");
    		roomTypeContainer.getChildren().add(roomTypeLabel);
    		
    		roomTypeID++;
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
    	if(roomTypeContainer != null) {
    	displayRoomTypes();
    	}
    }
    @FXML void resetUpdateMessages() {
    	updatePriceError.setText("");
    	updateNameError.setText("");
    	updateIDError.setText("");
    	
    	updateID.setStyle("-fx-border-color : darkSlateGray");
    	updateName.setStyle("-fx-border-color : darkSlateGray");
    	updatePrice.setStyle("-fx-border-color : darkSlateGray");
    	
       	updatePrice.setText("");
    	updateName.setText("");
    	updateID.setText("");
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
    @FXML
    public void toDeleteAmenities(ActionEvent e) {
    	loadScreen("/adminAmenitiesDelete.fxml",e);
    }
    @FXML
    public void toUpdateAmenities(ActionEvent e) {
    	loadScreen("/adminAmenitiesUpdate.fxml",e);
    	if(updateID != null && updateIDError != null) {
    	updateIDError.setText("");
    	
    	updateID.setStyle("-fx-border-color : darkSlateGray");
    	
    	updateID.setText("");
    	}
    	
    }
    @FXML
    public void toViewRoomTypes(ActionEvent e) {
    	loadScreen("/adminRoomTypesView.fxml",e);
    }
    @FXML
    public void toAddRoomTypes(ActionEvent e) {
    	loadScreen("/adminRoomTypesAdd.fxml",e);
    }
    @FXML
    public void toDeleteRoomTypes(ActionEvent e) {
    	loadScreen("/adminRoomTypesDelete.fxml",e);
    }
    @FXML
    public void toUpdateRoomTypes(ActionEvent e) {
    	loadScreen("/adminRoomTypesUpdate.fxml",e);
    	if(updateID != null && updateIDError != null) {
    	updateIDError.setText("");
    	
    	updateID.setStyle("-fx-border-color : darkSlateGray");
    	
    	updateID.setText("");
    	}
    }
    ////////////////////////////////////// AMENITIES FUNCTIONS START
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
    public void updateAmenity() {
    	String priceString = updatePrice.getText().trim();
    	String idString = updateID.getText().trim();
    	int price=-1;
    	int updateId=-1;
    	if(priceString.equals("")) {
    		updatePrice.setStyle("-fx-border-color : red;");
    		updatePriceError.setText("Price is Empty.");
    	}
    	if(idString.equals("")) {
    		updateID.setStyle("-fx-border-color : red");
    		updateIDError.setText("ID is Empty.");
    	}

    	try {
    		updateId = Integer.parseInt(idString);
			updateIDError.setText("");
			updateID.setStyle("-fx-border-color : darkSlateGray;");
    	}catch(Exception e){
    		updateID.setStyle("-fx-border-color : red;");
    		updateIDError.setText("Amenity not found. Please enter a valid ID.");
    		updateIDError.setStyle("-fx-text-fill : red;");
    		updateID.setText("");
    		updateSuccessMessage.setText("");
    		return;
    	}
		updateIDError.setText("");
		updateID.setStyle("-fx-border-color : darkSlateGray;");
		updatePriceError.setText("");
		updatePrice.setStyle("-fx-border-color : darkSlateGray;");

		if (updateId < 0 || updateId > DataBase.amenities.size()) {
			updateID.setStyle("-fx-border-color : red;");
    		updateIDError.setText("Invalid ID.");
    		updateIDError.setStyle("-fx-text-fill : red;");
    		updateID.setText("");
    		updateSuccessMessage.setText("");
    		return;
		}
		
		Amenity toUpdate = DataBase.amenities.get(updateId);
		
		
			try {
	    		price = Integer.parseInt(priceString);
				updatePriceError.setText("");
				updatePrice.setStyle("-fx-border-color : darkSlateGray;");
	    	}catch(Exception e){
	    		updatePrice.setStyle("-fx-border-color : red;");
	    		updatePriceError.setText("Invalid Price.");
	    		updatePriceError.setStyle("-fx-text-fill : red;");
	    		updatePrice.setText("");
	    		updateSuccessMessage.setText("");

	    		return;
	    	}
			updatePriceError.setText("");
			updatePrice.setStyle("-fx-border-color : darkSlateGray;");
			

			if(price < 0) {
				updatePrice.setStyle("-fx-border-color : red;");
	    		updatePriceError.setText("Invalid Price.");
	    		updatePriceError.setStyle("-fx-text-fill : red;");
	    		updatePrice.setText("");
	    		updateSuccessMessage.setText("");
	    		return;
			}
			
			
		toUpdate.setPrice(price);
		
		updateSuccessMessage.setStyle("-fx-text-fill: green;");
		updateSuccessMessage.setText("Amenity Updated Successfully!");
		updateID.setStyle("-fx-border-color : darkSlateGray");
		updatePrice.setStyle("-fx-border-color : darkSlateGray");
		updateID.setText("");
		updatePrice.setText("");
		
		
		displayAmenities();
    	
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
    ///////////////////////////////////// AMENITIES FUNCTIONS END
    
    ///////////////////////////////////// ROOMTYPES FUNCTIONS START
    @FXML
    public void addRoomType() {
    	String addName = roomTypeName.getText().trim();
    	if(addName.equals("")) {
    		roomTypeName.setStyle("-fx-border-color : red;");
    		roomTypeNameMessage.setText("Room type name is empty.");
    		roomTypeNameMessage.setStyle("-fx-text-fill : red;");
    		return;
    	}else {
			roomTypeName.setStyle("-fx-border-color : darkSlateGray;");
			roomTypeNameMessage.setText("");
    		roomTypeName.setText("");
    	}
		for (int i = 0; i < DataBase.roomTypes.size(); i++) {
			if (DataBase.roomTypes.get(i).getRoomType().equalsIgnoreCase(addName)) {
	    		roomTypeName.setStyle("-fx-border-color : red;");
	    		roomTypeNameMessage.setStyle("-fx-text-fill : red;");				
	    		roomTypeNameMessage.setText("Room type already exists.");
	    		return;
			}
		}
		
		DataBase.roomTypes.add(new RoomType(addName));
		roomTypeNameMessage.setStyle("-fx-text-fill : green;");
		roomTypeNameMessage.setText(addName + " added successfully!");
		roomTypeName.setStyle("-fx-border-color : green");
		displayRoomTypes();
    }
    
    @FXML
    public void updateRoomType() {
    	String idString = updateID.getText().trim();
    	String newName = updateName.getText().trim();
    	int updateId = -1;
    	try {
    		updateId = Integer.parseInt(idString); 
    	}catch(Exception e){
    		updateIDError.setText("ID is invalid.");
    		updateID.setText("");
    		updateID.setStyle("-fx-border-color : red;");
    		return;
    	}
		updateID.setStyle("-fx-border-color : darkSlateGray;");
    	if(updateId < 0 || updateId > DataBase.roomTypes.size()) {
    		updateIDError.setText("ID not found.");
    		updateID.setStyle("-fx-border-color : red;");
    		updateID.setText("");
    		return;
    	}
		updateID.setStyle("-fx-border-color : darkSlateGray;");
		updateIDError.setText("");
    	if(newName.equals("")) {
    		updateName.setStyle("-fx-border-color : red;");
    		updateNameError.setText("Room type name is empty.");
    		updateNameError.setStyle("-fx-text-fill : red;");
    		return;
    	}else {
    		updateName.setStyle("-fx-border-color : darkSlateGray;");
			updateNameError.setText("");
			updateName.setText("");
    	}
		for (int i = 0; i < DataBase.roomTypes.size(); i++) {
			if (DataBase.roomTypes.get(i).getRoomType().equalsIgnoreCase(newName)) {
				updateName.setStyle("-fx-border-color : red;");
				updateNameError.setStyle("-fx-text-fill : red;");				
				updateNameError.setText("Room type already exists.");
	    		return;
			}
		}
    	
		DataBase.roomTypes.get(updateId).setRoomType(newName);
    	updateName.setText("");
    	updateNameError.setText("");
    	updateID.setText("");
    	updateIDError.setText("");
    	updateSuccessMessage.setText("Roomtype updated successfully!");
    	displayRoomTypes();
    }
    
    @FXML
    public void deleteRoomType() {
    	int deletedId = -1;
    	
    	try {
    		deletedId = Integer.parseInt(deleteID.getText().trim());
    	}catch(Exception e){
    		deleteID.setStyle("-fx-border-color : red;");
    		roomTypeDeleteMessage.setText("Room Type not found. Please enter a valid ID.");
    		roomTypeDeleteMessage.setStyle("-fx-text-fill : red;");
    		deleteID.setText("");
    		return;
    	}
    	roomTypeDeleteMessage.setText("");
		deleteID.setStyle("-fx-border-color : darkSlateGray;");

		if (deletedId < 0 || deletedId >= DataBase.amenities.size()) {
			deleteID.setStyle("-fx-border-color : red;");
    		roomTypeDeleteMessage.setText("Room Type not found. Please enter a valid ID.");
    		roomTypeDeleteMessage.setStyle("-fx-text-fill : red;");
    		deleteID.setText("");
    		return;
		}
		
		RoomType toDelete = DataBase.roomTypes.get(deletedId);
		for (int i = 0; i < DataBase.rooms.size(); i++) {
			if (DataBase.rooms.get(i).getRoomType().equals(toDelete)) {
				deleteID.setStyle("-fx-border-color : red;");
	    		roomTypeDeleteMessage.setText("Room Type already belongs to a room.");
	    		roomTypeDeleteMessage.setStyle("-fx-text-fill : red;");
				return;
			}
		}
		
		DataBase.roomTypes.remove(toDelete);
		roomTypeDeleteMessage.setStyle("-fx-text-fill: green;");
		roomTypeDeleteMessage.setText("Room Type deleted and removed from all rooms!");
		deleteID.setStyle("-fx-border-color : darkSlateGray");
		deleteID.setText("");
		
		displayRoomTypes();
    }
    ///////////////////////////////////// ROOMTYPES FUNCTIONS END
}