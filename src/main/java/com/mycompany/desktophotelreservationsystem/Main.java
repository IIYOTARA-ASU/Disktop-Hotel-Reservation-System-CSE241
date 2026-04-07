package com.mycompany.desktophotelreservationsystem;
public class Main {
    public static void main(String args[]){
        System.out.println("Hello, world!");

        Guest guest = new Guest();
        Receptionist receptionist = new Receptionist();
        Room room = new Room();
        Admin admin = new Admin();
        admin.createRoom(room);
        admin.deleteRoom(room);
        guest.register();
        guest.login();

    }
}



