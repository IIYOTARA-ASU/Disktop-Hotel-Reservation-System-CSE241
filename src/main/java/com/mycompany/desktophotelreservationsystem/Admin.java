package com.mycompany.desktophotelreservationsystem;

import java.util.Scanner;

public class Admin extends Staff{
	Admin(){
	}

	@Override
	public void viewGuests() {
		for (int i = 0; i < DataBase.guests.size(); i++) {
			System.out.println("Guest"+i+": "+ DataBase.guests.get(i));
		}
	}

	@Override
	public void viewRooms() {
		for (int i = 0; i < DataBase.rooms.size(); i++) {
			System.out.println("Room"+i+": "+ DataBase.rooms.get(i));
		}
	}

	@Override
	public void viewReservation() {
		for (int i = 0; i < DataBase.reservations.size(); i++) {
			System.out.println("reservation"+i+": "+ DataBase.reservations.get(i));
		}
	}

	public void createRoom(Room room){
		DataBase.rooms.add(room);
	}


	public void createAmenities(Amenity amenity){
		DataBase.amenities.add(amenity);
	}


	public void createRoomTypes(RoomType roomType){
		DataBase.roomTypes.add(roomType);
	}

	public void readRoom(int index){
		DataBase.rooms.get(index);
	}


	public void readAmenities(int index){
		DataBase.amenities.get(index);
	}


	public void readRoomTypes(int index){
		DataBase.roomTypes.get(index);
	}

	public void updateRoom(int index,Room room){
		DataBase.rooms.set( index, room);
	}


	public void updateAmenities(int index,Amenity amenity){
		DataBase.amenities.set(index, amenity);
	}


	public void updateRoomTypes(int index , RoomType roomType){
		DataBase.roomTypes.set(index, roomType);
	}

	public void deleteRoom(Room room){
		DataBase.rooms.remove(room);
	}


	public void deleteAmenities(Amenity amenity){
		DataBase.amenities.remove(amenity);
	}


	public void deleteRoomTypes(RoomType roomType){
		DataBase.roomTypes.remove(roomType);
	}



	@Override
	public boolean login() {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("please enter username: ");
		String inputUser = input.nextLine();

		System.out.println("please enter password: ");
		String inputPass = input.nextLine();


		return DataBase.loginUser(inputUser,inputPass);
	}

	@Override
	public void register() {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter a username: ");
		String inputUser = scanner.nextLine().trim();
		System.out.print("Enter a password: ");
		String inputPass = scanner.nextLine().trim();

		if (DataBase.registerUser(inputUser, inputPass)) {
			this.userName = inputUser;
			this.password = inputPass;
		}
	}



}
