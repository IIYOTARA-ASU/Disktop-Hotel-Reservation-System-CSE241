package com.mycompany.desktophotelreservationsystem;

import java.util.Scanner;

public class Receptionist extends Staff {
	DataBase dataBase;

	Receptionist(DataBase dataBase) {
		super(dataBase);
	}

	@Override
	public void viewGuests() {
		for (int i = 0; i < dataBase.guests.size(); i++) {
			System.out.println("Guest"+i+": "+ dataBase.guests.get(i));
		}

	}

	@Override
	public void viewRooms() {
		for (int i = 0; i < dataBase.rooms.size(); i++) {
			System.out.println("Room"+i+": "+ dataBase.rooms.get(i));
		}

	}

	@Override
	public void viewReservation() {
		for (int i = 0; i < dataBase.reservations.size(); i++) {
			System.out.println("reservation"+i+": "+ dataBase.reservations.get(i));
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


		return dataBase.loginUser(inputUser,inputPass);//make sure that account exists

	}

	@Override
	public void register() {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter a username: ");
		String inputUser = scanner.nextLine().trim();
		System.out.print("Enter a password: ");
		String inputPass = scanner.nextLine().trim();

		if (dataBase.registerUser(inputUser, inputPass)) {
			this.userName = inputUser;
			this.password = inputPass;
		}
	}


    
    
}
