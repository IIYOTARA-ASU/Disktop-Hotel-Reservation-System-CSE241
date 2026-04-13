package com.mycompany.desktophotelreservationsystem;

import java.util.Scanner;

public class Main {
	static boolean appRunning = true;

	public static void guestMenu(User user) {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
		System.out.println("║                  WELCOME TO THE PALISADE HOTEL                ║");
		System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");
		System.out.println("─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─");
		Validation.centerText(">> WELCOME, GUEST " + user.getUserName().toUpperCase() + "!", 65);
		Guest guest = (Guest) user;
		while(true){
			guest.guestInterface();
			Scanner scanner=new Scanner(System.in);
			boolean continueLoop = Validation.getYesNo(scanner, "Do you want to do something else? (y/n): ");
			if(!continueLoop) {
				System.out.println("\n>> Logging out\n");
				break;
			}
		}

	}
	public static void adminMenu(User user) {
        System.out.println("─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─");
		Validation.centerText(">> WELCOME, ADMIN " + user.getUserName().toUpperCase() + "!", 65);
		Admin admin = (Admin) user;
		while(true){
			admin.adminInterface();
			Scanner scanner=new Scanner(System.in);
			boolean continueLoop = Validation.getYesNo(scanner, "Do you want to do something else? (y/n): ");
			if(!continueLoop) {
				System.out.println("\n>> Logging out\n");
				break;
			}
		}
	}


    public static void receptionistMenu(User user) {
        System.out.println("─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─");
		Validation.centerText(">> WELCOME, RECEPTIONIST " + user.getUserName().toUpperCase() + "!", 65);
		Receptionist receptionist = (Receptionist) user;
		while(true){
			receptionist.receptionistInterface();
			Scanner scanner=new Scanner(System.in);
			boolean continueLoop = Validation.getYesNo(scanner, "Do you want to do something else? (y/n): ");
			if(!continueLoop) {
				System.out.println("\n>> Logging out\n");
				break;
			}
		}
    }

	public static User enterAccount() {
        Scanner scanner = new Scanner(System.in);
        User user = new User();
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
		System.out.println("║                  WELCOME TO THE PALISADE HOTEL                ║");
		System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");
		System.out.println("");
        String prompt = """
		[1] Login  [2] Register
		>> Select an option: """;
		int inputOption = Validation.getOption(scanner, 2, prompt);
        
        switch(inputOption) {
        case 1 : user = user.login(); break;
        case 2 : user = user.register(); break; }
        user.loggedIn = true;
        return user;
	}
	
    public static void main(String args[]){
        User user = null;
        DataBase.demoFill(); // Initialize the database with some default data
        while (true){
        do { user = enterAccount();
        } while(user == null);


        if(user instanceof Guest) {
            guestMenu(user);
        }
        else if(user instanceof Receptionist) {
            receptionistMenu(user);
        }
        else if(user instanceof Admin) {
            adminMenu(user);
        }
        }
    }
}



