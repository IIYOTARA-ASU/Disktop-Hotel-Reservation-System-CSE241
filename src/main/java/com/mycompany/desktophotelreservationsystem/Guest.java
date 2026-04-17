package com.mycompany.desktophotelreservationsystem;

import java.util.*;

public class Guest extends User implements users {
	public static Guest currentLoggedInGuest;

	/// /////////////////////////////////GUI method to access reservation class
	public void populateReservationContainer(javafx.scene.layout.VBox container) {
		container.getChildren().clear();

		for (int i = 0; i < DataBase.reservations.size(); i++) {
			Reservation res = DataBase.reservations.get(i);

			if (res.getGuest().equals(this)) {

				String statusColor = "#f39c12"; // Orange for Pending
				String statusStr = res.getReservationStatus().toString();

				if (statusStr.equals("CONFIRMED")) {
					statusColor = "#27ae60"; // Green
				} else if (statusStr.equals("CANCELLED")) {
					statusColor = "#c0392b"; // Red
				}

				String info = "Room: " + res.getRoom().getRoomNumber() +
						"\nCheck-in: " + res.getCheckInDate() +
						"\nStatus: " + statusStr;

				javafx.scene.control.Label card = new javafx.scene.control.Label(info);

				card.setStyle(
						"-fx-text-fill: beige; " +
								"-fx-font-size: 15px; " +
								"-fx-font-weight: bold; " +
								"-fx-background-color: #24423f; " + // Teal to match your Reservation Header
								"-fx-padding: 10; " +
								"-fx-background-radius: 10; " +
								"-fx-text-alignment: center; " +
								"-fx-min-width: 500; " +
								"-fx-border-color: " + statusColor + "; " + // Dynamic status color
								"-fx-border-width: 0 0 0 10; " +           // Left-side highlight
								"-fx-border-radius: 10;"
				);

				container.getChildren().add(card);
			}
		}
	}
	/// /////////////////////////////////////////////for cancelation GUI
	public boolean processCancellation(int roomNum, java.util.Date inDate, java.util.Date outDate) {
		for (int i = 0; i < DataBase.reservations.size(); i++) {
			Reservation res = DataBase.reservations.get(i);

			if (res.getGuest().equals(this) &&
					res.getRoom().getRoomNumber() == roomNum &&
					res.getCheckInDate().equals(inDate) &&
					res.getCheckOutDate().equals(outDate)) {

				this.cancelReservation(res);
				return true;
			}
		}
		return false; // No match found
	}


	/// //////////////////////////////////////////////////////////////////

	private double balance;
	private String address;

	public double getBalance()              { return balance; }
	public void   setBalance(double b)      { this.balance = b; }
	public String getAddress()              { return address; }
	public void   setAddress(String a)      { this.address = a; }

	Guest() {}
	public Guest(String username, String password) { super(username, password); }

	public enum gender { MALE, FEMALE }
	Room currentRoom = null;

	// ─────────────────────────────────────────────────────────────────────────
	//  Core actions
	// ─────────────────────────────────────────────────────────────────────────
	@Override
	public void viewRooms() { displayRoomTable(); }

	public void makeReservation(Room room, Date inDate, Date outDate) {
		Reservation reservation = new Reservation(this, room, inDate, outDate);
		DataBase.reservations.add(reservation);
		reservation.setReservationStatus(Reservation.ReservationStatus.PENDING);
		System.out.println("   [OK] Reservation request submitted. status: PENDING.");
	}

	public void viewReservation(Reservation reservation) {
		String format = "%-10s %-20s %-20s %12s%n";
		System.out.printf(format, "ROOM", "CHECK-IN DATE", "CHECK-OUT DATE", "STATUS");
		System.out.println("─────────────────────────────────────────────────────────────────");
		System.out.printf(format,
			reservation.getRoom().getRoomNumber(),
			reservation.getCheckInDate(),
			reservation.getCheckOutDate(),
			reservation.getReservationStatus());
		System.out.println("─────────────────────────────────────────────────────────────────");
	}

	public void cancelReservation(Reservation reservation) {
		reservation.setReservationStatus(Reservation.ReservationStatus.CANCELLED);
		System.out.println("   [OK] Reservation cancelled.");
	}

	public void checkout(Reservation reservation) {
		double total = reservation.getRoom().getPrice() * reservation.calculateDuration();
		if (this.balance < total) {
			System.out.println("   [Error] Insufficient balance. Checkout failed.");
			reservation.setReservationStatus(Reservation.ReservationStatus.PENDING);
		} else {
			payInvoice(reservation);
			reservation.setReservationStatus(Reservation.ReservationStatus.COMPLETED);
			System.out.println("   [OK] Checkout complete.");
		}
	}

