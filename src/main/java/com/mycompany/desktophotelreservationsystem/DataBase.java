package com.mycompany.desktophotelreservationsystem;
import java.util.*; 

public class DataBase {
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    static ArrayList<Invoice> invoices = new ArrayList<>();
    static ArrayList<User> people = new ArrayList<>();
    static ArrayList<Guest> guests = new ArrayList<>();
    static ArrayList<RoomType> roomTypes = new ArrayList<>();
    static ArrayList<Amenity> amenities = new ArrayList<>();


    DataBase(){

    }

    public static void demoFill() {
        /////////////////////////////////////////////////////////// PEOPLE
        Admin admin = new Admin("Ahmed", "67");
        Guest guest = new Guest("Baraa", "67");
        Receptionist receptionist = new Receptionist("Youssef", "67");
        people.add(admin);
        people.add(guest);
        people.add(receptionist);

        /////////////////////////////////////////////////////////// ROOM TYPES
        RoomType single = new RoomType("Single");
        RoomType couple = new RoomType("Double"); // "double" is a reserved word in java
        RoomType suite = new RoomType("Suite"); 
        roomTypes.add(single);
        roomTypes.add(couple);
        roomTypes.add(suite);

        /////////////////////////////////////////////////////////// AMENITIES
        Amenity pool = new Amenity("Pool", 50);
        Amenity gym = new Amenity("Gym", 30);
        Amenity coffee = new Amenity("Coffee Machine", 5);
        Amenity wifi = new Amenity("Free Wifi", 10);
        amenities.add(pool);
        amenities.add(gym);
        amenities.add(coffee);
        amenities.add(wifi);

        /////////////////////////////////////////////////////////// ROOM TYPES
        Room room067 = new Room(67, suite, 670).addAmenity(gym).addAmenity(coffee);
        Room room108 = new Room(108, single, 100).addAmenity(gym);
        Room room123 = new Room(123, couple, 150).addAmenity(wifi).addAmenity(pool);
        rooms.add(room067);
        rooms.add(room108);
        rooms.add(room123);
    }


}