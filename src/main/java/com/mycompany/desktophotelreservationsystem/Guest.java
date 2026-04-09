
package com.mycompany.desktophotelreservationsystem;
import java.util.*;

public class Guest extends User{
    double balance;
    String address;

    Guest() {
    }
    Guest(String username, String password){
        super(username, password);
    }

    public enum gender{MALE , FEMALE}
    RoomType roomChoice = new RoomType("Single");


    @Override
    public void viewRooms(){
    }
        
    public void makeReservation(){
    }
        
    @Override
    public void viewReservation(){  
    }
        
    public void cancelReservation(){
    }
        
    public void checkout(){
    }
        
    public void  payInvoice(){
    } 
        
        
}
