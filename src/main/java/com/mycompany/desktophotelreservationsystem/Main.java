package com.mycompany.desktophotelreservationsystem;
import java.io.FileInputStream;
import java.util.Scanner;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class Main {
		
	static boolean appRunning = true;

	// ─────────────────────────────────────────────────────────────────────────
	//  Role menus
	// ─────────────────────────────────────────────────────────────────────────
	public static void guestMenu(User user) {
		Validation.clearScreen();
		Validation.centerText("WELCOME, GUEST " + user.getUserName().toUpperCase() + "!", 65, false);
		
		Guest guest = (Guest) user;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			guest.guestInterface();
			boolean continueLoop = Validation.getYesNo(scanner, ">> Continue? (y/n): ");
			if (!continueLoop) {
				System.out.println(">> Logging out...\n");
				break;
			}
			Validation.clearScreen();
		}
	}

	public static void adminMenu(User user) {
		Validation.clearScreen();
		Validation.centerText("WELCOME, ADMIN " + user.getUserName().toUpperCase() + "!", 65, false);

		Admin admin = (Admin) user;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			admin.adminInterface();
			boolean continueLoop = Validation.getYesNo(scanner, ">> Continue? (y/n): ");
			if (!continueLoop) {
				System.out.println(">> Logging out...\n");
				break;
			}
			Validation.clearScreen();
		}
	}

	public static void receptionistMenu(User user) {
		Validation.clearScreen();
		Validation.centerText("WELCOME, RECEPTIONIST " + user.getUserName().toUpperCase() + "!", 65, false);

		Receptionist receptionist = (Receptionist) user;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			receptionist.receptionistInterface();
			boolean continueLoop = Validation.getYesNo(scanner, ">> Continue? (y/n): ");
			if (!continueLoop) {
				System.out.println(">> Logging out...\n");
				break;
			}
			Validation.clearScreen();
		}
	}

	// ─────────────────────────────────────────────────────────────────────────
	//  Login / Register
	// ─────────────────────────────────────────────────────────────────────────
	public static User enterAccount() {
		Scanner scanner = new Scanner(System.in);
		User user = new User();
		
		Validation.clearScreen();
		System.out.println(
			"╔═══════════════════════════════════════════════════════════════╗\n" +
			"║                  WELCOME TO THE PALISADE HOTEL                ║\n" +
			"╠═══════════════════════════════════════════════════════════════╣\n" +
			"║ [1] Log In              [2] Register             [3] Exit     ║\n" +
			"╚═══════════════════════════════════════════════════════════════╝"
		);

		String prompt = ">> Select an option: ";
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
				System.out.println("========= Goodbye =========");
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

		try {
            // 1. Point to your JSON key file
            FileInputStream serviceAccount = new FileInputStream("serviceAccountKey.json");

            // 2. Configure options with your specific Database URL
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://hotelreservationoop-default-rtdb.firebaseio.com/")
                .build();

            // 3. Initialize the app (The Handshake)
            if (FirebaseApp.getApps().isEmpty()) { 
                FirebaseApp.initializeApp(options);
            }
            
            System.out.println("[Firebase] Initialized Successfully!");

        } catch (Exception e) {
            System.err.println("Error initializing Firebase: " + e.getMessage());
            e.printStackTrace();
        }


		while (true) {
			do {
				user = enterAccount();
			} while (user == null);
			
			if      (user instanceof Guest)        { guestMenu(user); }
			else if (user instanceof Receptionist) { receptionistMenu(user); }
			else if (user instanceof Admin)        { adminMenu(user); }
		}
	}
}