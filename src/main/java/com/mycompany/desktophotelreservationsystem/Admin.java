package com.mycompany.desktophotelreservationsystem;

import java.util.Scanner;

import com.mycompany.desktophotelreservationsystem.RoomType.roomtype;

public class Admin extends Staff {
	Admin() {
	}

	Admin(String username, String password) {
		super(username, password);
	}

	// ############################### ABILITIES ###############################//eh kol el method deeeh
	public void createRoom(Room room)                            { DataBase.rooms.add(room); }
	public void readRoom(int index)                             { DataBase.rooms.get(index); }
	public void updateRoom(int index, Room room)                { DataBase.rooms.set(index, room); }
	public void deleteRoom(Room room)                           { DataBase.rooms.remove(room); }

	public void createAmenities(Amenity amenity)                { DataBase.amenities.add(amenity); System.out.println("Amenity added successfully!"); }
	public void readAmenities(int index)                        { DataBase.amenities.get(index); }
	public void updateAmenities(int index, Amenity amenity)     { DataBase.amenities.set(index, amenity); }
	public void deleteAmenities(Amenity amenity)                { DataBase.amenities.remove(amenity); }

	public void createRoomTypes(RoomType roomType)              { DataBase.roomTypes.add(roomType); }
	public void readRoomTypes(int index)                        { DataBase.roomTypes.get(index); }
	public void updateRoomTypes(int index, RoomType roomType)   { DataBase.roomTypes.set(index, roomType); }
	public void deleteRoomTypes(RoomType roomType)              { DataBase.roomTypes.remove(roomType); }


	// ############################### INTERFACE ###############################
	private void displayRoomTable() {

        String format = "%-10s %-10s %-30s %8s%n"; // Some Gemini Sauce for a well formatted table
        System.out.printf(format, "NUMBER", "TYPE", "AMENITIES", "PRICE");
        System.out.println("-------------------------------------------------------------");
        
		for (int i = 0; i < DataBase.rooms.size(); i++) {
			Room thisRoom = DataBase.rooms.get(i);
			int roomNumber = thisRoom.getRoomNumber();
			String roomtype = thisRoom.getRoomType().getRoomTypeString();
			String roomPrice = thisRoom.getPrice() + "$";
			String amenities = "";
			for (int j = 0; j < thisRoom.getAmenities().size(); j++) {
				amenities += thisRoom.getAmenities().get(j).getName();
				amenities += thisRoom.getAmenities().size()-j != 1 ? ", " : ""; // Add a comma after each amenity except the last one
			}

			System.out.printf(format, roomNumber, roomtype, amenities, roomPrice);
		}
	}

	public void adminInterface() {
		Scanner scanner = new Scanner(System.in);
		int choice;


		System.out.println("=================================================================");
		System.out.println("=========================== ADMIN MENU ==========================");
		System.out.println("MANAGEMENT OPTIONS]");
		System.out.println("[1] Rooms  [2] Amenities  [3] Room Types");
		
		String inputOption;
		boolean validOption;
		do {
			inputOption = scanner.nextLine().trim();
			validOption = inputOption.equals("1") || inputOption.equals("2") || inputOption.equals("3");
			if (!validOption) { System.out.println("Invalid option, please try again"); }
		} while (!validOption);

		switch(inputOption) {
			case "1" :
				roomsMenu(scanner);
				break;
			case "2" :	amenitiesMenu(scanner);			break;
			case "3" : 	roomTypesMenu(scanner); 				break;
			default  : System.out.println("Invalid number"); 		 return;
		}
	}

	private void roomsMenu(Scanner scanner) {
		System.out.println("[1] View  [2] Add  [3] Update  [4] Delete");
		String option = getValidOption(scanner, "1", "2", "3", "4");

		switch (option) {
			case "1": displayRoomTable(); break;
			case "2":
				System.out.print("Enter room number: ");
				int newRoomNumber = Integer.parseInt(scanner.nextLine().trim());
				System.out.print("Enter room type (Single / Double / Suite): ");
				String newRoomType = scanner.nextLine().trim();
				System.out.print("Enter room price: ");
				int newRoomPrice = Integer.parseInt(scanner.nextLine().trim());
				createRoom(new Room(newRoomNumber, new RoomType(newRoomType), newRoomPrice));
				System.out.println("Room added successfully");
				break;
			case "3":
				displayRoomTable();
				System.out.print("Enter room number to update: ");
				int updateRoomNumber = Integer.parseInt(scanner.nextLine().trim());
				int updateIndex = -1;

				for (int i = 0; i < DataBase.rooms.size(); i++) {
					if (DataBase.rooms.get(i).getRoomNumber() == updateRoomNumber) {
						updateIndex = i;
						break; }
				}

				if (updateIndex == -1) {
					System.out.println("Room not found.");
					break;
				}


				System.out.print("Enter new room type (Single / Double / Suite): ");
				String updatedType = scanner.nextLine().trim();
				System.out.print("Enter new price: ");
				int updatedPrice = Integer.parseInt(scanner.nextLine().trim());
				updateRoom(updateIndex, new Room(updateRoomNumber, new RoomType(updatedType), updatedPrice));

				System.out.println("Room updated successfully");
				break;

			case "4":
				displayRoomTable();
				System.out.print("Enter room number to delete: ");
				int deleteRoomNumber = Integer.parseInt(scanner.nextLine().trim());
				Room roomToDelete = null;

				for (int i = 0; i < DataBase.rooms.size(); i++) {
					if (DataBase.rooms.get(i).getRoomNumber() == deleteRoomNumber) {
						roomToDelete = DataBase.rooms.get(i);
						break;
					}
				}

				if (roomToDelete == null) {
					System.out.println("Room not found.");
					break;
				}

				deleteRoom(roomToDelete);
				System.out.println("Room deleted successfully");

				break;
		}
	}



	private void amenitiesMenu(Scanner scanner) {
		System.out.println("[1] View  [2] Add  [3] Update  [4] Delete");
		String option = getValidOption(scanner, "1", "2", "3", "4");

		switch (option) {
			case "1": /*read amenity logic */ break;
			case "2":
				System.out.print("Enter amenity name: ");
				String newAmenityName = scanner.nextLine().trim();
				System.out.print("Enter amenity price: ");
				double newAmenityPrice = Double.parseDouble(scanner.nextLine().trim());
				createAmenities(new Amenity(newAmenityName, newAmenityPrice));
				System.out.println("Amenity added successfully!");
				break;
			case "3": /* update amenity logic */ break;
			case "4": /* delete amenity logic */ break;
		}
	}

	private void roomTypesMenu(Scanner scanner) {
		System.out.println("[1] View  [2] Add  [3] Update  [4] Delete");
		String option = getValidOption(scanner, "1", "2", "3", "4");

		switch (option) {
			case "1": ; break;
			case "2": /* add room type logic */ break;
			case "3": /* update room type logic */ break;
			case "4": /* delete room type logic */ break;
		}
	}

	private String getValidOption(Scanner scanner, String... validOptions) {
		String input;
		boolean valid;
		do {
			input = scanner.nextLine().trim();
			valid = false;
			for (int i = 0; i < validOptions.length; i++) {
				if (input.equals(validOptions[i])) { valid = true; break; }
			}
			if (!valid) { System.out.println("Invalid option, please try again"); }
		} while (!valid);
		return input;
	}
}
