package com.mycompany.desktophotelreservationsystem;

import java.util.Scanner;

import com.mycompany.desktophotelreservationsystem.RoomType.roomtype;

public class Admin extends Staff {
	Admin() {
	}

	Admin(String username, String password) {
		super(username, password);
	}

	// ############################### ABILITIES ###############################
	public void createRoom(Room room) {
		DataBase.rooms.add(room);
	}

	public void createAmenities(Amenity amenity) {
		DataBase.amenities.add(amenity);
		System.out.println("Amenity added successfully!");
	}

	public void createRoomTypes(RoomType roomType) {
		DataBase.roomTypes.add(roomType);
	}

	public void readRoom(int index) {
		DataBase.rooms.get(index);
	}

	public void readAmenities(int index) {
		DataBase.amenities.get(index);
	}

	public void readRoomTypes(int index) {
		DataBase.roomTypes.get(index);
	}

	public void updateRoom(int index, Room room) {
		DataBase.rooms.set(index, room);
	}

	public void updateAmenities(int index, Amenity amenity) {
		DataBase.amenities.set(index, amenity);
	}

	public void updateRoomTypes(int index, RoomType roomType) {
		DataBase.roomTypes.set(index, roomType);
	}

	public void deleteRoom(Room room) {
		DataBase.rooms.remove(room);
	}

	public void deleteAmenities(Amenity amenity) {
		DataBase.amenities.remove(amenity);
	}

	public void deleteRoomTypes(RoomType roomType) {
		DataBase.roomTypes.remove(roomType);
	}

	// ############################### INTERFACE ###############################
	private void displayRoomTable() {

        String format = "%-10s %-10s %-30s %8s%n"; // Some Gemini Sauce for a well formatted table
        System.out.printf(format, "NUMBER", "TYPE", "AMINITIES", "PRICE");
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
			case "1" : 	displayRoomTable();			break;
			case "2" :	System.out.println("Amenities:") ;				break;
			case "3" : 	System.out.println("Room Types:") ; 				break;
			default  : System.out.println("Invalid number"); 		 return;
		}
	}

}
