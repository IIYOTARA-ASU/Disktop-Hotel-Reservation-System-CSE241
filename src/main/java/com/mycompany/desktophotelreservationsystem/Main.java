package com.mycompany.desktophotelreservationsystem;

import java.util.Scanner;

public class Main {
	static boolean appRunning = true;
	private static final String BANNER =
		"╔═══════════════════════════════════════════════════════════════╗\n" +
		"║                  WELCOME TO THE PALISADE HOTEL                ║\n" +
		"╚═══════════════════════════════════════════════════════════════╝";

	private static final String SEPARATOR =
		"─────────────────────────────────────────────────────────────────";

	// ─────────────────────────────────────────────────────────────────────────
	//  Role menus
	// ─────────────────────────────────────────────────────────────────────────
	public static void guestMenu(User user) {
		System.out.println(SEPARATOR);
		Validation.centerText("WELCOME, GUEST " + user.getUserName().toUpperCase() + "!", 65);

		Guest guest = (Guest) user;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			guest.guestInterface();
			boolean continueLoop = Validation.getYesNo(scanner, ">> Continue? (y/n): ");
			if (!continueLoop) {
				System.out.println(">> Logging out...\n");
				break;
			}
		}
	}

	public static void adminMenu(User user) {
		System.out.println(SEPARATOR);
		Validation.centerText("WELCOME, ADMIN " + user.getUserName().toUpperCase() + "!", 65);

		Admin admin = (Admin) user;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			admin.adminInterface();
			boolean continueLoop = Validation.getYesNo(scanner, ">> Continue? (y/n): ");
			if (!continueLoop) {
				System.out.println(">> Logging out...\n");
				break;
			}
		}
	}

	public static void receptionistMenu(User user) {
		System.out.println(SEPARATOR);
		Validation.centerText("WELCOME, RECEPTIONIST " + user.getUserName().toUpperCase() + "!", 65);

		Receptionist receptionist = (Receptionist) user;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			receptionist.receptionistInterface();
			boolean continueLoop = Validation.getYesNo(scanner, ">> Continue? (y/n): ");
			if (!continueLoop) {
				System.out.println(">> Logging out...\n");
				break;
			}
		}
	}

	// ─────────────────────────────────────────────────────────────────────────
	//  Login / Register
	// ─────────────────────────────────────────────────────────────────────────
	public static User enterAccount() {
		Scanner scanner = new Scanner(System.in);
		User user = new User();

		System.out.println(BANNER);
		System.out.println();
		
		String prompt =
			"[1] Login   [2] Register   [3] exit\n" +
			">> Select an option: ";
		int inputOption = Validation.getOption(scanner, 3, prompt);
		String inputName;
		String inputPass;
		int inputType;
		switch (inputOption) {
			case 1: 
				
				user = user.login("","",false);    break;
			
			case 2:

				user = user.register("","",0,false); break;
			case 3:
				System.out.println("=========Goodbye=========");
				System.exit(0);
		}
		user.loggedIn = true;
		return user;
	}

	// ─────────────────────────────────────────────────────────────────────────
	//  Entry point
	// ────────────────────────────────────────────────────────────────────────-
	
	public static void main(String args[]) {
		User user = null;
		DataBase.demoFill();

		while (true) {
			do {
				user = enterAccount();
			} while (user == null);
			
			if      (user instanceof Guest)       { guestMenu(user); }
			else if (user instanceof Receptionist) { receptionistMenu(user); }
			else if (user instanceof Admin)        { adminMenu(user); }
		}
	}
}