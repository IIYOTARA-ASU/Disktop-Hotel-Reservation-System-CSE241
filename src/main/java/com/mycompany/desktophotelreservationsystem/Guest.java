
package com.mycompany.desktophotelreservationsystem;
import java.util.*;

public class Guest extends User{
    double balance;
    String address;
    DataBase dataBase;

    Guest(DataBase dataBase) {
        super(dataBase);
    }

    public enum gender{MALE , FEMALE}
    RoomType roomChoice = new RoomType();

    @Override
    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a username: ");
        String inputUser = scanner.nextLine().trim();
        System.out.print("Enter a password: ");
        String inputPass = scanner.nextLine().trim();

        if (dataBase.registerUser(inputUser, inputPass)) {
            this.userName = inputUser;
            this.password = inputPass;
        }
    }

    @Override
    public boolean login() {
    	Scanner input = new Scanner(System.in);
        System.out.println("please enter username: ");
        String inputUser = input.nextLine();

        System.out.println("please enter password: ");
        String inputPass = input.nextLine();


        return dataBase.loginUser(inputUser,inputPass);//make sure that account exists
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
