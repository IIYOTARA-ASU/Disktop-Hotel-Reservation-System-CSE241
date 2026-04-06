package com.mycompany.desktophotelreservationsystem;
public abstract class Staff extends User {
    DataBase dataBase;
    Staff(DataBase dataBase){
       super(dataBase);
    }
    public enum roll {ADMIN, RECEPTIONIST}
    int workingHours;
    public abstract void viewGuests();
}
