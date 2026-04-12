package com.mycompany.desktophotelreservationsystem;

import java.util.Scanner;


public abstract class Staff extends User {
    Scanner in = new Scanner(System.in);
    Staff(){
    }
    Staff(String n, String p){
        super(n,p);
    }
    public enum roll {ADMIN, RECEPTIONIST}
    int workingHours;


    public void acceptResevation(Reservation reservation){
        int input;
        do {
            System.out.println("Do you want to accept this reservation of guest"+reservation.getGuest().getUserName()+":\n1: Yes\n2: No\n");
            input = in.nextInt();
        }while(input != 1 && input != 2);
        if (input == 1) {
           reservation.setReservationStatus(Reservation.ReservationStatus.CONFIRMED);
            System.out.println("Reservation Operation Complete");
        }
        if (input == 2) {
            reservation.setReservationStatus(Reservation.ReservationStatus.CANCELLED);
            System.out.println("Reservation Operation Cancelled");
        }
    }

	public void viewGuests() {
		for (int i = 0; i < DataBase.people.size(); i++) {
			System.out.println("Guest"+i+": "+ DataBase.people.get(i));
		}
	}
}
