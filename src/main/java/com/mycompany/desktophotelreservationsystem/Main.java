package com.mycompany.desktophotelreservationsystem;

import java.util.Scanner;

public class Main {
	static boolean appRunning = true;

	public static void guestMenu(User user) {
        System.out.println("=================================================================");
        System.out.println("Welcome, Guest " + user.userName + "!");
        Guest guest = (Guest) user;
        while(true){
        guest.guestInterface();
        Scanner scanner=new Scanner(System.in);
            System.out.println("Do you want to do something else? (yes/no):");
            String keepGoing = scanner.nextLine().trim();
        if(keepGoing.equalsIgnoreCase("no")) {
            System.out.println("logging out");
            break;
        }
        }

	}
	public static void adminMenu(User user) {
        System.out.println("=================================================================");
        System.out.println("Welcome, Admin " + user.userName + "!");
        Admin admin = (Admin) user;
        while(true){
            admin.adminInterface();
            Scanner scanner=new Scanner(System.in);
            System.out.println("Do you want to do something else? (yes/no):");
            String keepGoing = scanner.nextLine().trim();
            if(keepGoing.equalsIgnoreCase("no")) {
                System.out.println("logging out");
                break;
            }
        }
	}


    public static void receptionistMenu(User user) {
        System.out.println("=================================================================");
        System.out.println("Welcome, Receptionist " + user.userName + "!");
        Receptionist receptionist = (Receptionist) user;
        while(true){
            receptionist.receptionistInterface();
            Scanner scanner=new Scanner(System.in);
            System.out.println("Do you want to do something else? (yes/no):");
            String keepGoing = scanner.nextLine().trim();
            if(keepGoing.equalsIgnoreCase("no")) {
                System.out.println("logging out");
                break;
            }
        }
    }

	public static User enterAccount() {
        Scanner input = new Scanner(System.in);
        User user = new User();
        System.out.println("=================================================================");
        System.out.println("Welcome to The Palisade Hotel!");
        System.out.println("1 : Login ");
        System.out.println("2 : Register ");
        String choice = input.next();
        switch(choice) {
        case "1" : user = user.login(); break;
        case "2" : user = user.register(); break;
        }
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



