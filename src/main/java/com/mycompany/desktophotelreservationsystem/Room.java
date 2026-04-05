
package com.mycompany.desktophotelreservationsystem;

public class Room {
	private RoomType type;
	private Guest roomGuest;
	private boolean occupied;
	private int roomNumber;
	ArrayList<Amenity> ameneties = new ArrayList<>();

	Room() {
		type = new Single();
		occupier = "Unnamed";
		occupied = false;
	}

	Room(RoomType type, Guest roomGuest, boolean occupied) {
		this.type = type;
		this.roomGuest = roomGuest;
		this.occupied = occupied;
	}

	public Roomtype getRoomType() {
		return type;
	}

	public Guest getroomGuest() {
		return roomGuest;
	}

	public boolean getOccupied() {
		return occupied;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomType(RoomType type) {
		this.type = type;
	}

	public void setRoomGuest(Guest roomGuest) {
		this.roomGuest = roomGuest;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

}
