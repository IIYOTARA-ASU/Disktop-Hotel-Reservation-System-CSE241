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

	public void viewGuests() {
		for (int i = 0; i < DataBase.people.size(); i++) {
			System.out.println("Guest"+i+": "+ DataBase.people.get(i));
		}
	}
}