	private void payInvoice(Reservation reservation) {
		Invoice invoice = new Invoice(reservation, Invoice.PaymentMethod.ONLINE, reservation.getCheckOutDate());
		this.balance -= invoice.getAmount();
		DataBase.invoices.add(invoice);
		System.out.println("   [OK] Payment processed (Online).");
	}

	// ─────────────────────────────────────────────────────────────────────────
	//  Interface
	// ─────────────────────────────────────────────────────────────────────────
	public void guestInterface() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("╔═══════════════════════════════════════════════════════════════╗");
		System.out.println("║                           GUEST MENU                          ║");
		System.out.println("╚═══════════════════════════════════════════════════════════════╝");
		System.out.println();
		System.out.println("Current Balance : $" + balance);

		String prompt =
			"[1] Available Rooms     [2] Make Reservation  [3] View Reservations\n" +
			"[4] Cancel Reservation  [5] Checkout  [6] Pay Invoice  [7] Exit\n" +
			">> Select an option: ";
		int inputOption = Validation.getOption(scanner, 7, prompt);
		System.out.println();

		switch (inputOption) {

			case 1:
				viewRooms();
				break;

			case 2:
				viewRooms();
				int roomNumber = Validation.getInt(scanner, ">> Enter desired room number: ");
				Room selectedRoom = null;
				for (int i = 0; i < DataBase.rooms.size(); i++) {
					if (DataBase.rooms.get(i).getRoomNumber() == roomNumber) {
						selectedRoom = DataBase.rooms.get(i);
						break;
					}
				}
				if (selectedRoom == null) {
					System.out.println("   [Error] Room not found.");
					break;
				}
				if (selectedRoom.getOccupied()) {
					System.out.println("   [Error] Room is already occupied.");
					break;
				}
				Date inDate  = readDate(scanner, ">> Check-in date:");
				Date outDate;
				do {
					outDate = readDate(scanner, ">> Check-out date:");
					if (outDate.before(inDate)) {
						System.out.println("   [Error] Check-out date cannot be before check-in date. Please try again.");
					}
				} while (outDate.before(inDate));
				makeReservation(selectedRoom, inDate, outDate);
				break;

			case 3:
				boolean anyFound = false;
				for (int i = 0; i < DataBase.reservations.size(); i++) {
					if (DataBase.reservations.get(i).getGuest().equals(this)) {
						viewReservation(DataBase.reservations.get(i));
						anyFound = true;
					}
				}
				if (!anyFound) { System.out.println("   [Info] You have no reservations."); }
				break;

			case 4:
				boolean pendingFound = false;
				for (int i = 0; i < DataBase.reservations.size(); i++) {
					Reservation r = DataBase.reservations.get(i);
					if (r.getGuest().equals(this) && r.getReservationStatus().equals("PENDING")) {
						cancelReservation(r);
						pendingFound = true;
					}
				}
				if (!pendingFound) { System.out.println("   [Info] You have no pending reservations."); }
				break;

			case 5:
				boolean confirmedFound = false;
				for (int i = 0; i < DataBase.reservations.size(); i++) {
					Reservation r = DataBase.reservations.get(i);
					if (r.getGuest().equals(this) && r.getReservationStatus().equals("CONFIRMED")) {
						checkout(r);
						confirmedFound = true;
					}
				}
				if (!confirmedFound) { System.out.println("   [Info] You have no confirmed reservations."); }
				break;

			case 6:
				boolean completedFound = false;
				for (int i = 0; i < DataBase.reservations.size(); i++) {
					Reservation r = DataBase.reservations.get(i);
					if (r.getGuest().equals(this) && r.getReservationStatus().equals("COMPLETED")) {
						checkout(r);
						completedFound = true;
					}
				}
				if (!completedFound) { System.out.println("   [Info] You have no completed reservations to pay."); }
				break;
			case 7:

				logOut(this); break;
		}
	}

	// ─────────────────────────────────────────────────────────────────────────
	//  Date helper
	// ─────────────────────────────────────────────────────────────────────────
	private Date readDate(Scanner scanner, String prompt) {
		System.out.println(prompt);
		int d = Validation.getIntInRange(scanner, "   Day   (1~30):  ", 1, 30);
		int m = Validation.getIntInRange(scanner, "   Month (1~12):  ", 1, 12);
		int y = Validation.getIntInRange(scanner, "   Year  (2026~2028): ", 2026, 2028);
		Calendar cal = Calendar.getInstance();
		cal.set(y, m - 1, d);
		return cal.getTime();
	}
}