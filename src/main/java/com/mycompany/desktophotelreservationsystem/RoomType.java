package com.mycompany.desktophotelreservationsystem;

import java.io.Serializable;

public class RoomType implements roomstuff,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String roomType;

	RoomType() {
	}

	public RoomType(String roomType) {
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
