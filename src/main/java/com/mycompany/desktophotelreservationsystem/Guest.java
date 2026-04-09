
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

    public void guestInterface() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("=================================================================");
        System.out.println("=========================== GUEST MENU ==========================");
        System.out.println("GUEST OPTIONS]");
        System.out.println("[1] Available Rooms  [2] Make Reservation  [3] View Reservation  ");
        System.out.println("[4] Cancel Reservation  [5] Checkout  [6] Pay Invoice");

        String inputOption;
        boolean validOption;
        do {
            inputOption = scanner.nextLine().trim();
            validOption = inputOption.equals("1") || inputOption.equals("2") || inputOption.equals("3")|| inputOption.equals("4")|| inputOption.equals("5")|| inputOption.equals("6");
            if (!validOption) { System.out.println("Invalid option, please try again"); }
        } while (!validOption);

        switch(inputOption) {
            case "1" : 	viewRooms();			break;
            case "2" :
                viewRooms();
                System.out.println("please enter desired room number");
                int roomNumber = Integer.parseInt(scanner.nextLine().trim());
                Room selectedRoom = null;
                for (int i = 0; i < DataBase.rooms.size(); i++) {
                    if (DataBase.rooms.get(i).getRoomNumber() == roomNumber) {
                        selectedRoom = DataBase.rooms.get(i);
                        break;
                    }
                }
                if(selectedRoom==null){
                    System.out.println("room not found");
                    break;
                }
                if(selectedRoom.getOccupied()){
                    System.out.println("room is already occupied");
                    break;
                }
                Date inDate  = readDate(scanner, "Enter check-in date:");
                Date outDate = readDate(scanner, "Enter check-out date:");

                makeReservation(selectedRoom , inDate , outDate);
                break;
            case "3" : 	System.out.println("Room Types:") ; 				break;
            default  : System.out.println("Invalid number"); 		 return;
        }
    }

    private Date readDate(Scanner scanner, String prompt) {
        System.out.println(prompt);
        System.out.print("Day: ");   int d = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Month: "); int m = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Year: ");  int y = Integer.parseInt(scanner.nextLine().trim());

        Calendar cal = Calendar.getInstance();
        cal.set(y, m - 1, d); // month is like an array fa lazem tezabat el index
        return cal.getTime();
    }

}



