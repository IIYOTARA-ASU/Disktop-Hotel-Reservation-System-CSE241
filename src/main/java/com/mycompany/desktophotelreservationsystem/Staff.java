package com.mycompany.desktophotelreservationsystem;
public abstract class Staff extends User {
    public enum roll {ADMIN, RECEPTIONIST}
    int workingHours;
    public abstract void viewGuests();
}
