package com.mycompany.desktophotelreservationsystem;

import java.util.Scanner;

public class Admin extends Staff implements users{

	Admin() {}
	Admin(String username, String password) { super(username, password); }

	// =========================================================================
	//  ROOM CRUD
	// =========================================================================

	public void createRoom() {
		Scanner scanner = new Scanner(System.in);

		// ── Room number ───────────────────────────────────────────────────────
		int newRoomNumber = Validation.getInt(scanner, ">> Enter room number: ");
		for (int i = 0; i < DataBase.rooms.size(); i++) {
			if (DataBase.rooms.get(i).getRoomNumber() == newRoomNumber) {
				System.out.println("   [Error] Room number already exists. Please enter a unique room number.");
				return;
			}
		}

		// ── Room type ─────────────────────────────────────────────────────────
		int newRoomTypeID      = Validation.getOption(scanner, DataBase.roomTypes.size(), buildTypePrompt());
		RoomType existingType  = DataBase.roomTypes.get(newRoomTypeID - 1);

		// ── Room price ────────────────────────────────────────────────────────
		int newRoomPrice = Validation.getInt(scanner, ">> Enter room price: ");

		// ── Create room ───────────────────────────────────────────────────────
		DataBase.rooms.add(new Room(newRoomNumber, existingType, newRoomPrice));
		Room newRoom = DataBase.rooms.get(DataBase.rooms.size() - 1);

		// ── Amenities checklist ───────────────────────────────────────────────
		System.out.println(">> Room Amenities Checklist:");
		System.out.println("   ─────────────────────────────────────────────");
		for (int i = 0; i < DataBase.amenities.size(); i++) {
			boolean has = Validation.getYesNo(scanner,
				"   Include " + DataBase.amenities.get(i).getName() + "? (y/n): ");
			if (has) { newRoom.addAmenity(DataBase.amenities.get(i)); }
		}

		System.out.println("   [OK] Room created successfully.");
	}

	public void updateRoom() {
		Scanner scanner = new Scanner(System.in);
		displayRoomTable();

		// ── Choose room ───────────────────────────────────────────────────────
		int updateIndex = -1;
		int updateRoomNumber;
		do {
			updateRoomNumber = Validation.getInt(scanner, ">> Enter room number to update: ");
			for (int i = 0; i < DataBase.rooms.size(); i++) {
				if (DataBase.rooms.get(i).getRoomNumber() == updateRoomNumber) { updateIndex = i; break; }
			}
			if (updateIndex == -1) {
				System.out.println("   [Error] Room not found. Please enter a valid room number.");
			}
		} while (updateIndex == -1);

		// ── Room type ─────────────────────────────────────────────────────────
		int updatedTypeID     = Validation.getOption(scanner, DataBase.roomTypes.size(), buildTypePrompt());
		RoomType existingType = DataBase.roomTypes.get(updatedTypeID - 1);

		// ── Room price ────────────────────────────────────────────────────────
		int updatedPrice = Validation.getInt(scanner, ">> Enter new price: ");

		// ── Apply changes ─────────────────────────────────────────────────────
		Room updatedRoom = DataBase.rooms.get(updateIndex);
		updatedRoom.setRoomNumber(updateRoomNumber);
		updatedRoom.setRoomType(existingType);
		updatedRoom.setPrice(updatedPrice);
		updatedRoom.getAmenities().clear();

		// ── Amenities checklist ───────────────────────────────────────────────
		System.out.println(">> Room Amenities Checklist:");
		System.out.println("   ─────────────────────────────────────────────");
		for (int i = 0; i < DataBase.amenities.size(); i++) {
			boolean has = Validation.getYesNo(scanner,
				"   Include " + DataBase.amenities.get(i).getName() + "? (y/n): ");
			if (has) { updatedRoom.addAmenity(DataBase.amenities.get(i)); }
		}

		System.out.println("   [OK] Room updated successfully.");
	}

	public void deleteRoom() {
		Scanner scanner = new Scanner(System.in);
		displayRoomTable();

		int deletedIndex = -1;
		int deletedRoomNumber;
		do {
			deletedRoomNumber = Validation.getInt(scanner, ">> Enter room number to delete: ");
			for (int i = 0; i < DataBase.rooms.size(); i++) {
				if (DataBase.rooms.get(i).getRoomNumber() == deletedRoomNumber) { deletedIndex = i; break; }
			}
			if (deletedIndex == -1) {
				System.out.println("   [Error] Room not found. Please enter a valid room number.");
			}
		} while (deletedIndex == -1);

		for (int i = 0; i < DataBase.reservations.size(); i++) {
			Reservation r = DataBase.reservations.get(i);
			if (r.getRoom().getRoomNumber() == deletedRoomNumber && r.isReservationActive()) {
				System.out.println("   [Error] Cannot delete room " + deletedRoomNumber
					+ " — it is currently reserved by guest " + r.getGuest().getUserName() + ".");
				return;
			}
		}

		DataBase.rooms.remove(deletedIndex);
		System.out.println("   [OK] Room deleted successfully.");
	}

