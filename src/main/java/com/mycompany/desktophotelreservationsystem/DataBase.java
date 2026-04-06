package com.mycompany.desktophotelreservationsystem;
import java.util.*;//better
public class DataBase {
    ArrayList<Guest> guests = new ArrayList<>();
    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Reservation> reservations = new ArrayList<>();
    ArrayList<Invoice> invoices = new ArrayList<>();
    ArrayList<Staff> staff = new ArrayList<>();
    //ahmed arraylists feeha commands 7elwa fa45 eba esta5demhom


    static ArrayList<String> usernames = new ArrayList<>();//array list for registering and loging in
    static ArrayList<String> passwords = new ArrayList<>();//a place to store users instead of files
    //leave lists and methods static because need to call using class and they are shared by all objecrs anyways

    Database(){
    guests.add(new Guest());
    rooms.add(new Room());
    reservations.add(new Reservation());
    invoices.add(new Invoice());
    staff.add(new Admin());
    }

    //leave lists and methods static because need to call using class and they are shared by all objecrs anyways

    public static boolean registerUser(String username, String password){
        if(username.contains(username)){
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
    }


    public static boolean loginUser(String username, String passsword){
    int index= usernames.indexof(username);
    if (index!=-1&& passwords.get(index).equals(passsword)){//because indexof momken terga3 -1
        System.out.println("login successful welcome, "+ username);
        return true;

    }else{
        System.out.println("invalid username or password please try again");
        return false;
    }


}


}