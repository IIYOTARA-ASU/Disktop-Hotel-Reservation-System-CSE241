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
        displayRoomTable();
    }
        
    public void makeReservation(Room room , Date inDate , Date outDate){
        Reservation reservation = new Reservation(this, room, inDate, outDate);
        DataBase.reservations.add(reservation);
        reservation.setReservationStatus(Reservation.ReservationStatus.PENDING);
        System.out.println(">> Request is Pending");
    }

    public void viewReservation(Reservation reservation){
        String format = "%-10s %-10s %-30s %8s%n"; // Some Gemini Sauce for a well formatted table
        System.out.printf(format, "ROOM", "CHECK IN DATE", "CHECK OUT DATE", "STATUS");
        System.out.println("-------------------------------------------------------------");
        System.out.printf(format, reservation.getRoom().getRoomNumber(), reservation.getCheckInDate(), reservation.getCheckOutDate(), reservation.getReservationStatus());
    }

        
    public void cancelReservation(Reservation reservation){
        reservation.setReservationStatus(Reservation.ReservationStatus.CANCELLED);
        System.out.println(">> Reservation Cancelled");
    }
        
    public void checkout(Reservation reservation) {
        if (this.balance < reservation.getRoom().getPrice() * reservation.calculateDuration()) {
            System.out.println(">> Sorry, balance is not enough");
            reservation.setReservationStatus(Reservation.ReservationStatus.PENDING);
        } else {
                this.payInvoice(reservation);
            reservation.setReservationStatus(Reservation.ReservationStatus.COMPLETED);
            System.out.println(">> Checkout Operation Complete");
        }
    }
        private void payInvoice (Reservation reservation) {
            Invoice invoice = new Invoice(reservation, Invoice.PaymentMethod.ONLINE, reservation.getCheckOutDate());
            this.balance -= invoice.getAmount();
            DataBase.invoices.add(invoice);
            System.out.println(">> Cash Operation Complete");
        }

    public void guestInterface() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
		System.out.println("║                           GUEST MENU                          ║");
		System.out.println("╚═══════════════════════════════════════════════════════════════╝");
		System.out.println("");

        String prompt = """
        [1] Available Rooms     [2] Make Reservation  [3] View Reservation
        [4] Cancel Reservation  [5] Checkout          [6] Pay Invoice
        >> Select an option: """;
		int inputOption = Validation.getOption(scanner, 6, prompt);


        switch(inputOption) {
            case 1 : 	viewRooms();			break;
            case 2 :
                viewRooms();
                System.out.println(">> Please enter desired room number");
                int roomNumber = Integer.parseInt(scanner.nextLine().trim());
                Room selectedRoom = null;
                for (int i = 0; i < DataBase.rooms.size(); i++) {
                    if (DataBase.rooms.get(i).getRoomNumber() == roomNumber) {
                        selectedRoom = DataBase.rooms.get(i);
                        break;
                    }
                }
                if(selectedRoom==null){
                    System.out.println(">> Room not found");
                    break;
                }
                if(selectedRoom.getOccupied()){
                    System.out.println(">> Room is already occupied");
                    break;
                }


                Date inDate  = readDate(scanner, "Enter check-in date:");
                Date outDate;
                boolean validOutDate;
                do {
                    validOutDate = true;
                    outDate = readDate(scanner, "Enter check-out date:");
                    if (outDate.before(inDate)){
                        System.out.println(">> Outdate is before Indate, try again!");
                        validOutDate = false;
                    }
                }while (!validOutDate);

                makeReservation(selectedRoom , inDate , outDate);
                break;
            case 3 :
                boolean findReservation=false;
                for(int i=0;i< DataBase.reservations.size();i++){
                    if(DataBase.reservations.get(i).getGuest().equals(this)){
                        viewReservation( DataBase.reservations.get(i)) ;
                        findReservation=true;
                    }
                }
                if(!findReservation){
                    System.out.println(">> You have no reservations");
                    break;
                }
                break;
            case 4:
                boolean findPending=false;
                for(int i=0;i< DataBase.reservations.size();i++){
                    if(DataBase.reservations.get(i).getGuest().equals(this) && DataBase.reservations.get(i).getReservationStatus().equals("PENDING")){

                        cancelReservation( DataBase.reservations.get(i)) ;
                        findPending=true;
                    }
                }
                if(!findPending) {
                    System.out.println(">> You have no pending reservations");
                    break;
                }
                break;
            case 5:
                boolean findConfirmed=false;
                for(int i=0;i< DataBase.reservations.size();i++){
                    if(DataBase.reservations.get(i).getGuest().equals(this) && DataBase.reservations.get(i).getReservationStatus().equals("CONFIRMED")){

                        checkout( DataBase.reservations.get(i)); ;
                        findConfirmed=true;
                    }
                }
                if(!findConfirmed) {
                    System.out.println(">> You have no confirmed reservations");
                    break;
                }
                break;
            case 6:
                boolean findCompleted=false;
                for(int i=0;i< DataBase.reservations.size();i++){
                    if(DataBase.reservations.get(i).getGuest().equals(this) && DataBase.reservations.get(i).getReservationStatus().equals("COMPLETED")){

                        checkout( DataBase.reservations.get(i)); ;
                        findCompleted=true;
                    }
                }
                if(!findCompleted) {
                    System.out.println(">> You have no confirmed reservations");
                    break;
                }
                break;



            default  : System.out.println("Invalid number"); 		 return;
        }
    }

    private Date readDate(Scanner scanner, String prompt) {
        System.out.println(prompt);
        int d;
        int m;
        int y;
        do{
            System.out.print("Day: ");
            d = Integer.parseInt(scanner.nextLine().trim());
            if (d < 1 || d > 30){
                System.out.println("Invalid input, try again!");
            }
        }while (d < 1 || d > 30);

        do{
            System.out.print("Month: ");
            m = Integer.parseInt(scanner.nextLine().trim());
            if (m < 1 || m > 12){
                System.out.println("Invalid input, try again!");
            }
        }while (m < 1 || m > 12);

        do{
            System.out.print("year: ");
            y = Integer.parseInt(scanner.nextLine().trim());
            if (y < 2026 || y > 2028){
                System.out.println("Invalid input, try again!");
            }
        }while (y < 2026 || y > 2028);

        Calendar cal = Calendar.getInstance();
        cal.set(y, m - 1, d); // month is like an array fa lazem tezabat el index
        return cal.getTime();
    }

}



