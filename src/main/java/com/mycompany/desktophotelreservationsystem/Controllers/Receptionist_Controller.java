package com.mycompany.desktophotelreservationsystem.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.mycompany.desktophotelreservationsystem.Receptionist;
import com.mycompany.desktophotelreservationsystem.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import com.mycompany.desktophotelreservationsystem.DataBase;
import javafx.scene.paint.Color;
import java.util.Date;
import java.util.Date.*;
import java.util.ArrayList;
import java.text.*;


public class Receptionist_Controller {

    private Admin admin;
    private Receptionist receptionist;
    private boolean[] roomstate = new boolean[25];
    private Room r = new Room();

    RoomType single = new RoomType("Single");
    RoomType couple = new RoomType("Double"); // "double" is a reserved word in java
    RoomType suite = new RoomType("Suite");


    private Stage stage;
    private Scene scene;
    private Parent root;
    private GuestController g = new GuestController();
    @FXML
    private VBox reservationListContainer;
    @FXML
    Rectangle Room67;
    @FXML
    Rectangle Room123;
    @FXML
    Rectangle Room108;
    @FXML
    Label recepname;
    @FXML
    Label Date;
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
    private VBox recepContainers;
    @FXML
    private Rectangle NoOfGuest;
    @FXML
    private TextField roomno ;
    @FXML
    private VBox checkinContainers;
    @FXML
    private VBox checkoutContainers;
    @FXML
    boolean amenities = false;
    @FXML
    private Label date;


