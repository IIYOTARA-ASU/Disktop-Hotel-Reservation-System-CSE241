package com.mycompany.desktophotelreservationsystem;
import java.util.*;

public class Room implements roomstuff {
	private RoomType type;
	private int roomNumber;
	private int price;
	private boolean occupied;

	ArrayList<Amenity> amenities = new ArrayList<>();

	public Room() {
	}

	public Room(int roomNumber, RoomType type, int price) {
		this.roomNumber = roomNumber;
		this.type = type;
		this.price = price;
	}


	// ###############################  GETTERS  ###############################
	public RoomType getRoomType() {
		return type;
	}

	public boolean getOccupied() {
		Date currentDate = new Date();
		for (int i = 0; i < DataBase.reservations.size(); i++) {
			boolean isInReservations = DataBase.reservations.get(i).getRoom().getRoomNumber() == this.roomNumber; // Room is in reservations
			boolean isOccupied = DataBase.reservations.get(i).isReservationActive(); // Reservation is active
			if (isInReservations && isOccupied) { return true; }
		}
		return false;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public int getPrice() {
		return price;
	}

	public ArrayList<Amenity> getAmenities() {
		return amenities;
	}


	// ###############################  SETTERS  ###############################
	public void setRoomType(RoomType type) {
		this.type = type;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Room addAmenity(Amenity amenity) {
		this.amenities.add(amenity);
		return this; // To allow method chaining (according to Gemini) for faster addition of amenities to rooms
	}
	public boolean getOcc(){
		return this.occupied;
	}
	public void setOccupied(){
		this.occupied=true;
	}
	public void setUnOccupied(){
		this.occupied=false;
	}

}
