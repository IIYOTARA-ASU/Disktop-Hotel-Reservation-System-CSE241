package com.mycompany.desktophotelreservationsystem;
import java.util.*;
public class User {
    User(){}
    
    User(String n, String p){
    	userName = n;
    	password = p;
    }
    
    private String userName;
    private String password;
    private Date dateOfBirth;
    boolean loggedIn = false;

    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}
    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
    public Date getDateOfBirth() {return dateOfBirth;}
    public void setDateOfBirth(Date dateOfBirth) {this.dateOfBirth = dateOfBirth;}

    public void viewRooms() {
		for (int i = 0; i < DataBase.rooms.size(); i++) {
			System.out.println("Room"+i+": "+ DataBase.rooms.get(i).getRoomNumber());
		}
	}
	
	public User login() {
        Scanner scanner = new Scanner(System.in);
        String inputUser;
        boolean usernameAlreadyExists;
        int userIndex = -1; // Initialize userIndex to an invalid value, It gets set to the correct index if the username is found
		
        /////////////////////////////////////////////////////////// TAKE AN EXISTING USERNAME 
        do {
            System.out.print("Enter your username: ");
            inputUser  = scanner.nextLine().trim();
            usernameAlreadyExists = false;
            for(int i = 0 ; i < DataBase.people.size(); i++ ) {
                if((DataBase.people.get(i)).userName.equals(inputUser)) {
                    usernameAlreadyExists = true;
                    userIndex = i; // Store the index of the user with the matching username
                }
            }
            if (!usernameAlreadyExists) { System.out.println("Username not found, please try again"); } 
        } while (!usernameAlreadyExists);

        /////////////////////////////////////////////////////////// TAKE USER PASSWORD
        boolean correctPassword;
        do {
            System.out.print("Enter your password: ");
            String inputPass = scanner.nextLine().trim();
            correctPassword = DataBase.people.get(userIndex).password.equals(inputPass);
            if (!correctPassword) { System.out.println("Incorrect password, please try again"); }
        } while (!correctPassword);

        System.out.println("Login successful, welcome " + inputUser);
        return DataBase.people.get(userIndex); // Return the logged-in user object
	}

	public User register() {
		User user;
		
		/////////////////////////////////////////////////////////// TAKE A VALID USERNAME 
		Scanner scanner = new Scanner(System.in);
        String inputUser;
        boolean usernameAlreadyExists;
		
        do {
            System.out.print("Enter a username: ");
            inputUser  = scanner.nextLine().trim();
            
            // System.out.println("DEBUG] Number of users in database: " + DataBase.people.size());
            usernameAlreadyExists = false;
            for(int i = 0 ; i < DataBase.people.size(); i++ ) {
                if((DataBase.people.get(i)).userName.equals(inputUser)) {
                    usernameAlreadyExists = true;
                    System.out.println("Username already taken, please try again");
                }
            } 
        } while (usernameAlreadyExists);


        /////////////////////////////////////////////////////////// TAKE USER PASSWORD
		System.out.print("Enter a password: ");
		String inputPass = scanner.nextLine().trim();


        /////////////////////////////////////////////////////////// TAKE VALID ACCOUNT TYPE
		String inputType;
        boolean validType;

        do {
            System.out.println("Choose account type");
            System.out.println("1 : Guest");
            System.out.println("2 : Admin");
            System.out.println("3 : Receptionist");
            inputType = scanner.nextLine().trim();
            validType = inputType.equals("1") || inputType.equals("2") || inputType.equals("3");
            if (!validType) { System.out.println("Invalid account type, please try again"); }
        } while (!validType);

        ///////////////////////////////////////////////////////// CHECK USER TYPE
		switch(inputType) {
		case "1" : 	user = new Guest(inputUser,inputPass);			   break;
		case "2" :	user = new Admin(inputUser,inputPass);		       break;
		case "3" : 	user = new Receptionist(inputUser,inputPass);	   break;
		default  : System.out.println("Invalid number"); 		 return null;
		}

		///////////////////////////////////////////////////////// ADD USER TO DATABASE
		DataBase.people.add(user);
		System.out.println("User added successfully!");
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
