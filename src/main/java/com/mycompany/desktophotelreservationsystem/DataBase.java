package com.mycompany.desktophotelreservationsystem;
import java.util.*;//better
public class DataBase {
    static ArrayList<Guest> guests = new ArrayList<>();
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    static ArrayList<Invoice> invoices = new ArrayList<>();
    static ArrayList<Staff> staff = new ArrayList<>();
    static ArrayList<Amenity> amenities  = new ArrayList<>();
    static ArrayList<RoomType> roomTypes  = new ArrayList<>();
    //ahmed arraylists feeha commands 7elwa fa45 eba esta5demhom


    static ArrayList<String> usernames = new ArrayList<>(List.of("youssef"));//array list for registering and loging in
    static ArrayList<String> passwords = new ArrayList<>(List.of("hell")); //a place to store users instead of files
    //leave lists and methods static because need to call using class and they are shared by all objecrs anyways

    DataBase(){

    }

    //leave lists and methods static because need to call using class ,and they are shared by all objecrs anyways

    public static boolean registerUser(String username, String password){

        if(usernames.contains(username)){
            System.out.println("username already exists please choose another");
            return false;
        }
        else{
            usernames.add(username);
            passwords.add(password);
            System.out.println("Registration successful Welcome, " + username);
            return true;
        }
    }


    public static boolean loginUser(String username, String password){
    int index= usernames.indexOf(username);
    if (index!=-1&& passwords.get(index).equals(password)){//because indexof momken terga3 -1
        System.out.println("login successful welcome, "+ username);
        return true;

    }else{
        System.out.println("invalid username or password please try again");
        return false;
    }


}


}