package com.mycompany.desktophotelreservationsystem;
public class Main {
    public static void main(String args[]){
        System.out.println("Hello, world!");
        DataBase dataBase = new DataBase();
        Guest guest=new Guest(dataBase);
        Amenity amenity = new Amenity();
        Invoice invoice = new Invoice();
        Receptionist receptionist = new Receptionist(dataBase);
        Room room = new Room();
        RoomType roomType = new RoomType();
        Admin admin = new Admin(dataBase);
        admin.createRoom(room);
        admin.deleteRoom(room);
        guest.register();
        guest.login();

    }
}
