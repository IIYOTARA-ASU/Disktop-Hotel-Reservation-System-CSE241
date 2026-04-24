package com.mycompany.desktophotelreservationsystem;

import java.util.*;

public class User implements users {

	public User() {
	}

	public User(String n, String p) {
		userName = n;
		password = p;
	}

	public String userName;
	private String password;
	private String rfidId;
	private Date dateOfBirth;
	boolean loggedIn = false;
	private static String adminCode = "badrEhabByakolFera5Masloo2a";
	String inputCode;
	
	public String getRfidId() {
		return rfidId;
	}

	public void setRfidId(String rfidId) {
		this.rfidId = rfidId;
	}
	public static String getAdminCode() {
		return adminCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	// ─────────────────────────────────────────────────────────────────────────
	// Room display
	// ─────────────────────────────────────────────────────────────────────────
	public void viewRooms() {
		displayRoomTable();
	}

	public void displayRoomTable() {
		if (DataBase.rooms.isEmpty()) {
			System.out.println("   [Info] No rooms available.");
			return;
		}

		String format = "%-10s %-12s %-28s %8s%n";
		System.out.println();
		System.out.printf(format, "NUMBER", "TYPE", "AMENITIES", "PRICE");
		System.out.println("─────────────────────────────────────────────────────────────────");

		for (int i = 0; i < DataBase.rooms.size(); i++) {
			Room room = DataBase.rooms.get(i);
			String amenities = "";
			for (int j = 0; j < room.getAmenities().size(); j++) {
				amenities += room.getAmenities().get(j).getName();
				if (room.getAmenities().size() - j != 1) {
					amenities += ", ";
				}
			}
			System.out.printf(format,
					room.getRoomNumber(),
					room.getRoomType().getRoomType(),
					amenities,
					room.getPrice() + "$");
		}
		System.out.println("─────────────────────────────────────────────────────────────────");
	}

	// ─────────────────────────────────────────────────────────────────────────
	// Reservation display
	// ─────────────────────────────────────────────────────────────────────────
	public void viewReservation() {
		if (DataBase.reservations.isEmpty()) {
			System.out.println("   [Info] No reservations on record.");
			return;
		}
		for (int i = 0; i < DataBase.reservations.size(); i++) {
			System.out.println("   Reservation " + (i + 1) + ": " + DataBase.reservations.get(i));
		}
	}

	// ─────────────────────────────────────────────────────────────────────────
	// Login
	// ─────────────────────────────────────────────────────────────────────────
	public User login(String inputUser, String inputPass, boolean GUI) {
		System.out.println();
		Scanner scanner = new Scanner(System.in);
		boolean usernameFound;
		int userIndex = -1;

		do {
			if (!GUI) {
				inputUser = Validation.getString(scanner, ">> Enter your username: ");
			} else if (GUI) {
				usernameFound = false;
				for (int i = 0; i < DataBase.people.size(); i++) {
					if (DataBase.people.get(i).userName.equals(inputUser)) {
						usernameFound = true;
						userIndex = i;
					}
				}
				if (!usernameFound) {
					System.out.println("   [Error] Username not found. Please try again.");
					return null;
				}

			}

			usernameFound = false;
			for (int i = 0; i < DataBase.people.size(); i++) {
				if (DataBase.people.get(i).userName.equals(inputUser)) {
					usernameFound = true;
					userIndex = i;
				}
			}
			if (!usernameFound) {
				System.out.println("   [Error] Username not found. Please try again.");
			}
		} while (!usernameFound);

		boolean correctPassword;
		do {
			if (!GUI) {
				inputPass = Validation.getString(scanner, ">> Enter your password: ");
			} else {
				correctPassword = DataBase.people.get(userIndex).password.equals(inputPass);
				if (!correctPassword) {
					System.out.println("   [Error] Incorrect password. Please try again.");
				}
				;
				break;
			}

			correctPassword = DataBase.people.get(userIndex).password.equals(inputPass);
			if (!correctPassword) {
				System.out.println("   [Error] Incorrect password. Please try again.");
			}
		} while (!correctPassword);

		System.out.println("   [OK] Login successful.\n");
		return DataBase.people.get(userIndex);
	}

	// ─────────────────────────────────────────────────────────────────────────
	// Register
	// ─────────────────────────────────────────────────────────────────────────
	public User register(String inputUser, String inputPass, int inputType, boolean GUI) {
		System.out.println();
		Scanner scanner = new Scanner(System.in);
		User user;

		boolean usernameAlreadyExists;

		do {
			if (!GUI) {
				inputUser = Validation.getString(scanner, ">> Enter a username: ");
			} else {
				usernameAlreadyExists = false;
				for (int i = 0; i < DataBase.people.size(); i++) {
					if (DataBase.people.get(i).userName.equals(inputUser)) {
						usernameAlreadyExists = true;
						System.out.println("   [Error] Username already taken. Please try another.");
						return null;
					}
				}
			}
			usernameAlreadyExists = false;
			for (int i = 0; i < DataBase.people.size(); i++) {
				if (DataBase.people.get(i).userName.equals(inputUser)) {
					usernameAlreadyExists = true;
					System.out.println("   [Error] Username already taken. Please try another.");
				}
			}
		} while (usernameAlreadyExists);

		if (!GUI) {
			inputPass = Validation.getString(scanner, ">> Enter a password: ");
		}

		if (!GUI) {
			inputType = Validation.getOption(scanner, 3,
					">> Account type  [1] Guest  [2] Admin  [3] Receptionist: ");
		}

		switch (inputType) {
			case 1:
				if (!GUI) {
					double balance = Validation.getDouble(scanner, ">> Enter Account Balance : ");
					user = new Guest(inputUser, inputPass);
					((Guest) user).setBalance(balance);
					break;
				}

			case 2:

				inputCode = Validation.getString(scanner, "Enter Admin Code : ");
				if (inputCode.equals(adminCode)) {
					user = new Admin(inputUser, inputPass);
				} else {
					System.out.println("   [Error] You are not authorised to make a Staff account... GET OUT");
					System.exit(0);
					return null;
				}
				break;
			case 3:
				if (!GUI) {
					inputCode = Validation.getString(scanner, "Enter Admin Code : ");
					if (inputCode.equals(adminCode)) {
						user = new Receptionist(inputUser, inputPass);
					} else {
						System.out.println("   [Error] You are not authorised to make a Staff account... GET OUT");
						System.exit(0);
						return null;
					}
					int workingHours = Validation.getIntInRange(scanner, ">> Enter Working Hours (0~15) : ", 0, 15);
					((Receptionist) user).setWorkingHours(workingHours);
					break;
				}
			default:
				System.out.println("   [Error] Invalid option.");
				return null;
		}

		DataBase.people.add(user);
		System.out.println("   [OK] Account created successfully!\n");
		return user;

	}
	// take user inputs for login / register

	public void logOut(User user) {
		System.out.println("Logged out successfully!");
		System.out.println("////////////////////////////////////////");
		user = null;

		while (true) {
			do {
				user = Main.enterAccount();
			} while (user == null);

			if (user instanceof Guest) {
				Main.guestMenu(user);
			} else if (user instanceof Receptionist) {
				Main.receptionistMenu(user);
			} else if (user instanceof Admin) {
				Main.adminMenu(user);
			}
		}
	}


}