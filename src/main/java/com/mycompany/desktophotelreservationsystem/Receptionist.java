package com.mycompany.desktophotelreservationsystem;

import java.util.Scanner;

public class Receptionist extends Staff {
	DataBase dataBase;

	@Override
	public void viewGuests() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewRooms() {
		// TODO Auto-generated method stub
		
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

	@Override
	public void viewReservation() {
		// TODO Auto-generated method stub
		
	}
    
    
}
