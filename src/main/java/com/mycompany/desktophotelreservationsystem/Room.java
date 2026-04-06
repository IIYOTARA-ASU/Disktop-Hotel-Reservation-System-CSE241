package com.mycompany.desktophotelreservationsystem;
import java.util.*;
import java.util.ArrayList;

public class Room {
	private RoomType type;
	private Guest roomGuest;
	private boolean occupied;
	private int roomNumber;
	ArrayList<Amenity> amenities = new ArrayList<>();

	Room() {
		//type = new Single(); NEEDS TO BE UPDATED
		//occupier = "Unnamed"; NEEDS TO BE UPDATED
		occupied = false;
	}

	Room(RoomType type, Guest roomGuest, boolean occupied, ArrayList amenities) {
		this.type = type;
		this.roomGuest = roomGuest;
		this.occupied = occupied;
		for(int i = 0 ; i<ameneties.size();i++) {
			this.ameneties.set(i,ameneties.get(i));
		}
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
