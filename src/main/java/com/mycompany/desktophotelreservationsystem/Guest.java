
package com.mycompany.desktophotelreservationsystem;
import java.util.*;

public class Guest extends User{
    private double balance;
    private String address;

    public double getBalance(){return balance;}
    public void setBalance(double balance) {this.balance = balance;}

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    Guest() {
    }
    Guest(String username, String password){
        super(username, password);
    }

    public enum gender{MALE , FEMALE}
    Room currentRoom = null;
    @Override
    public void viewRooms() {
        for (int i = 0; i < DataBase.rooms.size(); i++) {
            if (DataBase.rooms.get(i).getOccupied() == false)
            System.out.println("Room: "+ DataBase.rooms.get(i).getRoomNumber());
        }
    }
        
    public void makeReservation(Room room , Date inDate , Date outDate){
        Reservation reservation = new Reservation(this, room, inDate, outDate);
        DataBase.reservations.add(reservation);
        reservation.setReservationStatus(Reservation.ReservationStatus.PENDING);
        System.out.println("Request is Pending");
    }

    public void viewReservation(Reservation reservation){
        String format = "%-10s %-10s %-30s %8s%n"; // Some Gemini Sauce for a well formatted table
        System.out.printf(format, "ROOM", "CHECK IN DATE", "CHECK OUT DATE", "STATUS");
        System.out.println("-------------------------------------------------------------");
        System.out.printf(format, reservation.getRoom().getRoomNumber(), reservation.getCheckInDate(), reservation.getCheckOutDate(), reservation.getReservationStatus());
    }

        
    public void cancelReservation(Reservation reservation){
        reservation.setReservationStatus(Reservation.ReservationStatus.CANCELLED);
        System.out.println("Reservation Cancelled");
    }
        
    public void checkout(Reservation reservation) {
        if (this.balance < reservation.getRoom().getPrice() * reservation.calculateDuration()) {
            System.out.println("Sorry, balance is not enough");
            reservation.setReservationStatus(Reservation.ReservationStatus.PENDING);
        } else {
                this.payInvoice(reservation);
            reservation.setReservationStatus(Reservation.ReservationStatus.COMPLETED);
            System.out.println("Checkout Operation Complete");
        }
    }
        private void payInvoice (Reservation reservation) {
            Invoice invoice = new Invoice(reservation, Invoice.PaymentMethod.ONLINE, reservation.getCheckOutDate());
            this.balance -= invoice.getAmount();
            DataBase.invoices.add(invoice);
            System.out.println("Cash Operation Complete");
        }
}
