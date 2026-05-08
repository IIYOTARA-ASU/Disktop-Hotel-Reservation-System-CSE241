package com.mycompany.desktophotelreservationsystem.Controllers; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;

import org.controlsfx.control.Notifications;

import com.mycompany.desktophotelreservationsystem.Admin;
import com.mycompany.desktophotelreservationsystem.Amenity;
import com.mycompany.desktophotelreservationsystem.DataBase;
import com.mycompany.desktophotelreservationsystem.Guest;
import com.mycompany.desktophotelreservationsystem.InvalidBalanceException;
import com.mycompany.desktophotelreservationsystem.RFIDThread;
import com.mycompany.desktophotelreservationsystem.Receptionist;
import com.mycompany.desktophotelreservationsystem.Room;
import com.mycompany.desktophotelreservationsystem.RoomType;
import com.mycompany.desktophotelreservationsystem.User;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class theGOATcontroller {
	
	User user = new User();
	User currentUser;
    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    @FXML
    private Text staffCodeText; 
    @FXML
    private Text balanceText;
    @FXML
    private Label dynamicText; 
    @FXML
    private Label usernameErrorMessage; 
    @FXML
    private Label passwordErrorMessage; 
    @FXML
    private Label staffCodeErrorMessage; 
    @FXML
    private Label accountTypeErrorMessage; 
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
    private Label roomDeleteMessage;
    @FXML
    private Label RFIDUpdateMessage;
    @FXML
	private Label currentRFID;
    @FXML
	private Label getRFID;
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
    private Label roomNumberError;
    @FXML
    private Label roomPriceError;
    @FXML
    private Label roomAmenityError;
    @FXML
    private Label roomTypeError;
    @FXML
    private Label roomAddSuccess;
    @FXML
    private Label roomChangeMessage;
    @FXML
    private Label balanceErrorMessage;
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
    private TextField roomNumber;
    @FXML
    private TextField roomPrice;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField staffCode;
    @FXML
    private TextField balance;
    @FXML
    private FlowPane amenityContainer;
    @FXML
    private FlowPane rfidContainer;
    @FXML
    private FlowPane roomTypeContainer;
    @FXML
    private FlowPane roomContainer;
    @FXML
    private VBox roomTypesAddList;
    @FXML
    private VBox amenitiesAddList;
    @FXML
    private ToggleGroup roomTypeRadios = new ToggleGroup();
    @FXML
    private ToggleGroup rfidRadios = new ToggleGroup();
    @FXML
    private ToggleGroup roomTypeUpdateRadios = new ToggleGroup();
    @FXML
    private ToggleGroup amenityUpdateRadios = new ToggleGroup();
    @FXML
    private ToggleGroup roomUpdateRadios = new ToggleGroup();
    @FXML
    private ToggleGroup accountType;
    @FXML
    private ArrayList<CheckBox> amenityCheckBoxes = new ArrayList<>();
    
    public static Stage currentStage;
	Thread t = new Thread(new RFIDThread());
	static boolean isRfidRunning = false;
    boolean amenities = false;
	public static theGOATcontroller goated;
	private static boolean isUpdatingRfid = false;
	private static boolean isUpdatingRoomTypes = false;
	private static boolean isUpdatingAmenities = false;
	private static boolean isUpdatingRooms = false;

    public theGOATcontroller() {}


    String s = "Baller";
    
    @FXML
    public void chooseAmenities() {
    	amenitiesAddList.getChildren().clear();
    	amenityCheckBoxes.clear();
    	for(Amenity a : DataBase.amenities) {
    	CheckBox cb = new CheckBox(a.getName() + " ($" + a.getPrice() + ")");
    	cb.setUserData(a);
    	cb.setStyle("-fx-text-fill: beige; -fx-font-weight: bold; -fx-padding: 8; -fx-font-size: 14px;");
    	amenityCheckBoxes.add(cb);
    	amenitiesAddList.getChildren().add(cb);
    	}
    }
    @FXML
    public void chooseRoomTypes() {
    	roomTypesAddList.getChildren().clear();
    	for(RoomType r : DataBase.roomTypes) {
    	RadioButton rb = new RadioButton(r.getRoomType());
    	rb.getStyleClass().add("roomTypesRadio");
    	rb.setToggleGroup(roomTypeRadios);
    	rb.setUserData(r);
    	rb.setStyle("-fx-text-fill: beige; -fx-font-weight: bold; -fx-padding: 8; -fx-font-size: 14px;");
    	roomTypesAddList.getChildren().add(rb);
    	}
    }
    @FXML
    public void displayAmenities() {
    	int amenityID = 0;
    	amenityContainer.getChildren().clear();
    	
    	amenityContainer.setHgap(20); 
        amenityContainer.setVgap(20);
        amenityContainer.setPadding(new Insets(20));
        
        for(Amenity a : DataBase.amenities) {
    		VBox cardWrapper = new VBox(10); 
            cardWrapper.setAlignment(Pos.CENTER);
            cardWrapper.setSpacing(1);
    		String amenityInfo = "ID : "+ amenityID + "\nName : "+a.getName() + "\nPrice : $" + a.getPrice();
        	
    		Label amenityLabel = new Label(amenityInfo);
    		
    		amenityLabel.
    		setStyle("-fx-text-fill: beige; -fx-font-size: 15px; -fx-font-weight: bold; " +
                    "-fx-background-color: #333; -fx-padding: 10; -fx-background-radius: 10; " +
                    "-fx-text-alignment: center; -fx-min-width: 120;");
    		
    		cardWrapper.getChildren().add(amenityLabel);  
    		RadioButton rb = new RadioButton(Integer.toString(amenityID));
        	rb.getStyleClass().add("radioButtons");
        	rb.setToggleGroup(amenityUpdateRadios);
        	rb.setStyle("-fx-text-fill: beige; -fx-font-weight: bold; -fx-padding: 8; -fx-font-size: 14px;");
        	if(isUpdatingAmenities) {
        	cardWrapper.getChildren().add(rb);
        	}
        	amenityContainer.getChildren().add(cardWrapper);
    		
    		amenityID++;
    	}
    }
    
    @FXML
    public void displayRooms() {
    	roomContainer.getChildren().clear();
    	
    	roomContainer.setHgap(20); 
        roomContainer.setVgap(20);
        roomContainer.setPadding(new Insets(20));
        
    	for(Room r : DataBase.rooms) {
    		VBox cardWrapper = new VBox(10); 
            cardWrapper.setAlignment(Pos.CENTER);
            cardWrapper.setSpacing(1);
            
    		String roomInfo = "Room Number : " + r.getRoomNumber() +"\n Room Type : " +r.getRoomType().getRoomType() + "\n Room Amenities : ";
			for (int j = 0; j < r.getAmenities().size(); j++) {
				roomInfo += r.getAmenities().get(j).getName();
				if (r.getAmenities().size() - j != 1) { roomInfo += ", "; }
			}
			roomInfo+="\n Price : $"+ r.getPrice();
    	
    		Label roomLabel = new Label(roomInfo);
    		
    		roomLabel.
    		setStyle("-fx-text-fill: beige; -fx-font-size: 15px; -fx-font-weight: bold; " +
                    "-fx-background-color: #333; -fx-padding: 10; -fx-background-radius: 10; " +
                    "-fx-text-alignment: center; -fx-min-width: 120;");
    		cardWrapper.getChildren().add(roomLabel);
    		
    		RadioButton rb = new RadioButton(Integer.toString(r.getRoomNumber()));
        	rb.getStyleClass().add("radioButtons");
        	rb.setToggleGroup(roomUpdateRadios);
        	rb.setStyle("-fx-text-fill: beige; -fx-font-weight: bold; -fx-padding: 8; -fx-font-size: 14px;");
        	if(isUpdatingRooms) {
        	cardWrapper.getChildren().add(rb);
        	}
    		roomContainer.getChildren().add(cardWrapper);
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
    		VBox cardWrapper = new VBox(10); 
            cardWrapper.setAlignment(Pos.CENTER);
            cardWrapper.setSpacing(1);
    		String roomTypeInfo = "ID : "+ roomTypeID + "\nName : "+r.getRoomType();
        	
    		Label roomTypeLabel = new Label(roomTypeInfo);
    		
    		roomTypeLabel.
    		setStyle("-fx-text-fill: beige; -fx-font-size: 15px; -fx-font-weight: bold; " +
                    "-fx-background-color: #333; -fx-padding: 10; -fx-background-radius: 10; " +
                    "-fx-text-alignment: center; -fx-min-width: 120;");
    		
    		cardWrapper.getChildren().add(roomTypeLabel);  
    		RadioButton rb = new RadioButton(Integer.toString(roomTypeID));
        	rb.getStyleClass().add("radioButtons");
        	rb.setToggleGroup(roomTypeUpdateRadios);
        	rb.setStyle("-fx-text-fill: beige; -fx-font-weight: bold; -fx-padding: 8; -fx-font-size: 14px;");
        	if(isUpdatingRoomTypes) {
        	cardWrapper.getChildren().add(rb);
        	}
        	roomTypeContainer.getChildren().add(cardWrapper);
    		
    		roomTypeID++;
    	}
    }
    @FXML
    public void displayRFID() {
    	int rfidID = 0;
    	rfidContainer.getChildren().clear();
    	
    	rfidContainer.setHgap(20); 
        rfidContainer.setVgap(20);
        rfidContainer.setPadding(new Insets(20));
        
    	for(User u : DataBase.people) {
    		VBox cardWrapper = new VBox(10); 
            cardWrapper.setAlignment(Pos.CENTER);
            cardWrapper.setSpacing(1);
    		String rfidInfo = "Name : "+ u.userName + "\nRFID ID : " + u.getRfidId() ;
    	
    		Label rfidLabel = new Label(rfidInfo);
    		
    		rfidLabel.
    		setStyle("-fx-text-fill: beige; -fx-font-size: 15px; -fx-font-weight: bold; " +
                    "-fx-background-color: #333; -fx-padding: 10; -fx-background-radius: 10; " +
                    "-fx-text-alignment: center; -fx-min-width: 120;");
    		
    		cardWrapper.getChildren().add(rfidLabel);  
    		RadioButton rb = new RadioButton(Integer.toString(rfidID));
        	rb.getStyleClass().add("radioButtons");
        	rb.setToggleGroup(rfidRadios);
        	rb.setUserData(u.getUserName());
        	rb.setStyle("-fx-text-fill: beige; -fx-font-weight: bold; -fx-padding: 8; -fx-font-size: 14px;");
        	if(isUpdatingRfid) {
        	cardWrapper.getChildren().add(rb);
        	}
        	rfidID++;
        	rfidContainer.getChildren().add(cardWrapper);
        }
    }
    @FXML
    public void initialize() {
    	goated = this;
    	if(!isRfidRunning) {
        t.setDaemon(true); 
        t.start();
        isRfidRunning = true;
    	}
    	if (dynamicText != null) {
            dynamicText.setText("Welcome, " + DataBase.currentUser.getUserName());
        }
    	if(rfidContainer != null) {
    		displayRFID();
    	}
    	if(amenityContainer != null) {
    	displayAmenities();
    	}
    	if(roomTypeContainer != null) {
    	displayRoomTypes();
    	}
    	if(roomContainer != null) {
    	displayRooms();
    	}
    	if(amenitiesAddList != null) {
    	chooseAmenities();
    	}
    	if (roomTypesAddList != null) {
    	chooseRoomTypes();
    	}
    }
    public void toLoginFromRFID(String path) {
 
    	
        Platform.runLater(() -> {
            try {
            	Parent root = FXMLLoader.load(getClass().getResource(path));
                currentStage.setScene(new Scene(root));
                currentStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    @FXML  
    void loadScreen(String path, Event e) {
  	  String css = "/Style.css";
    	try {
          css = this.getClass().getResource("/Style.css").toExternalForm();
			root = FXMLLoader.load(getClass().getResource(path));
			
		  	if (e != null) {
		        currentStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		        }
		  	
		  } catch (IOException e1) {
			// TODO Auto-generated catch block
			  System.out.println("Ballersssss");
			e1.printStackTrace();
		  }
  	  

  	  stage = (Stage)((Node)e.getSource()).getScene().getWindow();
  	  scene = new Scene(root);
  	  scene.getStylesheets().add(css);
  	  stage.setScene(scene);
  	  stage.show();    
  	  
    }
    public void displayNotification(String title,String text) {
    	Notifications notification = Notifications.create();
    	notification
        .title(title)
        .text(text)
        .styleClass("notificationRectangle");
    	notification.show();
    }
    
    @FXML
    public void toAmenities(ActionEvent e) {
    	isUpdatingAmenities = false;
    	loadScreen("/adminAmenities.fxml",e);
    }
	
    @FXML
    public void toAdmin(Event e) {
    	loadScreen("/theGoat.fxml",e);
    }
    
    @FXML
    public void toRooms(ActionEvent e) {
    	isUpdatingRooms = false;
    	loadScreen("/adminRooms.fxml",e);
    }
    @FXML
    public void toViewRooms(ActionEvent e) {
    	loadScreen("/adminRoomsView.fxml",e);
    }
    @FXML
    public void toDeleteRooms(ActionEvent e) {
    	isUpdatingRooms = true;
    	loadScreen("/adminRoomsDelete.fxml",e);
    }
    @FXML
    public void toAddRooms(ActionEvent e) {
    	loadScreen("/adminRoomsAdd.fxml",e);
    }
    @FXML
    public void toUpdateRooms(ActionEvent e) {
    	isUpdatingRooms = true;
    	loadScreen("/adminRoomsUpdate.fxml",e);
    }
    @FXML
    public void toRoomTypes(ActionEvent e) {
    	isUpdatingRoomTypes = false;
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
    	isUpdatingAmenities = true;
    	loadScreen("/adminAmenitiesDelete.fxml",e);
    }
    @FXML
    public void toUpdateAmenities(ActionEvent e) {
    	isUpdatingAmenities = true;
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
    public void clickRadioButton(ActionEvent e) {
    	RadioButton selectedType = (RadioButton) accountType.getSelectedToggle();
    	switch(selectedType.getText()) {
    	case "g" :     	
    	staffCode.setVisible(false); 
    	staffCodeErrorMessage.setVisible(false);
    	staffCodeText.setVisible(false);
       	balance.setVisible(true); 
    	balanceErrorMessage.setVisible(true);
    	balanceText.setVisible(true);
    	break;
    	case "a" : 
    	staffCode.setVisible(true); 
    	staffCodeErrorMessage.setVisible(true);
    	staffCodeText.setVisible(true);
       	balance.setVisible(false); 
    	balanceErrorMessage.setVisible(false);
    	balanceText.setVisible(false);
    	break;
    	case "r" : 
        	staffCode.setVisible(true); 
        	staffCodeErrorMessage.setVisible(true);
        	staffCodeText.setVisible(true);
           	balance.setVisible(false); 
        	balanceErrorMessage.setVisible(false);
        	balanceText.setVisible(false);
        	break;
    	}
    }
    
    @FXML
    public void loginGui(Event e) {
		username.setStyle("-fx-border-color : darkSlateGray");
		password.setStyle("-fx-border-color : darkSlateGray");
		passwordErrorMessage.setText("");
		usernameErrorMessage.setText("");

    	String loginPass = password.getText().trim();
    	String loginUser = username.getText().trim();
    	
    	if(loginUser.equals("")) {
    		username.setStyle("-fx-border-color : red");
    		username.setText("");
    		usernameErrorMessage.setText("Username is empty");
    	}
    	if(loginPass.equals("")) {
    		password.setStyle("-fx-border-color : red");
    		password.setText("");
    		passwordErrorMessage.setText("Password is empty");
    	}
    	if(loginUser.equals("") || loginPass.equals("")) {
    		return;
    	}
    	boolean usernameFound = false;
    	int userIndex = -1;
		for (int i = 0; i < DataBase.people.size(); i++) {
			if (DataBase.people.get(i).userName.equals(loginUser)) {
				usernameFound = true;
				userIndex = i;
			}
		}
		if (!usernameFound) { 
    		username.setStyle("-fx-border-color : red");
    		username.setText("");
    		usernameErrorMessage.setText("Username not found.");
		return; }
    	
		boolean correctPassword   = DataBase.people.get(userIndex).getPassword().equals(loginPass);
		if (!correctPassword) {
		password.setStyle("-fx-border-color : red");
		password.setText("");
		passwordErrorMessage.setText("Wrong Password");
		return;
		};
    	
		
    	DataBase.currentUser = user.login(loginUser, loginPass, true);
    	DataBase.loggedIn = true;
	    	if(DataBase.currentUser instanceof Admin) {
	    		toAdmin(e);
	    		displayNotification("LOGIN SUCCESSFUL","You logged in successfully!");
	    	}
	    	if(DataBase.currentUser instanceof Receptionist) {
	    		//
	    		toReceptionist(e);
	    	}

			if(DataBase.currentUser instanceof Guest) {
				// Save the session inside the Guest class
				Guest.currentLoggedInGuest = (Guest) DataBase.currentUser;
				
				// Redirect to the Guest Menu
				loadScreen("/guestscenebuilder.fxml", e);
		}

    }
    @FXML
    public void Register(Event e) {
		username.setStyle("-fx-border-color : darkSlateGray");
		password.setStyle("-fx-border-color : darkSlateGray");
		staffCode.setStyle("-fx-border-color : darkSlateGray");
		balance.setStyle("-fx-border-color : darkSlateGray");

		balanceErrorMessage.setText("");
		passwordErrorMessage.setText("");
		usernameErrorMessage.setText("");
		usernameErrorMessage.setText("");
		staffCodeErrorMessage.setText("");

    	String registerBalance = balance.getText().trim();
    	String registerPass = password.getText().trim();
    	String registerUser = username.getText().trim();
    	String registerStaffCode = staffCode.getText().trim();
    	
    	if(registerUser.equals("")) {
    		username.setStyle("-fx-border-color : red");
    		username.setText("");
    		usernameErrorMessage.setText("Username is empty");
    	}
    	if(registerPass.equals("")) {
    		password.setStyle("-fx-border-color : red");
    		password.setText("");
    		passwordErrorMessage.setText("Password is empty");
    	}
    	if(registerBalance.equals("") && balance.isVisible()) {
    		balance.setStyle("-fx-border-color : red");
    		balance.setText("");
    		balanceErrorMessage.setText("Balance is empty");
    	}
    	/////////////////////////
    	int bal = 0;
    	try {
    		if(balance.isVisible()) {
    		bal = Integer.parseInt(registerBalance);
    		if(bal<0) {
    			throw new InvalidBalanceException();
    		} 
    		}
    	}catch(InvalidBalanceException ibe) {
    		balance.setStyle("-fx-border-color : red");
    		balance.setText("");
    		balanceErrorMessage.setText("You can't have a negative balance.");
    		return;
    	}catch(Exception e1) {
    		balance.setStyle("-fx-border-color : red");
    		balance.setText("");
    		balanceErrorMessage.setText("Balance is not a valid number");
    		return;
    	}
    	/////////////////
    	if(registerUser.equals("") || registerPass.equals("") || registerBalance.equals("") && balance.isVisible()) {
    		return;
    	}
    	boolean usernameFound = false;
		for (int i = 0; i < DataBase.people.size(); i++) {
			if (DataBase.people.get(i).userName.equals(registerUser)) {
				usernameFound = true;
			}
		}
		if (usernameFound) { 
    		username.setStyle("-fx-border-color : red");
    		username.setText("");
    		usernameErrorMessage.setText("Username already taken.");
		return; 
		}
		if(staffCode.isVisible()) {
			if(!registerStaffCode.equals(User.getAdminCode())) {
	    		staffCode.setStyle("-fx-border-color : red");
	    		staffCode.setText("");
	    		staffCodeErrorMessage.setText("Wrong Staff Code.");
	    		return;
			}
		}
		
		RadioButton selectedAccType = (RadioButton)accountType.getSelectedToggle();
		String newAccType = selectedAccType.getText();
		switch(newAccType) {
		case "g" : DataBase.currentUser = new Guest(registerUser, registerPass); ((Guest)DataBase.currentUser).setBalance(bal); break;
		case "a" : DataBase.currentUser = new Admin(registerUser, registerPass); break;
		case "r" : DataBase.currentUser = new Receptionist(registerUser, registerPass); ((Receptionist)DataBase.currentUser).setWorkingHours(7); break;
		}
		DataBase.people.add(DataBase.currentUser);
		if(DataBase.currentUser instanceof Admin) {
    		toAdmin(e);
    	}
    	if(DataBase.currentUser instanceof Receptionist) {
    		//
    		toReceptionist(e);
    	}
    	if(DataBase.currentUser instanceof Guest) {
    		//
    		toGuest(e);
    	}
		
    }
    @FXML
    public void toRegister(ActionEvent e) {
    	loadScreen("/Register.fxml",e);
    }
    @FXML
    public void toLogin(ActionEvent e) {
    	DataBase.loggedIn = false;
    	DataBase.currentUser = null;
    	loadScreen("/Login.fxml",e);
    }
    @FXML
    public void toGuest(Event e){
    	loadScreen("/guestscenebuilder.fxml",e);
    }
    @FXML
    public void toReceptionist(Event e){
    	loadScreen("/Receptionists.fxml",e);
    }
    @FXML
    public void toAddRoomTypes(ActionEvent e) {
    	loadScreen("/adminRoomTypesAdd.fxml",e);
    }
    @FXML
    public void toDeleteRoomTypes(ActionEvent e) {
    	isUpdatingRoomTypes = true;
    	loadScreen("/adminRoomTypesDelete.fxml",e);
    }
    @FXML
    public void toRFID(ActionEvent e) {
    	loadScreen("/adminRFID.fxml",e);
    }
    @FXML
    public void toAddRFID(ActionEvent e) {
    	loadScreen("/adminRFIDAdd.fxml",e);
    }
    @FXML
    public void toDeleteRFID(ActionEvent e) {
    	loadScreen("/adminRFIDDelete.fxml",e);
    }
    @FXML
    public void toUpdateRFID(ActionEvent e) {
    	isUpdatingRfid = true;
    	loadScreen("/adminRFIDUpdate.fxml",e);
    }
    @FXML
    public void toViewRFID(ActionEvent e) {
    	isUpdatingRfid = false;
    	loadScreen("/adminRFIDView.fxml",e);
    }
    @FXML
    public void loginKeyBoard(KeyEvent e) {
    	KeyCode keycode = e.getCode();
    	if(keycode == KeyCode.ENTER) {
    		loginGui(e);
    	}
    }
    @FXML
    public void registerKeyBoard(KeyEvent e) {
    	KeyCode keycode = e.getCode();
    	if(keycode == KeyCode.ENTER) {
    		Register(e);
    	}
    }
    @FXML
    public void toUpdateRoomTypes(ActionEvent e) {
    	isUpdatingRoomTypes = true;
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
    	int price=-1;
    	int updateId=-1;
    	if(amenityUpdateRadios.getSelectedToggle() == null) {
    		System.out.println("Not Selected");
    		updateSuccessMessage.setText("No Room Type Selected");
    		updateSuccessMessage.setStyle("-fx-text-fill : red;");
    		return;
    		
    	}else {
    		updateId = Integer.parseInt(((RadioButton)amenityUpdateRadios.getSelectedToggle()).getText());
    	}
    	if(priceString.equals("")) {
    		updatePrice.setStyle("-fx-border-color : red;");
    		updatePriceError.setText("Price is Empty.");
    	}
    	

		updatePriceError.setText("");
		updatePrice.setStyle("-fx-border-color : darkSlateGray;");

		
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
		updatePrice.setStyle("-fx-border-color : darkSlateGray");
		updatePrice.setText("");
		
		
		displayAmenities();
		amenityUpdateRadios.selectToggle(null);
    	
    }
    
    @FXML
    public void deleteAmenity() {
		amenityDeleteMessage.setText("");
    	int deletedId = -1;
    	
       	if(amenityUpdateRadios.getSelectedToggle() == null) {
    		System.out.println("Not Selected");
    		amenityDeleteMessage.setText("No Room Type Selected");
    		amenityDeleteMessage.setStyle("-fx-text-fill : red;");
    		return;
    		
    	}else {
    		deletedId = Integer.parseInt(((RadioButton)amenityUpdateRadios.getSelectedToggle()).getText());
    	}
		
		Amenity toDelete = DataBase.amenities.get(deletedId);
		for (int i = 0; i < DataBase.rooms.size(); i++) {
			DataBase.rooms.get(i).getAmenities().removeIf(a -> a.equals(toDelete));
		}
		DataBase.amenities.remove(toDelete);
		System.out.println("   [OK] Amenity deleted and removed from all rooms.");
		amenityDeleteMessage.setStyle("-fx-text-fill: green;");
		amenityDeleteMessage.setText("Amenity deleted and removed from all rooms!");
		
		displayAmenities();
		amenityUpdateRadios.selectToggle(null);
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
		updateSuccessMessage.setText("");
    	String newName = updateName.getText().trim();
    	int updateId = -1;
    	if(roomTypeUpdateRadios.getSelectedToggle() == null) {
    		System.out.println("Not Selected");
    		updateSuccessMessage.setText("No Room Type Selected");
    		updateSuccessMessage.setStyle("-fx-text-fill : red;");
    		return;
    		
    	}else {
    		updateId = Integer.parseInt(((RadioButton)roomTypeUpdateRadios.getSelectedToggle()).getText());
    	}
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
		updateSuccessMessage.setStyle("-fx-text-fill : green;");
    	updateSuccessMessage.setText("Roomtype updated successfully!");
    	displayRoomTypes();
		roomTypeUpdateRadios.selectToggle(null);
    }
    
    @FXML
    public void deleteRoomType() {
    	int deletedId = -1;
    	
    	if(roomTypeUpdateRadios.getSelectedToggle() == null) {
    		System.out.println("Not Selected");
    		roomTypeDeleteMessage.setText("No Room Type Selected");
    		roomTypeDeleteMessage.setStyle("-fx-text-fill : red;");
    		return;
    		
    	}else {
    		deletedId = Integer.parseInt(((RadioButton)roomTypeUpdateRadios.getSelectedToggle()).getText());
    	}
    	
    	roomTypeDeleteMessage.setText("");

		
		RoomType toDelete = DataBase.roomTypes.get(deletedId);
		for (int i = 0; i < DataBase.rooms.size(); i++) {
			if (DataBase.rooms.get(i).getRoomType().equals(toDelete)) {
	    		roomTypeDeleteMessage.setText("Room Type already belongs to a room.");
	    		roomTypeDeleteMessage.setStyle("-fx-text-fill : red;");
				return;
			}
		}
		
		DataBase.roomTypes.remove(toDelete);
		roomTypeDeleteMessage.setStyle("-fx-text-fill: green;");
		roomTypeDeleteMessage.setText("Room Type deleted successfully");
		
		displayRoomTypes();
		roomTypeUpdateRadios.selectToggle(null);

    }
    ///////////////////////////////////// ROOMTYPES FUNCTIONS END
    
    ///////////////////////////////////// ROOMS FUNCTIONS START
    
    public void deleteRoom(){
    	int deletedNumber = -1;
    	int deletedIndex = -1;
    	
    	if(roomUpdateRadios.getSelectedToggle() == null) {
    		System.out.println("Not Selected");
    		roomDeleteMessage.setText("No Room Type Selected");
    		roomDeleteMessage.setStyle("-fx-text-fill : red;");
    		return;
    		
    	}else {
    		deletedNumber = Integer.parseInt(((RadioButton)roomUpdateRadios.getSelectedToggle()).getText());
    	}
    	
    	
    	roomDeleteMessage.setText("");
		for (int i = 0; i < DataBase.rooms.size(); i++) {
		if (DataBase.rooms.get(i).getRoomNumber() == deletedNumber) { deletedIndex = i; break; }
		}
	
		
		Room toDelete = DataBase.rooms.get(deletedIndex);
		DataBase.rooms.remove(toDelete);
		roomDeleteMessage.setStyle("-fx-text-fill: green;");
		roomDeleteMessage.setText("Room deleted successfully!");
		roomUpdateRadios.selectToggle(null);
		
		displayRooms();
    }
    
    public void addRoom() {
		roomNumber.setStyle("-fx-border-color : darkSlateGray;");
		roomPrice.setStyle("-fx-border-color : darkSlateGray;");
		roomNumberError.setText("");
		roomPriceError.setText("");
		roomTypeError.setText("");
		roomAmenityError.setText("");

		RadioButton selectedRoomTypeRadio = (RadioButton) roomTypeRadios.getSelectedToggle();
		RoomType addRoomType = null;
    	String roomNumberString = roomNumber.getText().trim();
    	String roomPriceString = roomPrice.getText().trim();
    	int addRoomNumber = -1;
    	int addRoomPrice = -1;
    	try {
    		addRoomNumber = Integer.parseInt(roomNumberString);
        	if(addRoomNumber < 0) {
        		throw new Exception();
        	}
    	}catch(Exception e) {
    		roomNumber.setStyle("-fx-border-color : red;");
    		roomNumberError.setText("Invalid Room Number.");
    		return;
    	}
    	for(Room r : DataBase.rooms) {
    		if(r.getRoomNumber() == addRoomNumber) {
        		roomNumber.setStyle("-fx-border-color : red;");
        		roomNumber.setText("");
        		roomNumberError.setText("Room already exists.");
    			return;
    		}
    	}
    	try {
    		addRoomPrice = Integer.parseInt(roomPriceString);
    		if(addRoomPrice < 0) {
    			throw new Exception();
    		}
    	}catch(Exception e) {
    		roomPrice.setStyle("-fx-border-color : red;");
    		roomPrice.setText("");
    		roomPriceError.setText("Invalid Room Number.");
    		return;
    	}
    	if(selectedRoomTypeRadio != null) {
    		addRoomType = (RoomType)(selectedRoomTypeRadio.getUserData());
    	}else {
    		roomTypeError.setText("You must select a room type.");
    		return;
    	}
    	
    	ArrayList<Amenity> amenitiesSelected = new ArrayList<>();
    	for(CheckBox cb : amenityCheckBoxes) {
    		if(cb.isSelected()) {
    			Amenity amenity = (Amenity)cb.getUserData();
    			amenitiesSelected.add(amenity);
    		}
    	}
    	
    	if(amenitiesSelected.size() == 0) {
    		roomAmenityError.setText("You must select an amenity.");
    		return;
    	}
    	
    	Room addRoom = new Room(addRoomNumber,addRoomType,addRoomPrice);
    	for(Amenity a : amenitiesSelected) {
    		addRoom.addAmenity(a);
    	}
    	DataBase.rooms.add(addRoom);
    	
		roomAddSuccess.setText("Room added successfully!");
		
		roomNumber.setText("");
		roomPrice.setText("");
		
    }
    
    public void updateRoom() {
		roomPrice.setStyle("-fx-border-color : darkSlateGray;");
		roomNumberError.setText("");
		roomPriceError.setText("");
		roomTypeError.setText("");
		roomAmenityError.setText("");
		
		int roomIndex = -1;
		RadioButton selectedRoomTypeRadio = (RadioButton) roomTypeRadios.getSelectedToggle();
		RoomType updateRoomType = null;
    	String roomPriceString = roomPrice.getText().trim();
    	int updateRoomNumber = -1;
    	int updateRoomPrice = -1;
    	if(roomUpdateRadios.getSelectedToggle() == null) {
    		System.out.println("Not Selected");
    		roomNumberError.setText("No Room Type Selected");
    		roomNumberError.setStyle("-fx-text-fill : red;");
    		return;
    		
    	}else {
    		updateRoomNumber = Integer.parseInt(((RadioButton)roomUpdateRadios.getSelectedToggle()).getText());
    	}
    	
    	try {
    		updateRoomPrice = Integer.parseInt(roomPriceString);
    		if(updateRoomPrice < 0) {
    			throw new Exception();
    		}
    	}catch(Exception e) {
    		roomPrice.setStyle("-fx-border-color : red;");
    		roomPrice.setText("");
    		roomPriceError.setText("Invalid Room Number.");
    		return;
    	}
    	if(selectedRoomTypeRadio != null) {
    		updateRoomType = (RoomType)(selectedRoomTypeRadio.getUserData());
    	}
    
    	ArrayList<Amenity> amenitiesSelected = new ArrayList<>();
    	for(CheckBox cb : amenityCheckBoxes) {
    		if(cb.isSelected()) {
    			Amenity amenity = (Amenity)cb.getUserData();
    			amenitiesSelected.add(amenity);
    		}
    	}
    	
		for (int i = 0; i < DataBase.rooms.size(); i++) {
		if (DataBase.rooms.get(i).getRoomNumber() == updateRoomNumber) { roomIndex = i; break; }
		}
    	
		Room updateRoom = DataBase.rooms.get(roomIndex);
		updateRoom.setRoomNumber(updateRoomNumber);
		updateRoom.setPrice(updateRoomPrice);
		if(updateRoomType != null) {
		updateRoom.setRoomType(updateRoomType);
		}
		if(amenitiesSelected.size() != 0) {
		updateRoom.getAmenities().clear();
		updateRoom.getAmenities().addAll(amenitiesSelected);
		}
		roomChangeMessage.setText("Room Updated successfully!");
		
		roomPrice.setText("");
		roomUpdateRadios.selectToggle(null);
		displayRooms();
		
    }
    ///////////////////////////////////// ROOMS FUNCTIONS END
    
    ///////////////////////////////////// RFID FUNCTIONS START
    @FXML
    public void updateRFID(ActionEvent e) {
		RFIDUpdateMessage.setText("");
    	RadioButton selectedRfidButton = (RadioButton)rfidRadios.getSelectedToggle();
    	User updateUser;
    	if(selectedRfidButton == null) {
    		RFIDUpdateMessage.setText("You must choose a user to change their RFID.");
    		RFIDUpdateMessage.setStyle("-fx-text-fill : red;");
    		return;
    	}else {
    		updateUser = DataBase.people.get(Integer.parseInt(selectedRfidButton.getText()));
    	}
    	
    	if(getRFID.getText().equals("")) {
    		RFIDUpdateMessage.setText("You must scan the card first!");
    		RFIDUpdateMessage.setStyle("-fx-text-fill : red;");
    		return;
    	}
    	updateUser.setRfidId(getRFID.getText());
		for(User u : DataBase.people) {
			if(u.getRfidId() != null && u.getRfidId().equals(getRFID.getText()) && u != updateUser) {
				u.setRfidId(null);
			}
		}
    	RFIDUpdateMessage.setText("Rfid Updated Successfully");
		RFIDUpdateMessage.setStyle("-fx-text-fill : green;");
		currentRFID.setText("");
		getRFID.setText("");
		displayRFID();
    	
    }
    @FXML
    public void deleteRFID(ActionEvent e) {
    	RadioButton selectedRfidButton = (RadioButton)rfidRadios.getSelectedToggle();
    	User deleteUser;
    	if(selectedRfidButton == null) {
    		RFIDUpdateMessage.setText("You must choose a user to change their RFID.");
    		RFIDUpdateMessage.setStyle("-fx-text-fill : red;");
    		return;
    	}
    	else {
    	    deleteUser = DataBase.people.get(Integer.parseInt(selectedRfidButton.getText()));
    	}
    	
    	if(deleteUser.getRfidId() == null){
    		RFIDUpdateMessage.setText("RFID already null!");
    		RFIDUpdateMessage.setStyle("-fx-text-fill : red;");
    		return;
    	}
    	
		DataBase.people.get(Integer.parseInt(selectedRfidButton.getText())).setRfidId(null);
		RFIDUpdateMessage.setText("Rfid Deleted Successfully");
		RFIDUpdateMessage.setStyle("-fx-text-fill : green;");
    	
    	displayRFID();
    }

	public  Label getCurrentRFID() {
		return currentRFID;
	}
	public  void setCurrentRFID(Label currentRFID) {
		this.currentRFID = currentRFID;
	}
	public  Label getGetRFID() {
		return getRFID;
	}
	public  void setGetRFID(Label getRFID) {
		this.getRFID = getRFID;
	}
	
    ///////////////////////////////////// RFID FUNCTIONS END
}