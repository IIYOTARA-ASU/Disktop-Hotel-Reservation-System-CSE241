package com.mycompany.desktophotelreservationsystem.Controllers;
import com.mycompany.desktophotelreservationsystem.DataBase;
import com.mycompany.desktophotelreservationsystem.Guest;
import com.mycompany.desktophotelreservationsystem.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class GuestController {
    @FXML private FlowPane roomContainer;
    @FXML private Label welcomelabel;
    @FXML private TextField roomTextFiel;
    @FXML private DatePicker checkinDatepicker;
    @FXML private DatePicker checkoutDatePicker;

    @FXML
    public void handleMakeReservation(ActionEvent event) {
        try {
            String roomNumStr = roomTextFiel.getText();
            LocalDate inDate = checkinDatepicker.getValue();
            LocalDate outDate = checkoutDatePicker.getValue();

            // Validation
            if (roomNumStr.isEmpty() || inDate == null || outDate == null) {
                System.out.println("Please fill all fields!"); // Replace with a Label for better UI
                return;
            }

            int roomNumber = Integer.parseInt(roomNumStr);

            //Find the Room in DataBase.rooms
            Room selectedRoom = null;
            for (Room r : DataBase.rooms) {
                if (r.getRoomNumber() == roomNumber) {
                    selectedRoom = r;
                    break;
                }
            }

            if (selectedRoom == null) {
                System.out.println("Room " + roomNumber + " does not exist.");
                return;
            }

            //make java date objects to send as parameters to textbased
            java.util.Date finalInDate = java.sql.Date.valueOf(inDate);
            java.util.Date finalOutDate = java.sql.Date.valueOf(outDate);

            // Access the Logged-in Guest for now haykoon baraa
            if (Guest.currentLoggedInGuest != null) {
                Guest.currentLoggedInGuest.makeReservation(selectedRoom, finalInDate, finalOutDate);
                System.out.println("Reservation submitted for " + Guest.currentLoggedInGuest.getUserName());

                toGuestMenu(event);
            } else {
                System.out.println("Error: No active Guest session.");
            }


        } catch (NumberFormatException e) {
            System.out.println("Invalid Room Number.");
        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button buttonViewRooms;
    @FXML
    private Button buttonViewReservation;
    @FXML
    private Button buttonMakeReservation;
    @FXML
    private Button buttonCancelReservation;
    @FXML
    private Button buttonCheckout;
    @FXML
    private Button buttonPayInvoice;

    @FXML
    public void initialize() {
        // This runs automatically when guestViewRooms.fxml is loaded
        if (roomContainer != null) {
            displayRooms();
        }

    }

    @FXML
    private void loadScreen(String path, ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println("Error loading FXML: " + path);
            ex.printStackTrace();
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

    @FXML
    public void toGuestMenu(ActionEvent e) {
        loadScreen("/guestscenebuilder.fxml", e);
    }

    @FXML
    public void switchToViewRooms(ActionEvent event) {
        loadScreen("/guestViewRooms.fxml", event);
    }
    @FXML
    public void switchToMakeReservations(ActionEvent event){
        loadScreen("/guestMakeReservations.fxml",event);
    }

}
