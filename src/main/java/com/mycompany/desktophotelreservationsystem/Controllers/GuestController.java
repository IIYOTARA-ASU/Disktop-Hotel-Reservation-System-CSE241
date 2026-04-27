package com.mycompany.desktophotelreservationsystem.Controllers;
import com.mycompany.desktophotelreservationsystem.DataBase;
import com.mycompany.desktophotelreservationsystem.Guest;
import com.mycompany.desktophotelreservationsystem.RFIDThread;
import com.mycompany.desktophotelreservationsystem.Reservation;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class GuestController {
    @FXML private FlowPane roomContainer;
    @FXML private Label welcomelabel;
    @FXML private TextField roomTextFiel;
    @FXML private DatePicker checkinDatepicker;
    @FXML private DatePicker checkoutDatePicker;
    @FXML private Label errorLabel;


    @FXML
    public void handleMakeReservation(ActionEvent event) {
        try {
            String roomNumStr = roomTextFiel.getText();
            LocalDate inDate = checkinDatepicker.getValue();
            LocalDate outDate = checkoutDatePicker.getValue();
            LocalDate currentDate=LocalDate.now();

            // Validation
            if (inDate!=null && outDate!=null&& outDate.isBefore(inDate) ){
                errorLabel.setText("error in inputted date");
                errorLabel.setVisible(true);
                return;
            }
            if (inDate!=null && outDate!=null&& outDate.isBefore(currentDate)||inDate!=null && outDate!=null&& inDate.isBefore(currentDate)){
                errorLabel.setText("error in inputted date");
                errorLabel.setVisible(true);
                return;
            }

            if (roomNumStr.isEmpty() || inDate == null || outDate == null) {
                errorLabel.setText("please enter all the required data");
                errorLabel.setVisible(true);
                return;
            }

            int roomNumber = Integer.parseInt(roomNumStr);

            //Find the Room in DataBase.rooms
            Room selectedRoom = null;
            for (Room r : DataBase.rooms) {
                if (r.getRoomNumber() == roomNumber) {
                    selectedRoom = r;
                    r.setOccupied();
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

    @FXML
    public void handleCancelReservation(ActionEvent event) {
        try {
            String roomNumStr = roomTextFiel.getText();
//            LocalDate inLocalDate = checkinDatepicker.getValue();
//            LocalDate outLocalDate = checkoutDatePicker.getValue();

            if (roomNumStr.isEmpty() ) {
                errorLabel.setText("enter all required data");
                errorLabel.setVisible(true);
                return;
            }

            int roomNumber = Integer.parseInt(roomNumStr);
//            java.util.Date finalIn = java.sql.Date.valueOf(inLocalDate);
//            java.util.Date finalOut = java.sql.Date.valueOf(outLocalDate);

            if (Guest.currentLoggedInGuest != null) {
                boolean success = Guest.currentLoggedInGuest.processCancellation(roomNumber);

                if (success) {
                    System.out.println("Reservation cancelled successfully.");
                    displayUserReservations();
                    toGuestMenu(event);
                } else {
                    errorLabel.setText("no such room is reserved");
                    errorLabel.setVisible(true);
                    return;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Room Number format.");
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
    private Label balanceLabel;

    @FXML
    public void initialize() {
    	theGOATcontroller.isRfidRunning = false;
    	RFIDThread.commPort.closePort();

    	if (roomContainer != null) {
            displayRooms();
        }
        if (reservationListContainer != null) {
            displayUserReservations();
        }
        if (balanceLabel != null && Guest.currentLoggedInGuest != null) {
            balanceLabel.setText("Balance: $" + Guest.currentLoggedInGuest.getBalance());
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
    public void handlePayInvoice(ActionEvent event) {
        try {
            String roomNumStr = roomTextFiel.getText();
            if (roomNumStr.isEmpty()) {
                errorLabel.setText("please enter all the required data");
                errorLabel.setVisible(true);
                return;
            }

            int roomNumber = Integer.parseInt(roomNumStr);

            if (Guest.currentLoggedInGuest != null) {
                boolean success = Guest.currentLoggedInGuest.payInvoiceByRoomNumber(roomNumber);

                if (success) {
                    errorLabel.setText("transaction successful");
                    errorLabel.setTextFill(Color.GREEN);
                    errorLabel.setVisible(true);
                    displayUserReservations(); // Refresh the GUI list
                    toGuestMenu(event);
                } else {
                    errorLabel.setText("room not found");
                    errorLabel.setVisible(true);
                    return;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Room Number.");
        }
    }

    @FXML private VBox reservationListContainer;

    @FXML
    public void displayUserReservations() {
        if (reservationListContainer != null && Guest.currentLoggedInGuest != null) {
            Guest.currentLoggedInGuest.populateReservationContainer(reservationListContainer);
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
    @FXML
    public void switchToViewReservations(ActionEvent event){
        loadScreen("/guestViewReservation.fxml",event);
    }
    @FXML
    public void switchToCancelReservations(ActionEvent event){
        loadScreen("/guestCancelReservation.fxml",event);
    }
    @FXML
    public void switchToPayInvoice(ActionEvent event){
        loadScreen("/guestPayInvoice.fxml",event);
    }
    public void logout(ActionEvent e){
    	DataBase.loggedIn = false;
    	DataBase.currentUser = null;
        loadScreen("/Login.fxml",e);
    }

}
