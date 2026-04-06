
package com.mycompany.desktophotelreservationsystem;

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
