package com.mycompany.desktophotelreservationsystem;
import java.util.Date;

public class Reservation {
    public enum ReservationStatus { PENDING, CONFIRMED, CANCELLED, COMPLETED }
    
    Reservation() {
    }
    
    Reservation(Guest guest, Room room, Date checkInDate, Date checkOutDate) {
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.reservationStatus = ReservationStatus.PENDING;
    }
    
    private Guest guest;
    private Room room;
    private ReservationStatus reservationStatus;
    private Date checkInDate;
    private Date checkOutDate;

    // ###############################  GETTERS  ###############################
    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }


    // ###############################  SETTERS  ###############################
    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public void setCheckInDate(Date checkInDate) {
        if (checkInDate != null && checkInDate.before(checkOutDate)) {
            this.checkInDate = checkInDate;
        }
    }

    public void setCheckOutDate(Date checkOutDate) {
        if (checkOutDate != null && checkOutDate.after(checkInDate)) {
            this.checkOutDate = checkOutDate;
        }
    }


    // ###############################  METHODS  ###############################
    public int calculateDuration() {
        long diffInMs = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
        long diffInDays = (diffInMs / (1000 * 60 * 60 * 24));
        return (int) (diffInDays == 0 ? 1 : diffInDays); // Minimum of 1 day
    }
    
}
