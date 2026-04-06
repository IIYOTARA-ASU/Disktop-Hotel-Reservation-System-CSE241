
package com.mycompany.desktophotelreservationsystem;
import java.util.*;

public class Guest extends User{
    double balance;
    String address;
     public enum gender{MALE , FEMALE}
     RoomType roomChoice = new RoomType();

     @Override
    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a username: ");
        String inputUser = scanner.nextLine().trim();
        System.out.print("Enter a password: ");
        String inputPass = scanner.nextLine().trim();

        if (Database.registerUser(inputUser, inputPass)) {
            this.userName = inputUser;
            this.password = inputPass;
        }
    }

    @Override
    public boolean login() {
        System.out.println("please enter username: ");
        String inputUser = scanner.nextLine();

        System.out.println("please enter password: ");
        String inputPass = scanner.nextLine();

        return inputUser.equals(this.userName) && inputPass.equals(this.password);//make sure that account exists
    }


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
