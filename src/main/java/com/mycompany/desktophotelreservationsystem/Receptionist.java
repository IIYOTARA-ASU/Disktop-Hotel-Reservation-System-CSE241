package com.mycompany.desktophotelreservationsystem;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Receptionist extends Staff {

	Receptionist() {}
	Receptionist(String username, String password) { super(username, password); }

	// ─────────────────────────────────────────────────────────────────────────
	//  Core actions
	// ─────────────────────────────────────────────────────────────────────────
	public void checkin(Guest guest, Room room, Date inDate, Date outDate) {
		if (room.getOccupied()) {
			System.out.println("   [Error] Room is already occupied.");
		} else {
			Reservation reservation = new Reservation(guest, room, inDate, outDate);
			DataBase.reservations.add(reservation);
			reservation.setReservationStatus(Reservation.ReservationStatus.CONFIRMED);
			System.out.println("   [OK] Room reserved and confirmed.");
		}
	}

	public void checkout(Guest guest, Reservation reservation) {
		double total = reservation.getRoom().getPrice() * reservation.calculateDuration();
		if (guest.getBalance() < total) {
			System.out.println("   [Error] Insufficient balance. Checkout failed.");
			reservation.setReservationStatus(Reservation.ReservationStatus.PENDING);
			return;
		}

		Scanner scanner = new Scanner(System.in);
		Invoice invoice = new Invoice(reservation, null, reservation.getCheckOutDate());

		int input = Validation.getOption(scanner, 2,
			">> Payment method  [1] Cash  [2] Credit Card: ");

		if (input == 1) {
			invoice.setPaymentMethod(Invoice.PaymentMethod.CASH);
			System.out.println("   [OK] Cash collected from guest.");
		} else {
			invoice.setPaymentMethod(Invoice.PaymentMethod.CREDIT_CARD);
			guest.setBalance(guest.getBalance() - invoice.getAmount());
			System.out.println("   [OK] Payment charged to credit card.");
		}

		DataBase.invoices.add(invoice);
		reservation.setReservationStatus(Reservation.ReservationStatus.COMPLETED);
		System.out.println("   [OK] Checkout complete.");
	}

	// ─────────────────────────────────────────────────────────────────────────
	//  Interface
	// ─────────────────────────────────────────────────────────────────────────
	public void receptionistInterface() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("╔═══════════════════════════════════════════════════════════════╗");
		System.out.println("║                       RECEPTIONIST MENU                       ║");
		System.out.println("╚═══════════════════════════════════════════════════════════════╝");
		System.out.println();

		int inputOption = Validation.getOption(scanner, 5,
			"[1] Check In  [2] Check Out  [3] View Pending  [4] Accept Pending  [5]Exit \n" +
			">> Select an option: ");
		System.out.println();

		switch (inputOption) {

			case 1: // ── Check In ─────────────────────────────────────────────
				Guest inGuest = chooseGuest(scanner);
				if (inGuest == null) { System.out.println("   [Error] Guest not found."); break; }

				viewRooms();
				int inRoomNumber = Validation.getInt(scanner, ">> Enter room number: ");
				Room inRoom = findRoom(inRoomNumber);
				if (inRoom == null)         { System.out.println("   [Error] Room not found."); break; }
				if (inRoom.getOccupied())   { System.out.println("   [Error] Room is already occupied."); break; }

				Date inDate  = readDate(scanner, ">> Check-in date:");
				Date outDate;
				do {
					outDate = readDate(scanner, ">> Check-out date:");
					if (outDate.before(inDate)) {
						System.out.println("   [Error] Check-out date cannot be before check-in date. Please try again.");
					}
				} while (outDate.before(inDate));

				checkin(inGuest, inRoom, inDate, outDate);
				break;

			case 2: // ── Check Out ────────────────────────────────────────────
				Guest outGuest = chooseGuest(scanner);
				if (outGuest == null) { System.out.println("   [Error] Guest not found."); break; }

				Reservation confirmedRes = null;
				for (int i = 0; i < DataBase.reservations.size(); i++) {
					Reservation r = DataBase.reservations.get(i);
					if (r.getGuest().equals(outGuest) && r.getReservationStatus().equals("CONFIRMED")) {
						confirmedRes = r;
						break;
					}
				}
				if (confirmedRes == null) { System.out.println("   [Info] No confirmed reservation found for this guest."); break; }

				checkout(outGuest, confirmedRes);
				break;

			case 3: // ── View Pending ─────────────────────────────────────────
				viewPending();
				break;

			case 4: // ── Accept Pending ───────────────────────────────────────
				acceptPending(scanner);
				break;
			case 5:
				System.out.println("=========Goodbye=========");
				System.exit(0);
		}
	}

	// ─────────────────────────────────────────────────────────────────────────
	//  Pending reservations
	// ─────────────────────────────────────────────────────────────────────────
	public boolean viewPending() {
		boolean any = false;
		for (int i = 0; i < DataBase.reservations.size(); i++) {
			if (DataBase.reservations.get(i).getReservationStatus() == "PENDING") {
				any = true;
				break;
			}
		}
		if (!any) {
			System.out.println("   [Info] No pending requests.");
			return false;
		}

		String format = "%-12s %-15s %-20s%n";
		System.out.println();
		System.out.printf(format, "REQUEST #", "ROOM", "GUEST");
		System.out.println("─────────────────────────────────────────────");
		int counter = 1;
		for (int i = 0; i < DataBase.reservations.size(); i++) {
			Reservation r = DataBase.reservations.get(i);
			if (r.getReservationStatus() == "PENDING") {
				System.out.printf(format, counter++,
					r.getRoom().getRoomNumber(),
					r.getGuest().getUserName());
			}
		}
		System.out.println("─────────────────────────────────────────────");
		return true;
	}

	public void acceptPending(Scanner scanner) {
		if (!viewPending()) { return; }

		int roomNumber = Validation.getInt(scanner, ">> Enter room number to accept: ");
		Reservation target = null;
		int targetIndex = -1;

		for (int i = 0; i < DataBase.reservations.size(); i++) {
			Reservation r = DataBase.reservations.get(i);
			if (r.getRoom().getRoomNumber() == roomNumber && r.getReservationStatus() == "PENDING") {
				target      = r;
				targetIndex = i;
				break;
			}
		}

		if (target == null) {
			System.out.println("   [Error] No pending reservation found for that room number.");
			return;
		}

		target.setReservationStatus(Reservation.ReservationStatus.CONFIRMED);
		DataBase.reservations.set(targetIndex, target);
		System.out.println("   [OK] Reservation accepted and confirmed.");
	}

	// ─────────────────────────────────────────────────────────────────────────
	//  Helpers
	// ─────────────────────────────────────────────────────────────────────────
	private Guest chooseGuest(Scanner scanner) {
		System.out.println(">> Registered guests:");
		System.out.println("   ─────────────────────────────────────────────");
		int count = 0;
		for (int i = 0; i < DataBase.people.size(); i++) {
			if (DataBase.people.get(i) instanceof Guest) {
				System.out.println("   " + (++count) + ". " + DataBase.people.get(i).getUserName());
			}
		}
		System.out.println("   ─────────────────────────────────────────────");

		String name = Validation.getString(scanner, ">> Enter guest username: ");
		for (int i = 0; i < DataBase.people.size(); i++) {
			if (DataBase.people.get(i) instanceof Guest
					&& DataBase.people.get(i).getUserName().equals(name)) {
				return (Guest) DataBase.people.get(i);
			}
		}
		return null;
	}

	private Room findRoom(int number) {
		for (int i = 0; i < DataBase.rooms.size(); i++) {
			if (DataBase.rooms.get(i).getRoomNumber() == number) { return DataBase.rooms.get(i); }
		}
		return null;
	}

	private Date readDate(Scanner scanner, String prompt) {
		System.out.println(prompt);
		int d = Validation.getIntInRange(scanner, "   Day   (1-30):  ", 1, 30);
		int m = Validation.getIntInRange(scanner, "   Month (1-12):  ", 1, 12);
		int y = Validation.getIntInRange(scanner, "   Year  (2026-2028): ", 2026, 2028);
		Calendar cal = Calendar.getInstance();
		cal.set(y, m - 1, d);
		return cal.getTime();
	}
}