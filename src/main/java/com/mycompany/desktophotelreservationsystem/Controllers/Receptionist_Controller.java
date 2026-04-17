package com.mycompany.desktophotelreservationsystem.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.mycompany.desktophotelreservationsystem.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

public class Receptionist_Controller {

    private Admin admin;
    private Receptionist receptionist;
    private ArrayList<Room> rooms =new ArrayList<>();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label room1;
    @FXML
    private Label room2;
    @FXML
    private Label room3;
    @FXML
    private Label room4;
    @FXML
    private Label room5;
    @FXML
    private Label room6;
    @FXML
    private Label room7;
    @FXML
    private Label room8;
    @FXML
    private Label room9;
    @FXML
    private Label room10;
    @FXML
    private Label room11;
    @FXML
    private Label room12;
    @FXML
    private Label room13;
    @FXML
    private Label room14;
    @FXML
    private Label room15;
    @FXML
    private Label room16;

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
    @FXML
    private FlowPane roomContainer;
    @FXML
    private Rectangle NoOfGuest;

    boolean amenities = false;

    @FXML void loadScreen(String path, ActionEvent e) {
        try {
            root = FXMLLoader.load(getClass().getResource(path));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            System.out.println("Ballersssss");
            e1.printStackTrace();
        }
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        if (stage != null) {
            stage.setScene(scene);
            stage.show();
        }

    }
    @FXML
    public void initialize(){
        if(roomContainer!=null)
        {
            displayRooms();
        }

    }
    @FXML
    public void view_roomtype(ActionEvent e){
        loadScreen("/ReceptionistViewroomtypes.fxml",e);
    }
    @FXML
    public void view_dashboard(ActionEvent e)
    {
        loadScreen("/Receptionists.fxml",e);
    }
    @FXML
    public void check_in()
    {

    }
    @FXML
    public void view_rooms(ActionEvent e){

        loadScreen("/ReceptionistViewrooms.fxml",e);

    }
    @FXML
    public void logout(ActionEvent e){
        loadScreen("/Login.fxml",e);
    }
    @FXML
    public void view_guest(){

    }
    @FXML
    public void check_out(){

    }
    @FXML
    public void view_pending(){

    }
    public void view_reservation()
    {

    }
    @FXML
    public void displayRooms() {

        roomContainer.getChildren().clear();

        roomContainer.setHgap(20);
        roomContainer.setVgap(20);
        roomContainer.setPadding(new Insets(20));

        for(Room r : DataBase.rooms) {
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
            roomContainer.getChildren().add(roomLabel);

        }
    }
}
