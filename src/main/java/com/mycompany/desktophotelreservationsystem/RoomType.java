package com.mycompany.desktophotelreservationsystem;
public class RoomType {
	enum roomtype{Single, Double, Suite};
	roomtype roomType;
	RoomType(String type){
		switch(type) {
		
		case "Single" : roomType = roomtype.Single; break;
		case "Double" : roomType = roomtype.Double; break;
		case "Suite" : roomType = roomtype.Suite; break;
		}
	}

	public String getRoomTypeString() {
		return roomType.name();
	}
}
