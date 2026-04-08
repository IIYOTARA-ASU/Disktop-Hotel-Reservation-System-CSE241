package com.mycompany.desktophotelreservationsystem;

import java.util.Scanner;

public class Admin extends Staff{
	Admin(){
	}
    Admin(String n, String p){
    	super(n,p);
    }
	public void createRoom(Room room){
		DataBase.rooms.add(room);
	}


	public void createAmenities(Amenity amenity){
		DataBase.amenities.add(amenity);
		System.out.println("Amenity added successfully!");
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







}
