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
        System.out.println("");
        Scanner scanner = new Scanner(System.in);
        String inputUser;
        boolean usernameAlreadyExists;
        int userIndex = -1; // Initialize userIndex to an invalid value, It gets set to the correct index if the username is found
		
        /////////////////////////////////////////////////////////// TAKE AN EXISTING USERNAME 
        do {
            inputUser = Validation.getString(scanner, "Enter your username: ");
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
            String inputPass = Validation.getString(scanner, "Enter your password: ");
            correctPassword = DataBase.people.get(userIndex).password.equals(inputPass);
            if (!correctPassword) { System.out.println("Incorrect password, please try again"); }
        } while (!correctPassword);

        System.out.println("");
        return DataBase.people.get(userIndex); // Return the logged-in user object
	}

	public User register() {
        System.out.println("");
		User user;
		
		/////////////////////////////////////////////////////////// TAKE A VALID USERNAME 
		Scanner scanner = new Scanner(System.in);
        String inputUser;
        boolean usernameAlreadyExists;
		
        do {
            inputUser = Validation.getString(scanner, "Enter a username: ");
            
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
		String inputPass = Validation.getString(scanner, "Enter a password: ");


        /////////////////////////////////////////////////////////// TAKE VALID ACCOUNT TYPE
        int inputType = Validation.getOption(scanner, 3, "Choose account type: [1] Guest, [2] Admin, [3] Receptionist: ");    

    
        ///////////////////////////////////////////////////////// CHECK USER TYPE
		switch(inputType) {
		case 1 : 	user = new Guest(inputUser,inputPass);			   break;
		case 2 :	user = new Admin(inputUser,inputPass);		       break;
		case 3 : 	user = new Receptionist(inputUser,inputPass);	   break;
		default  : System.out.println("Invalid number"); 		 return null;
		}

		///////////////////////////////////////////////////////// ADD USER TO DATABASE
		DataBase.people.add(user);
		System.out.println("User added successfully!");
		return user;	

	}

    public void viewReservation() {
		for (int i = 0; i < DataBase.reservations.size(); i++) {
			System.out.println("reservation"+i+": "+ DataBase.reservations.get(i));
		}
	}

    public void displayRoomTable() {

		String format = "%-10s %-10s %-30s %8s%n";
		System.out.printf(format, "NUMBER", "TYPE", "AMENITIES", "PRICE");
		System.out.println("-------------------------------------------------------------");

		for (int i = 0; i < DataBase.rooms.size(); i++) {
			Room thisRoom = DataBase.rooms.get(i);
			int roomNumber = thisRoom.getRoomNumber();
			String roomtype = thisRoom.getRoomType().getRoomType();
			String roomPrice = thisRoom.getPrice() + "$";
			String amenities = "";
			for (int j = 0; j < thisRoom.getAmenities().size(); j++) {
				amenities += thisRoom.getAmenities().get(j).getName();
				amenities += thisRoom.getAmenities().size()-j != 1 ? ", " : ""; // Add a comma after each amenity except the last one
			}

			System.out.printf(format, roomNumber, roomtype, amenities, roomPrice);
		}
	}
	

}
