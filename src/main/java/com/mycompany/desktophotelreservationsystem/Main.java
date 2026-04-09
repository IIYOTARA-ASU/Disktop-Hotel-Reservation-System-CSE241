package com.mycompany.desktophotelreservationsystem;

import java.util.Scanner;

public class Main {
	static boolean appRunning = true;

	public static void guestMenu(User user) {
		System.out.println("Hi guest");
        System.out.println("=================================================================");
        System.out.println("Welcome, Guest " + user.userName + "!");
        Guest guest = (Guest) user;
        while(true){
        guest.guestInterface();
        Scanner scanner=new Scanner(System.in);
            System.out.println("please enter if you want to continue:");
        boolean keepGoingTemp=scanner.nextBoolean();
        if(!keepGoingTemp)
            break;
        }
	}
	public static void adminMenu(User user) {
        System.out.println("=================================================================");
		System.out.println("Welcome, Admin " + user.userName + "!");
        Admin admin = (Admin) user;
        admin.adminInterface();
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

        do { user = enterAccount();
        } while(user == null);


        if(user instanceof Guest) {
            guestMenu(user);
        }
        else if(user instanceof Receptionist) {
            System.out.println("Hi, receptionist");
        }
        else if(user instanceof Admin) {
            adminMenu(user);
        }
    }
}