	// =========================================================================
	//  AMENITIES CRUD
	// =========================================================================

	public void createAmenities() {
		Scanner scanner = new Scanner(System.in);

		String newName = Validation.getString(scanner, ">> Enter amenity name: ");
		for (int i = 0; i < DataBase.amenities.size(); i++) {
			if (DataBase.amenities.get(i).getName().equalsIgnoreCase(newName)) {
				System.out.println("   [Error] An amenity with that name already exists.");
				return;
			}
		}

		double newPrice = Validation.getDouble(scanner, ">> Enter amenity price: ");
		DataBase.amenities.add(new Amenity(newName, newPrice));
		System.out.println("   [OK] Amenity added successfully.");
	}

	public void updateAmenities() {
		Scanner scanner = new Scanner(System.in);
		displayAmenitiesTable();

		int updateId = -1;
		do {
			updateId = Validation.getInt(scanner, ">> Enter amenity ID to update: ");
			if (updateId < 0 || updateId >= DataBase.amenities.size()) {
				System.out.println("   [Error] Amenity not found. Please enter a valid ID.");
				updateId = -1;
			}
		} while (updateId == -1);

		double updatedPrice = Validation.getDouble(scanner, ">> Enter new price: ");
		DataBase.amenities.get(updateId).setPrice(updatedPrice);
		System.out.println("   [OK] Amenity updated successfully.");
	}

	public void deleteAmenities() {
		Scanner scanner = new Scanner(System.in);
		displayAmenitiesTable();

		int deletedId = -1;
		do {
			deletedId = Validation.getInt(scanner, ">> Enter amenity ID to delete: ");
			if (deletedId < 0 || deletedId >= DataBase.amenities.size()) {
				System.out.println("   [Error] Amenity not found. Please enter a valid ID.");
				deletedId = -1;
			}
		} while (deletedId == -1);

		Amenity toDelete = DataBase.amenities.get(deletedId);
		for (int i = 0; i < DataBase.rooms.size(); i++) {
			DataBase.rooms.get(i).getAmenities().removeIf(a -> a.equals(toDelete));
		}
		DataBase.amenities.remove(toDelete);
		System.out.println("   [OK] Amenity deleted and removed from all rooms.");
	}

	// =========================================================================
	//  ROOM TYPES CRUD
	// =========================================================================

	public void createRoomTypes() {
		Scanner scanner = new Scanner(System.in);

		String newTypeName = Validation.getString(scanner, ">> Enter new room type name: ");
		for (int i = 0; i < DataBase.roomTypes.size(); i++) {
			if (DataBase.roomTypes.get(i).getRoomType().equalsIgnoreCase(newTypeName)) {
				System.out.println("   [Error] Room type already exists.");
				return;
			}
		}

		DataBase.roomTypes.add(new RoomType(newTypeName));
		System.out.println("   [OK] Room type added successfully.");
	}

	public void updateRoomTypes() {
		Scanner scanner = new Scanner(System.in);
		displayRoomTypesTable();

		int updateId = -1;
		do {
			updateId = Validation.getInt(scanner, ">> Enter room type ID to update: ");
			if (updateId < 0 || updateId >= DataBase.roomTypes.size()) {
				System.out.println("   [Error] Room type not found. Please enter a valid ID.");
				updateId = -1;
			}
		} while (updateId == -1);

		String newName = Validation.getString(scanner, ">> Enter new name for this room type: ");
		DataBase.roomTypes.get(updateId).setRoomType(newName);
		System.out.println("   [OK] Room type updated successfully.");
	}

