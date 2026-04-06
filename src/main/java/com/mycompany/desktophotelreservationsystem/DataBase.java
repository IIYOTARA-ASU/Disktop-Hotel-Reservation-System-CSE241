package com.mycompany.desktophotelreservationsystem;
import java.util.*;//better
public class DataBase {
    ArrayList<Guest> guests = new ArrayList<>();
    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Reservation> reservations = new ArrayList<>();
    ArrayList<Invoice> invoices = new ArrayList<>();
    ArrayList<Staff> staff = new ArrayList<>();
    
    Database(){
    guests.add(new Guest());
    rooms.add(new Room());
    reservations.add(new Reservation());
    invoices.add(new Invoice());
    staff.add(new Admin());
    }
}