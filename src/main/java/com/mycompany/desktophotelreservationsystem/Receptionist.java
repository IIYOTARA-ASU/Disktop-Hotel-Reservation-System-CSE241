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

		System.out.println("=================================================================");
		System.out.println("=========================== Receptionist MENU ==========================");
		System.out.println("RECEPTIONIST OPTIONS");
		System.out.println("[1] Check In  [2] Check out  ");


		String inputOption;
		boolean validOption;
		do {
			inputOption = scanner.nextLine().trim();
			validOption = inputOption.equals("1") || inputOption.equals("2") ;
			if (!validOption) { System.out.println("Invalid option, please try again"); }
		} while (!validOption);

		switch(inputOption) {
			case "1" :
				Guest selectedGuest = chooseGuest(scanner);
				if (selectedGuest == null) {
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
				Date outDate = readDate(scanner, "Enter check-out date:");

				checkin(selectedGuest,selectedRoom,inDate,outDate);
				break;
			case "2" :




				break;
			default  : System.out.println("Invalid number"); 		 return;
		}



	}

	private Guest chooseGuest(Scanner scanner){
		for(int i=0;i<DataBase.people.size();i++){
			if(DataBase.people.get(i)instanceof Guest){
				System.out.println("registered users:");
				System.out.println((i+1)+"-"+DataBase.people.get(i).userName);

			}
		}
		System.out.println("please enter the Guest's username:");
		String name = scanner.nextLine().trim();
		for(int i=0;i<DataBase.people.size();i++){
			if(DataBase.people.get(i)instanceof Guest && DataBase.people.get(i).userName.equals(name)){
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
		System.out.print("Day: ");   int d = Integer.parseInt(scanner.nextLine().trim());
		System.out.print("Month: "); int m = Integer.parseInt(scanner.nextLine().trim());
		System.out.print("Year: ");  int y = Integer.parseInt(scanner.nextLine().trim());

		Calendar cal = Calendar.getInstance();
		cal.set(y, m - 1, d); // month is like an array fa lazem tezabat el index
		return cal.getTime();
	}
}
