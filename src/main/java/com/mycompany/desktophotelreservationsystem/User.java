package com.mycompany.desktophotelreservationsystem;
import java.util.*;
public class User {
    User(){
    }
    
    User(String n, String p){
    	userName = n;
    	password = p;
    }
    
    String userName;
    String password;
    Date dateOfBirth;
    boolean loggedIn = false;

	public void viewRooms() {
		for (int i = 0; i < DataBase.rooms.size(); i++) {
			System.out.println("Room"+i+": "+ DataBase.rooms.get(i));
		}
	}
	
    
	public boolean login(User user) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("please enter username: ");
		String inputUser = input.nextLine();

		System.out.println("please enter password: ");
		String inputPass = input.nextLine();


		return DataBase.loginUser(inputUser,inputPass,user);
	}

	
	public User register() {
		
		User user;
		
		/////////////////////////////////////////////////////////// TAKE USER INPUTS START
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter a username: ");
		String inputUser = scanner.nextLine().trim();
		System.out.print("Enter a password: ");
		String inputPass = scanner.nextLine().trim();
		
		System.out.println("Choose account type");
		System.out.println("1 : Guest");
		System.out.println("2 : Admin");
		System.out.println("3 : Receptionist");
		String inputType = scanner.nextLine().trim();
		///////////////////////////////////////////////////////// TAKE USER INPUTS END
		
		//////////// CHECK USER TYPE START
	
		switch(inputType) {
		case "1" : 	user = new Guest(inputUser,inputPass);			   break;
		case "2" :	user = new Admin(inputUser,inputPass);		       break;
		case "3" : 	user = new Receptionist(inputUser,inputPass);	   break;
		default  : System.out.println("Invalid number"); 		 return null;
		}
		
		
		//////////// CHECK USER TYPE END
	
		/////////// SEARCH FOR NAMES IN DATABASE START

		System.out.println(DataBase.users.size());
		for(int i = 0 ; i < DataBase.users.size(); i++ ) {
			if((DataBase.users.get(i)).userName.equals(inputUser)) {
				System.out.println("Username already taken, try again");
				return null;
			}
		}
		
		/////////// SEARCH FOR NAMES IN DATABASE END
		
		/////////// ADD USER TO DATABASE START
		
		DataBase.users.add(user);
		System.out.println("User added successfully!");
		
		/////////// ADD USER TO DATABASE END
		
		return user;	

	}
	
	public void addUser(String inputUser, String inputPass,int type, User user) {

	}
	public void viewReservation() {
		for (int i = 0; i < DataBase.reservations.size(); i++) {
			System.out.println("reservation"+i+": "+ DataBase.reservations.get(i));
		}
	}
}