	public void deleteRoomTypes() {
		Scanner scanner = new Scanner(System.in);
		displayRoomTypesTable();

		int deletedId = -1;
		do {
			deletedId = Validation.getInt(scanner, ">> Enter room type ID to delete: ");
			if (deletedId < 0 || deletedId >= DataBase.roomTypes.size()) {
				System.out.println("   [Error] Room type not found. Please enter a valid ID.");
				deletedId = -1;
			}
		} while (deletedId == -1);

		RoomType toDelete = DataBase.roomTypes.get(deletedId);
		for (int i = 0; i < DataBase.rooms.size(); i++) {
			if (DataBase.rooms.get(i).getRoomType().equals(toDelete)) {
				System.out.println("   [Error] Cannot delete type '" + toDelete.getRoomType()
					+ "' — it is assigned to Room " + DataBase.rooms.get(i).getRoomNumber() + ".");
				return;
			}
		}

		DataBase.roomTypes.remove(deletedId);
		System.out.println("   [OK] Room type deleted successfully.");
	}

	// =========================================================================
	//  DISPLAY TABLES
	// =========================================================================

	private void displayAmenitiesTable() {
		if (DataBase.amenities.isEmpty()) { System.out.println("   [Info] No amenities on record."); return; }
		String format = "%-5s %-25s %10s%n";
		System.out.println();
		System.out.printf(format, "ID", "NAME", "PRICE");
		System.out.println("─────────────────────────────────────────────");
		for (int i = 0; i < DataBase.amenities.size(); i++) {
			Amenity a = DataBase.amenities.get(i);
			System.out.printf(format, i, a.getName(), a.getPrice() + "$");
		}
		System.out.println("─────────────────────────────────────────────");
	}

	private void displayRoomTypesTable() {
		if (DataBase.roomTypes.isEmpty()) { System.out.println("   [Info] No room types on record."); return; }
		String format = "%-5s %-20s%n";
		System.out.println();
		System.out.printf(format, "ID", "TYPE NAME");
		System.out.println("─────────────────────────");
		for (int i = 0; i < DataBase.roomTypes.size(); i++) {
			System.out.printf(format, i, DataBase.roomTypes.get(i).getRoomType());
		}
		System.out.println("─────────────────────────");
	}

	// =========================================================================
	//  INTERFACE
	// =========================================================================

	public void adminInterface() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("╔═══════════════════════════════════════════════════════════════╗");
		System.out.println("║                           ADMIN MENU                          ║");
		System.out.println("╚═══════════════════════════════════════════════════════════════╝");
		System.out.println();

		int inputOption = Validation.getOption(scanner, 4,
			"[1] Rooms  [2] Amenities  [3] Room Types  [4]Exit \n>> Select an option: ");

		System.out.println();

		switch (inputOption) {
			case 1: roomsMenu(scanner);     break;
			case 2: amenitiesMenu(scanner); break;
			case 3: roomTypesMenu(scanner); break;
			case 4: logOut(this); break;
		}
	}

	private void roomsMenu(Scanner scanner) {
		int option = Validation.getOption(scanner, 5,
			"[1] View  [2] Add  [3] Update  [4] Delete  [5]Exit\n>> Select an option: ");
		System.out.println();
		switch (option) {
			case 1: displayRoomTable(); break;
			case 2: createRoom();       break;
			case 3: updateRoom();       break;
			case 4: deleteRoom();       break;
			case 5: logOut(this); break;
		}
	}

	private void amenitiesMenu(Scanner scanner) {
		int option = Validation.getOption(scanner, 5,
			"[1] View  [2] Add  [3] Update  [4] Delete  [5]Exit\n>> Select an option: ");
		System.out.println();
		switch (option) {
			case 1: displayAmenitiesTable(); break;
			case 2: createAmenities();       break;
			case 3: updateAmenities();       break;
			case 4: deleteAmenities();       break;
			case 5:logOut(this); break;
		}
	}

	private void roomTypesMenu(Scanner scanner) {
		int option = Validation.getOption(scanner, 5,
			"[1] View  [2] Add  [3] Update  [4] Delete  [5]Exit\n>> Select an option: ");
		System.out.println();
		switch (option) {
			case 1: displayRoomTypesTable(); break;
			case 2: createRoomTypes();       break;
			case 3: updateRoomTypes();       break;
			case 4: deleteRoomTypes();       break;
			case 5: logOut(this); break;
		}
	}

	// =========================================================================
	//  HELPERS
	// =========================================================================

	/** Builds the room-type selection prompt, e.g.  [1] Single  [2] Double: */
	private String buildTypePrompt() {
		StringBuilder sb = new StringBuilder(">> Room type ");
		for (int i = 0; i < DataBase.roomTypes.size(); i++) {
			sb.append("[").append(i + 1).append("] ").append(DataBase.roomTypes.get(i).getRoomType());
			sb.append(i < DataBase.roomTypes.size() - 1 ? "  " : ": ");
		}
		return sb.toString();
	}
}