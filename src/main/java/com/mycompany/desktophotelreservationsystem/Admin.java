package com.mycompany.desktophotelreservationsystem;

import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends Staff {
	Admin() {
	}

	Admin(String username, String password) {
		super(username, password);
	}

	// ############################### ROOM CRUD ###############################
	
	public void createRoom() { 
		Scanner scanner = new Scanner(System.in);

		/////////////////////////////////////////////////////////// ROOM NUMBER
		int newRoomNumber = getValidInt(scanner, ">> Enter room number: ");

		for (int i = 0; i < DataBase.rooms.size(); i++) {
			if (DataBase.rooms.get(i).getRoomNumber() == newRoomNumber) {
				System.out.println("   [Error] Room number already exists. Please enter a unique room number.");
				return;
			}
		}

		/////////////////////////////////////////////////////////// ROOM TYPE
		StringBuilder typePrompt = new StringBuilder(">> Enter room type ");
		for (int i = 0; i < DataBase.roomTypes.size(); i++) {
			typePrompt.append("[").append(i + 1).append("] ").append(DataBase.roomTypes.get(i).getRoomType());
			if (DataBase.roomTypes.size() - i != 1) { typePrompt.append(" "); }
			else { typePrompt.append(": "); }
		}
		int newRoomTypeID = getValidOption(scanner, DataBase.roomTypes.size(), typePrompt.toString());
		RoomType existingRoomType = DataBase.roomTypes.get(newRoomTypeID - 1);


		/////////////////////////////////////////////////////////// ROOM PRICE
		int newRoomPrice = getValidInt(scanner, ">> Enter room price: ");


		/////////////////////////////////////////////////////////// CREATING ROOM
		DataBase.rooms.add(new Room(newRoomNumber, existingRoomType, newRoomPrice));
		Room NewRoom = DataBase.rooms.get(DataBase.rooms.size() - 1);


		/////////////////////////////////////////////////////////// ROOM AMENITIES
		System.out.println(">> Room Amenities Checklist: ");
		for (int i = 0; i < DataBase.amenities.size(); i++) {
			boolean hasAmenity = getValidYesNo(scanner, "   Should the room have " + DataBase.amenities.get(i).getName() + "? (y/n): ");
			if (hasAmenity) {
				NewRoom.addAmenity(DataBase.amenities.get(i));
			}
		}

		/////////////////////////////////////////////////////////// SUCCESS MESSAGE
		System.out.println("ROOM CREATED SUCCESSFULLY");
	}

	public void updateRoom(){
		Scanner scanner = new Scanner(System.in);
		displayRoomTable();
		System.out.println("");

		/////////////////////////////////////////////////////////// ROOM NUMBER
		int updateIndex = -1;
		int updateRoomNumber;

		do{
			updateRoomNumber = getValidInt(scanner, "Enter room number to update: ");

			for (int i = 0; i < DataBase.rooms.size(); i++) {
				if (DataBase.rooms.get(i).getRoomNumber() == updateRoomNumber) {
					updateIndex = i;
					break;
				}
			}

			if (updateIndex == -1) {System.out.println("   [Error] Room not found. Please enter a valid existing room number.");}
		} while (updateIndex == -1);


		/////////////////////////////////////////////////////////// ROOM TYPE
		StringBuilder typePrompt = new StringBuilder(">> Enter room type ");
		for (int i = 0; i < DataBase.roomTypes.size(); i++) {
			typePrompt.append("[").append(i + 1).append("] ").append(DataBase.roomTypes.get(i).getRoomType());
			if (DataBase.roomTypes.size() - i != 1) { typePrompt.append(" "); }
			else { typePrompt.append(": "); }
		}
		int updatedRoomTypeID = getValidOption(scanner, DataBase.roomTypes.size(), typePrompt.toString());
		RoomType existingRoomType = DataBase.roomTypes.get(updatedRoomTypeID - 1);

		int updatedPrice = getValidInt(scanner, "Enter new price: ");


		/////////////////////////////////////////////////////////// UPDATING ROOM
		Room UpdatedRoom = DataBase.rooms.get(updateIndex);
		UpdatedRoom.setRoomNumber(updateRoomNumber);
		UpdatedRoom.setRoomType(existingRoomType);
		UpdatedRoom.setPrice(updatedPrice);
		UpdatedRoom.getAmenities().clear();

		/////////////////////////////////////////////////////////// ROOM AMENITIES
		System.out.println(">> Room Amenities Checklist: ");
		for (int i = 0; i < DataBase.amenities.size(); i++) {
			boolean hasAmenity = getValidYesNo(scanner, "   Should the room have " + DataBase.amenities.get(i).getName() + "? (y/n): ");
			if (hasAmenity) {
				UpdatedRoom.addAmenity(DataBase.amenities.get(i));
			}
		}

		/////////////////////////////////////////////////////////// SUCCESS MESSAGE
		System.out.println("Room updated successfully");
	}
	
	public void deleteRoom(){ 
		Scanner scanner = new Scanner(System.in);
		displayRoomTable();
		System.out.println("");

		int deletedIndex = -1;
		int deletedRoomNumber;

		do{
			deletedRoomNumber = getValidInt(scanner, "Enter room number to delete: ");

			for (int i = 0; i < DataBase.rooms.size(); i++) {
				if (DataBase.rooms.get(i).getRoomNumber() == deletedRoomNumber) {
					deletedIndex = i;
					break;
				}
			}

			if (deletedIndex == -1) {System.out.println("   [Error] Room not found. Please enter a valid existing room number.");}
		} while (deletedIndex == -1);


		for (int i = 0; i < DataBase.reservations.size(); i++) {
			Reservation reservation = DataBase.reservations.get(i);
			if (reservation.getRoom().getRoomNumber() == deletedRoomNumber && reservation.isReservationActive()) {
				System.out.println("   [Error] Cannot delete room " + deletedRoomNumber + " because it is currently reserved by guest " + reservation.getGuest().getUserName());
				return;
			}
		}
		DataBase.rooms.remove(deletedIndex);
		System.out.println("Room deleted successfully");

	}

	// ############################### AMENITIES CRUD ###############################

	public void createAmenities() {
		Scanner scanner = new Scanner(System.in);

		/////////////////////////////////////////////////////////// AMENITY NAME
		String newAmenityName = getNonEmptyString(scanner, "Enter amenity name: ");

		for (int i = 0; i < DataBase.amenities.size(); i++) {
			Amenity existingAmenity = DataBase.amenities.get(i);
			if (existingAmenity.getName().equalsIgnoreCase(newAmenityName)) {
				System.out.println("Amenity with the same name already exists. Updating its price.");
				return;
			}
		}

		/////////////////////////////////////////////////////////// AMENITY PRICE
		double newAmenityPrice = getValidDouble(scanner, "Enter amenity price: ");

		
		/////////////////////////////////////////////////////////// SUCCESS MESSAGE
		DataBase.amenities.add(new Amenity(newAmenityName, newAmenityPrice));
		System.out.println("Amenity added successfully!");
	}

	public void updateAmenities() {
		Scanner scanner = new Scanner(System.in);
		displayAmenitiesTable();
		System.out.println("");
		int updateAmenityId = -1;
		
		/////////////////////////////////////////////////////////// AMENITY ID
		do{
			updateAmenityId = getValidInt(scanner, "Enter amenity ID to update: ");

			if (updateAmenityId < 0 || updateAmenityId >= DataBase.amenities.size()) {
				System.out.println("   [Error] Amenity not found. Please enter a valid existing amenity ID.");
				updateAmenityId = -1;
			}
		} while (updateAmenityId == -1);
		
		Amenity updatedAmenity = DataBase.amenities.get(updateAmenityId);


		/////////////////////////////////////////////////////////// AMENITY PRICE
		double updatedAmenityPrice = getValidDouble(scanner, "Enter new price: ");
		updatedAmenity.setPrice(updatedAmenityPrice);


		/////////////////////////////////////////////////////////// SUCCESS MESSAGE
		System.out.println("Amenity updated successfully.");
	}

	public void deleteAmenities() {
		Scanner scanner = new Scanner(System.in);
		displayAmenitiesTable();
		System.out.println("");

		int deletedAmenityId = -1;

		do {
			deletedAmenityId = getValidInt(scanner, "Enter amenity ID to delete: ");

			if (deletedAmenityId < 0 || deletedAmenityId >= DataBase.amenities.size()) {
				System.out.println("   [Error] Amenity not found. Please enter a valid existing amenity ID.");
				deletedAmenityId = -1;
			}
		} while (deletedAmenityId == -1);

		Amenity deletedAmenity = DataBase.amenities.get(deletedAmenityId);

		// Remove the amenity from all rooms
		for (int i = 0; i < DataBase.rooms.size(); i++) {
			DataBase.rooms.get(i).getAmenities().removeIf(amenity -> amenity.equals(deletedAmenity));
		}

		// Remove the amenity from the database
		DataBase.amenities.remove(deletedAmenity);


		System.out.println("Amenity deleted successfully and removed from all rooms.");
	}

	// ############################### ROOM TYPES CRUD ###############################

	public void createRoomTypes() {
		Scanner scanner = new Scanner(System.in);

		/////////////////////////////////////////////////////////// ROOM TYPE NAME
		String newTypeName = getNonEmptyString(scanner, "Enter new room type name: ");

		for (int i = 0; i < DataBase.roomTypes.size(); i++) {
			if (DataBase.roomTypes.get(i).getRoomType().equalsIgnoreCase(newTypeName)) {
				System.out.println("   [Error] Room type already exists.");
				return;
			}
		}

		/////////////////////////////////////////////////////////// SUCCESS MESSAGE
		DataBase.roomTypes.add(new RoomType(newTypeName));
		System.out.println("Room type added successfully!");
	}

	public void updateRoomTypes() {
		Scanner scanner = new Scanner(System.in);
		displayRoomTypesTable();
		System.out.println("");

		int updateTypeId = -1;

		/////////////////////////////////////////////////////////// ROOM TYPE ID
		do {
			updateTypeId = getValidInt(scanner, "Enter room type ID to update: ");

			if (updateTypeId < 0 || updateTypeId >= DataBase.roomTypes.size()) {
				System.out.println("   [Error] Room type not found. Please enter a valid existing ID.");
				updateTypeId = -1;
			}
		} while (updateTypeId == -1);

		RoomType updatedType = DataBase.roomTypes.get(updateTypeId);

		/////////////////////////////////////////////////////////// NEW NAME
		String newName = getNonEmptyString(scanner, "Enter new name for this room type: ");
		updatedType.setRoomType(newName);

		/////////////////////////////////////////////////////////// SUCCESS MESSAGE
		System.out.println("Room type updated successfully.");
	}

	public void deleteRoomTypes() {
		Scanner scanner = new Scanner(System.in);
		displayRoomTypesTable();
		System.out.println("");

		int deletedTypeId = -1;

		/////////////////////////////////////////////////////////// ROOM TYPE ID
		do {
			deletedTypeId = getValidInt(scanner, "Enter room type ID to delete: ");

			if (deletedTypeId < 0 || deletedTypeId >= DataBase.roomTypes.size()) {
				System.out.println("   [Error] Room type not found. Please enter a valid existing ID.");
				deletedTypeId = -1;
			}
		} while (deletedTypeId == -1);

		RoomType typeToDelete = DataBase.roomTypes.get(deletedTypeId);

		// Check if any rooms are currently using this type
		for (int i = 0; i < DataBase.rooms.size(); i++) {
			if (DataBase.rooms.get(i).getRoomType().equals(typeToDelete)) {
				System.out.println("   [Error] Cannot delete type '" + typeToDelete.getRoomType() + "' because it is assigned to Room " + DataBase.rooms.get(i).getRoomNumber());
				return;
			}
		}

		/////////////////////////////////////////////////////////// SUCCESS MESSAGE
		DataBase.roomTypes.remove(deletedTypeId);
		System.out.println("Room type deleted successfully.");
	}
	
	//################################ DISPLAY TABLES ##########################
	
	private void displayRoomTable() {

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
	
	private void displayAmenitiesTable() {
		if (DataBase.amenities.isEmpty()) { System.out.println("No amenities found."); return; }
		String format = "%-5s %-25s %10s%n";
		System.out.printf(format, "ID", "NAME", "PRICE");
		System.out.println("------------------------------------------");
		for (int i = 0; i < DataBase.amenities.size(); i++) {
			Amenity a = DataBase.amenities.get(i);
			System.out.printf(format, i, a.getName(), a.getprice() + "$");
		}
	}
	
	private void displayRoomTypesTable() {
		if (DataBase.roomTypes.isEmpty()) { System.out.println("No room types found."); return; }
		String format = "%-5s %-20s%n";
		System.out.printf(format, "ID", "TYPE NAME"); //id is simply the index bas kont 3ayez esm a7san
		System.out.println("-------------------------");
		for (int i = 0; i < DataBase.roomTypes.size(); i++) {
			System.out.printf(format, i, DataBase.roomTypes.get(i).getRoomType());
		}
	}

	// ############################### INTERFACE ###############################

	public void adminInterface() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("┌───────────────────────────────────────────────────────────────┐");
		System.out.println("│                           ADMIN MENU                          │");
		System.out.println("└───────────────────────────────────────────────────────────────┘");
		System.out.println("[====================== MANAGEMENT OPTIONS =====================]");
		
		String prompt = "             [1] Rooms  [2] Amenities  [3] Room Types\n>> Select an option: ";
		int inputOption = getValidOption(scanner, 3, prompt);
		
		switch(inputOption) {
			case 1 :    roomsMenu(scanner);                       break;
			case 2 :    amenitiesMenu(scanner);                   break;
			case 3 :    roomTypesMenu(scanner);                   break;
			default :   System.out.println("Invalid number");  return;
		}
	}

	private void roomsMenu(Scanner scanner) {
		String prompt = "[1] View  [2] Add  [3] Update  [4] Delete\n>> Select an option: ";
		int option = getValidOption(scanner, 4, prompt);

		switch (option) {
			case 1: displayRoomTable();       break;
			case 2: createRoom();             break;
			case 3: updateRoom();             break;
			case 4: deleteRoom();             break;	
		}
	}


	private void amenitiesMenu(Scanner scanner) {
		String prompt = "[1] View  [2] Add  [3] Update  [4] Delete\n>> Select an option: ";
		int option = getValidOption(scanner, 4, prompt);

		switch (option) {
			case 1: displayAmenitiesTable();  break;
			case 2: createAmenities();        break;
			case 3: updateAmenities();        break;
			case 4: deleteAmenities();        break;
		}
	}

	private void roomTypesMenu(Scanner scanner) {
		String prompt = "[1] View  [2] Add  [3] Update  [4] Delete\n>> Select an option: ";
		int option = getValidOption(scanner, 4, prompt);

		switch (option) {
			case 1: displayRoomTypesTable();  break;
			case 2: createRoomTypes();        break;
			case 3: updateRoomTypes();        break;
			case 4: deleteRoomTypes();        break;
		}
	}

	// ############################### VALIDATION HELPERS ###############################

	private int getValidInt(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim();
			if (input.isEmpty()) {
				System.out.println("   [Error] Input cannot be empty. Please enter a number.");
				continue;
			}
			try {
				return Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("   [Error] Invalid input. Please enter a valid integer.");
			}
		}
	}

	private double getValidDouble(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim();
			if (input.isEmpty()) {
				System.out.println("   [Error] Input cannot be empty. Please enter a number.");
				continue;
			}
			try {
				return Double.parseDouble(input);
			} catch (NumberFormatException e) {
				System.out.println("   [Error] Invalid input. Please enter a valid decimal number.");
			}
		}
	}

	private String getNonEmptyString(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim();
			if (!input.isEmpty()) {
				return input;
			}
			System.out.println("   [Error] Input cannot be empty. Please try again.");
		}
	}
	
	private int getValidOption(Scanner scanner, int maxOption, String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim();
			if (input.isEmpty()) {
				System.out.println("   [Error] Input cannot be empty. Please enter a number between 1 and " + maxOption + ".");
				continue;
			}
			try {
				int option = Integer.parseInt(input);
				if (option >= 1 && option <= maxOption) {
					return option;
				} else {
					System.out.println("   [Error] Invalid option. Please enter a number between 1 and " + maxOption + ".");
				}
			} catch (NumberFormatException e) {
				System.out.println("   [Error] Invalid input. Please enter a valid number between 1 and " + maxOption + ".");
			}
		}
	}

	private boolean getValidYesNo(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim().toLowerCase();
			if (input.equals("y")) {
				return true;
			} else if (input.equals("n")) {
				return false;
			} else {
				System.out.println("   [Error] Invalid input. Please enter 'y' or 'n'.");
			}
		}
	}

}