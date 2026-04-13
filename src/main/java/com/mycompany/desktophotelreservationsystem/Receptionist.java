package com.mycompany.desktophotelreservationsystem;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Receptionist extends Staff {
	Scanner in = new Scanner(System.in);
	Receptionist() {
	}

    Receptionist(String username, String password){
		super(username, password);
    }

	public void checkin(Guest guest , Room room , Date inDate , Date outDate){
		if (room.getOccupied() == true){
			System.out.println("Room Occupied already!");
		}
		else {
			Reservation reservation = new Reservation(guest, room, inDate, outDate);
			DataBase.reservations.add(reservation);
			reservation.setReservationStatus(Reservation.ReservationStatus.CONFIRMED);
			System.out.println("Room Reserved");
		}
	}


	public void checkout(Guest guest , Reservation reservation){
		if (guest.getBalance() < reservation.getRoom().getPrice() * reservation.calculateDuration()){
			System.out.println("Sorry, balance is not enough");
			reservation.setReservationStatus(Reservation.ReservationStatus.PENDING);
		}
		else{
			Invoice invoice = new Invoice(reservation , null , reservation.getCheckOutDate());
			int input;
			do {
				System.out.println("Enter your payment method:\n1: Cash\n2: Credit Card\n");
				input = in.nextInt();
			}while(input != 1 && input != 2);
			if (input == 1) {
				invoice.setPaymentMethod(Invoice.PaymentMethod.CASH);
				System.out.println("Taking Money from Guest by hands");
			}
			if (input == 2) {
				invoice.setPaymentMethod(Invoice.PaymentMethod.CREDIT_CARD);
				guest.setBalance(guest.getBalance() - invoice.getAmount());
				System.out.println("Cash Operation Complete");
			}
			DataBase.invoices.add(invoice);
			reservation.setReservationStatus(Reservation.ReservationStatus.COMPLETED);
		}
	}
	public void receptionistInterface(){
		Scanner scanner = new Scanner(System.in);
		int choice;

		System.out.println("╔═══════════════════════════════════════════════════════════════╗");
		System.out.println("║                       RECEPTIONIST MENU                       ║");
		System.out.println("╚═══════════════════════════════════════════════════════════════╝");
		System.out.println("");
		
		String prompt = """
		[1] Check In  [2] Check out  [3] view pending  [4] Accept pending
		>> Select an option: """;
		int inputOption = Validation.getOption(scanner, 4, prompt);

		switch(inputOption) {
			case 1:
				Guest inSelectedGuest = chooseGuest(scanner);
				if (inSelectedGuest == null) {
					System.out.println("Guest not found.");
					break;
				}


				viewRooms();
				System.out.println("please enter room number: ");
				int roomNumber= Integer.parseInt(scanner.nextLine().trim());
				Room selectedRoom=findRoom(roomNumber);
				if(selectedRoom==null){
					System.out.println("room not found:");
					break;
				}
				if (selectedRoom.getOccupied()) {
					System.out.println("room is already occupied");
					break;
				}

				Date inDate  = readDate(scanner, "Enter check-in date:");
				Date outDate;
				boolean validOutDate;
				do {
					validOutDate = true;
					outDate = readDate(scanner, "Enter check-out date:");
					if (outDate.before(inDate)){
						System.out.println("Outdate is before Indate , try again!");
						validOutDate = false;
					}
				}while (!validOutDate);


				checkin(inSelectedGuest,selectedRoom,inDate,outDate);
				break;
			case 2:
				Guest outSelectedGuest = chooseGuest(scanner);
				if (outSelectedGuest == null) {
					System.out.println("Guest not found.");
					break;
				}

				Reservation reservedGuestRoom=null;
				for (int i = 0; i < DataBase.reservations.size(); i++) {
					if(DataBase.reservations.get(i).getGuest().equals(outSelectedGuest) && DataBase.reservations.get(i).getReservationStatus().equals("CONFIRMED")){
						reservedGuestRoom=DataBase.reservations.get(i);
						break;
					}

				}
				if (reservedGuestRoom==null){
					System.out.println("no reserved room");
					break;
				}


				checkout(outSelectedGuest,reservedGuestRoom);
				break;
			case 3:
				viewPending();
				break;
			case 4:
				acceptPending();
				break;

			default  : System.out.println("Invalid number"); 		 return;
		}



	}

	private Guest chooseGuest(Scanner scanner){
		for(int i=0;i<DataBase.people.size();i++){
			if(DataBase.people.get(i)instanceof Guest){
				System.out.println("registered users:");
				System.out.println((i+1)+"-"+DataBase.people.get(i).getUserName());

			}
		}
		String name = Validation.getString(scanner, "Enter the guest's username: ");
		for(int i=0;i<DataBase.people.size();i++){
			if(DataBase.people.get(i)instanceof Guest && DataBase.people.get(i).getUserName().equals(name)){
				return ((Guest) DataBase.people.get(i));
			}
		}
		return (null);
	}

	private Room findRoom(int numberRoom){
		for(int i=0;i<DataBase.rooms.size();i++){
			if(DataBase.rooms.get(i).getRoomNumber()==numberRoom){
				return(DataBase.rooms.get(i));
			}
		}
		return (null);
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


	public boolean viewPending(){
		int size = DataBase.reservations.size();
		int counter = 0;
		for (int i = 0 ; i < size ; i++ ){
			if (DataBase.reservations.get(i).getReservationStatus() == "PENDING")
				counter++;
		}
		if (counter == 0){
			System.out.println("No pending requests!");
			return false;
		}
		for (int i = 0 ; i < size ; i++){
			if (DataBase.reservations.get(i).getReservationStatus() == "PENDING") {
				System.out.println("Request" + i + ": " + DataBase.reservations.get(i).getRoom().getRoomNumber()
						+ "From the guest: " + DataBase.reservations.get(i).getGuest().getUserName());
			}
		}
		return true;
	}


	public void acceptPending(){
		int i = 0;
		int size = DataBase.reservations.size();
		if(!viewPending())
			return;
		try {
			System.out.println("Enter the room you want to accept: ");
			int roomNumber = Integer.parseInt(in.nextLine().trim());
			Reservation newReservation = null;
			for (i = 0; i < size; i++) {
				if (DataBase.reservations.get(i).getRoom().getRoomNumber() == roomNumber) {
					newReservation = DataBase.reservations.get(i);
					break;
				}
			}
			if (newReservation == null) {
				System.out.println("Invalid Input! returning to main menu");
				acceptPending();
			}
			newReservation.setReservationStatus(Reservation.ReservationStatus.COMPLETED);
			DataBase.reservations.set(i, newReservation);
			System.out.println("Request was accepted!");
		}
		catch (NumberFormatException e){
			System.out.println("Input is not a number , Try again!");
			acceptPending();
		}
	}
}