    @FXML
    void loadScreen(String path, ActionEvent e) {
        try {
            root = FXMLLoader.load(getClass().getResource(path));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            System.out.println("SIKEEEEE DIGGER u entered a wrong path");
            e1.printStackTrace();
        }

        scene = new Scene(root);
        if (stage != null) {
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    public void initialize() {
        // 1. Handle the Room Labels (FlowPane)
        if (roomContainer != null) {
            displayRooms();
        }

        // 2. Handle the Rectangles (Visual Map)
        if (Room67 != null && Room123 != null && Room108 != null) {
            // Reset them all to Green first
            Room67.setFill(Color.GREEN);
            Room108.setFill(Color.GREEN);
            Room123.setFill(Color.GREEN);

            // Check the database and update specific rectangles
            for (Room r : DataBase.rooms) {
                if (r.getOcc()) {
                    // Change ONLY the rectangle matching this room number
                    if (r.getRoomNumber() == 67) Room67.setFill(Color.RED);
                    if (r.getRoomNumber() == 108) Room108.setFill(Color.RED);
                    if (r.getRoomNumber() == 123) Room123.setFill(Color.RED);
                }
            }
        }
        // 3. Put Receptionist Name
        if (recepname != null && DataBase.people.size() > 2) {
            recepname.setText(DataBase.people.get(2).getUserName());
        }
        if(date!=null)
        {
            SimpleDateFormat f= new SimpleDateFormat("d MMMM yyyy");
            Date d=new Date();
            f.format(d);
            date.setText(d.toString());
        }
        // null checking
        if (recepContainers != null ) {
            displayReservationCards();
        }

        // If we are on the Check-In FXML
        if (checkinContainers != null ) {
            checkin();
        }
        if (checkoutContainers != null ) {
            checkout();
        }
    }

    public void displayReservationCards() {

        recepContainers.getChildren().clear();

        // We want to see ALL reservations for the receptionist to manage
        for (Reservation res : DataBase.reservations) {
            String statusColor = "#f39c12"; // Pending yellow
            String statusStr = res.getReservationStatus().toString();

            if (statusStr.equals("CONFIRMED")) statusColor = "#27ae60";
            else if (statusStr.equals("CANCELLED")) statusColor = "#c0392b";
            else if (statusStr.equals("COMPLETED")) statusColor = "#3498db";

            String info = "Guest: " + res.getGuest().getUserName() +
                    "\nRoom: " + res.getRoom().getRoomNumber() +
                    "\nCheck-in: " + res.getCheckInDate() +
                    "\nStatus: " + statusStr+
                    "\nCheck-out:"+res.getCheckOutDate();

            Label card = new Label(info);
            card.setStyle(
                    "-fx-text-fill: beige; -fx-font-size: 15px; -fx-font-weight: bold; " +
                            "-fx-background-color: #24423f; -fx-padding: 15; -fx-background-radius: 10; " +
                            "-fx-min-width: 500; -fx-border-color: " + statusColor + "; " +
                            "-fx-border-width: 0 0 0 10; -fx-border-radius: 10;"
            );

            recepContainers.getChildren().add(card);
            // Add some spacing between cards
            VBox.setMargin(card, new Insets(0, 0, 10, 0));
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
    public void check_in(ActionEvent e)
    {

        loadScreen("/ReceptionistCheckIn.fxml",e);
    }
    private void checkin() {

        if (checkinContainers == null) return; // Or whatever container your check-in UI uses
        checkinContainers.getChildren().clear();

        for (Reservation r : DataBase.reservations) {
            // Only show PENDING reservations for check-in
            if (r.getReservationStatus().toString().equals("PENDING")) {

                Label infoLabel = new Label("Check-in Guest: " + r.getGuest().getUserName() +
                        "\nAssigning Room: " + r.getRoom().getRoomNumber()+"\nCheck in Date:"
                        +r.getCheckInDate()+"\nCheck out Date:"+r.getCheckOutDate());

                Button btn = new Button("Confirm Check-In");
                btn.setOnAction(e -> executeCheckIn(r)); // Separate logic method

                HBox card = new HBox(20, infoLabel, btn);
                card.setAlignment(Pos.CENTER_LEFT);
                card.setStyle("-fx-background-color: #2c3e50; -fx-padding: 15; -fx-background-radius: 10;");

                checkinContainers.getChildren().add(card);
                VBox.setMargin(card, new Insets(0, 0, 10, 0));
            }
        }

    }
    public void executeCheckIn(Reservation res)
    {
        res.setReservationStatus(Reservation.ReservationStatus.CONFIRMED);
        res.getRoom().setOccupied();
        checkin();
    }
    private boolean isCheckInScreen() {
        return checkinContainers != null;
    }
    private String getStatusColor(String status) {
        switch (status) {
            case "CONFIRMED": return "#27ae60";
            case "CANCELLED": return "#c0392b";
            case "COMPLETED": return "#3498db";
            default: return "#f39c12";
        }
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
    public void view_guest(ActionEvent e){

        loadScreen("/ReceptionistViewGuests.fxml",e);
    }
    @FXML
    public void check_out(ActionEvent e){
        loadScreen("/ReceptionistCheckOut.fxml",e);
    }
    public void checkout(){
        if (checkoutContainers == null) return; // Or whatever container your check-in UI uses
        checkoutContainers.getChildren().clear();

        for (Reservation r : DataBase.reservations) {
            // Only show PENDING reservations for check-in
            if (r.getReservationStatus().toString().equals("CONFIRMED")) {

                Label infoLabel = new Label("Check-in Guest: " + r.getGuest().getUserName() +
                        "\nAssigning Room: " + r.getRoom().getRoomNumber()+"\nCheck in Date:"
                        +r.getCheckInDate()+"\nCheck out Date:"+r.getCheckOutDate());

                Button btn = new Button("Confirm Check-Out");
                btn.setOnAction(e -> executeCheckOut(r)); // Separate logic method

                HBox card = new HBox(20, infoLabel, btn);
                card.setAlignment(Pos.CENTER_LEFT);
                card.setStyle("-fx-background-color: #2c3e50; -fx-padding: 15; -fx-background-radius: 10;");

                checkoutContainers.getChildren().add(card);
                VBox.setMargin(card, new Insets(0, 0, 10, 0));
            }
        }

    }
    public void executeCheckOut(Reservation res)
    {
        res.setReservationStatus(Reservation.ReservationStatus.COMPLETED);
        res.getRoom().setUnOccupied();
        checkout();
    }
    @FXML
    public void view_reservation(ActionEvent e)
    {
        loadScreen("/ReceptionistViewreservation.fxml",e);
    }
    @FXML
    public void displayRoom(){

        roomContainer.getChildren().clear();
        roomContainer.setHgap(20);
        roomContainer.setVgap(20);
        roomContainer.setPadding(new Insets(20));

        for (Room r : DataBase.rooms) {
            // Create a Label to act as our "Square"
            Label roomSquare = new Label(String.valueOf(r.getRoomNumber()));

            // Determine color based on occupancy
            // Assuming r.getOcc() returns true if occupied
            String backgroundColor = r.getOcc() ? "#e74c3c" : "#2ecc71"; // Red if occupied, Green if free

            roomSquare.setStyle(
                    "-fx-background-color: " + backgroundColor + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 18px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-alignment: center;" +
                            "-fx-min-width: 80;" +
                            "-fx-min-height: 80;" +
                            "-fx-background-radius: 15;" + // Rounds the corners like your screenshot
                            "-fx-border-color: #333;" +
                            "-fx-border-radius: 15;" +
                            "-fx-border-width: 2;"
            );

            // Optional: Add a Tooltip so you can still see price/amenities on hover
            String tooltipText = "Type: " + r.getRoomType().getRoomType() + "\nPrice: $" + r.getPrice();
            javafx.scene.control.Tooltip.install(roomSquare, new javafx.scene.control.Tooltip(tooltipText));

            roomContainer.getChildren().add(roomSquare);
        }
    }
    @FXML
    public void displayRooms() {
        roomContainer.getChildren().clear();

        roomContainer.setHgap(20);
        roomContainer.setVgap(20);
        roomContainer.setPadding(new Insets(20));

        for(Room r : DataBase.rooms) {
            if(!r.getOccupied()) {
                String roomInfo = "Room Number : " + r.getRoomNumber() + "\n Room Type : " + r.getRoomType().getRoomType() + "\n Room Amenities : ";
                for (int j = 0; j < r.getAmenities().size(); j++) {
                    roomInfo += r.getAmenities().get(j).getName();
                    if (r.getAmenities().size() - j != 1) {
                        roomInfo += ", ";
                    }
                }
                roomInfo += "\n Price : $" + r.getPrice();

                Label roomLabel = new Label(roomInfo);

                roomLabel.
                        setStyle("-fx-text-fill: beige; -fx-font-size: 15px; -fx-font-weight: bold; " +
                                "-fx-background-color: #333; -fx-padding: 10; -fx-background-radius: 10; " +
                                "-fx-text-alignment: center; -fx-min-width: 120;");
                roomContainer.getChildren().add(roomLabel);
            }

        }
    }
}