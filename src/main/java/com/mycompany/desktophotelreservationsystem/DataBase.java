package com.mycompany.desktophotelreservationsystem;
import java.util.*;//better
public class DataBase {
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    static ArrayList<Invoice> invoices = new ArrayList<>();
    static ArrayList<User> people = new ArrayList<>();
    static ArrayList<RoomType> roomTypes = new ArrayList<>();
    static ArrayList<Amenity> amenities = new ArrayList<>();

    //ahmed arraylists feeha commands 7elwa fa45 eba esta5demhom


//    static ArrayList<String> usernames = new ArrayList<>();//array list for registering and loging in
//    static ArrayList<String> passwords = new ArrayList<>();//a place to store users instead of files
    //leave lists and methods static because need to call using class and they are shared by all objecrs anyways

    DataBase(){

    }

    //leave lists and methods static because need to call using class ,and they are shared by all objecrs anyways


    public static boolean loginUser(String username, String password,User user){
//    int index= usernames.indexOf(username);
//    if (index!=-1&& passwords.get(index).equals(password)){//because indexof momken terga3 -1
//        System.out.println("login successful welcome, "+ username);
//        user = users.get(index);
//        return true;
//
//    }else{
//        System.out.println("invalid username or password please try again");
//        return false;
//    }

    	return true;
}


}