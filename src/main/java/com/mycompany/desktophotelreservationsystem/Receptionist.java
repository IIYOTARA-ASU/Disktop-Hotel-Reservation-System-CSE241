package com.mycompany.desktophotelreservationsystem;

import java.util.Scanner;

public class Receptionist extends Staff {
	Receptionist() {
	}

	@Override
	public void viewGuests() {
		for (int i = 0; i < DataBase.guests.size(); i++) {
			System.out.println("Guest"+i+": "+ DataBase.guests.get(i));
		}

	}

	@Override
	public void viewRooms() {
		for (int i = 0; i < DataBase.rooms.size(); i++) {
			System.out.println("Room"+i+": "+ DataBase.rooms.get(i));
		}

	}

	@Override
	public void viewReservation() {
		for (int i = 0; i < DataBase.reservations.size(); i++) {
			System.out.println("reservation"+i+": "+ DataBase.reservations.get(i));
		}
	}


	@Override
	public boolean login() {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("please enter username: ");
		String inputUser = input.nextLine();

		System.out.println("please enter password: ");
		String inputPass = input.nextLine();


		return DataBase.loginUser(inputUser,inputPass);//make sure that account exists

	}

	@Override
	public void register() {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter a username: ");
		String inputUser = scanner.nextLine().trim();
		System.out.print("Enter a password: ");
		String inputPass = scanner.nextLine().trim();

		if (DataBase.registerUser(inputUser, inputPass)) {
			this.userName = inputUser;
			this.password = inputPass;
		}
	}


    
    
}
