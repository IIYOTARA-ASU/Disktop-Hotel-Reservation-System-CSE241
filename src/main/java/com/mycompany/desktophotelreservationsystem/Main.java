package com.mycompany.desktophotelreservationsystem;

import java.util.Scanner;

public class Main {
	static boolean appRunning = true;
	
	public static void guestMenu(User user) {
		System.out.println("Hi guest");
	}
	public static void adminMenu(User user) {
        System.out.println("=================================================================");
		System.out.println("Welcome, Admin " + user.userName + "!");
        // user.createAmenities(new Amenity("Pool", 50));
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
        while(appRunning) {
            User user = null;
            do { user = enterAccount();
            } while(user == null);

            // System.out.println(user.loggedIn);

            if(user instanceof Guest) {
                guestMenu(user);
            }else {
                adminMenu(user);
            }
        
        }
    }
}



