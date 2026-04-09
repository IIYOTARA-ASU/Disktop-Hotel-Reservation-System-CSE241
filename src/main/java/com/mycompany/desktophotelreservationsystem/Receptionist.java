package com.mycompany.desktophotelreservationsystem;

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
}
