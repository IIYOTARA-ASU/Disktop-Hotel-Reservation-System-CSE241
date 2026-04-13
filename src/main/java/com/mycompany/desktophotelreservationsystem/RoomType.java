package com.mycompany.desktophotelreservationsystem;

public class RoomType {
	String roomType;

	RoomType() {
	}

	RoomType(String roomType) {
		this.roomType = roomType.toLowerCase();
	}

	public String getRoomType() {
		// Capitalize the first letter 
		return roomType.substring(0, 1).toUpperCase() + roomType.substring(1).toLowerCase(); 
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType.toLowerCase();
	}
}
