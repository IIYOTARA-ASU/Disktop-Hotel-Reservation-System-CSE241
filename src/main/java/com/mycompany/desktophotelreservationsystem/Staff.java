package com.mycompany.desktophotelreservationsystem;
public abstract class Staff extends User {
    Staff(){
    }
    Staff(String n, String p){
    	super(n,p);
    }
    public enum roll {ADMIN, RECEPTIONIST}
    int workingHours;
	public void viewGuests() {
		for (int i = 0; i < DataBase.guests.size(); i++) {
			System.out.println("Guest"+i+": "+ DataBase.guests.get(i));
		}
	}
}
