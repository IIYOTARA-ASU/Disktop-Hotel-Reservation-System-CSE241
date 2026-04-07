package com.mycompany.desktophotelreservationsystem;
public abstract class Staff extends User {
    Staff(){
    }
    public enum roll {ADMIN, RECEPTIONIST}
    int workingHours;
    public abstract void viewGuests();
}
