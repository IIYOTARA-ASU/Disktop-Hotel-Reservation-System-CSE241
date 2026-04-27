package com.mycompany.desktophotelreservationsystem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import org.checkerframework.checker.units.qual.s;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Receptionist extends Staff implements users{

	Receptionist() {}
	public Receptionist(String username, String password) { super(username, password); }


    private DatabaseReference chatRef;
    private ChildEventListener activeListener;

    public void sendMessageToFirebase(String text, DatabaseReference chatReference) {
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("sender", this.getUserName());
        messageData.put("text", text);
        messageData.put("timestamp", System.currentTimeMillis());

        // push() creates a unique ID so messages are sorted chronologically
        chatReference.push().setValueAsync(messageData);
    }

	private void startChat(User user) {
		System.out.println();
		Validation.centerText("LIVE CHAT WITH A GUEST", 65, true);
		Scanner scanner = new Scanner(System.in);


		CountDownLatch latch = new CountDownLatch(1);
		DatabaseReference chatReference = FirebaseDatabase.getInstance().getReference("chats");
		ArrayList<String> foundGuestChats = new ArrayList<>();

		chatReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				String format = "%-5s %-25s %33s%n";
				System.out.println();
				System.out.printf(format, "ID", "GUEST", "UNREPLIED MESSAGES");
				System.out.println("─────────────────────────────────────────────────────────────────");

				for (DataSnapshot guestSnapshot : snapshot.getChildren()) {
					String name = guestSnapshot.getKey();
					String hasUnread = "NO";
					foundGuestChats.add(name);

					// 1. Target the messages node for this specific guest
					DataSnapshot messagesNode = guestSnapshot.child("messages");
					DataSnapshot latestMsgSnapshot = null;

					// 2. Iterate to find the last child (Firebase children are chronological)
					for (DataSnapshot msgSnapshot : messagesNode.getChildren()) {
						latestMsgSnapshot = msgSnapshot;
					}

					if (latestMsgSnapshot != null) {
						String sender = latestMsgSnapshot.child("sender").getValue(String.class);
						if (sender != null && sender.equals(name)) {
							hasUnread = "YES";
						}
					}

					System.out.printf(format, foundGuestChats.indexOf(name) + 1, name, hasUnread);
				}
				System.out.println("─────────────────────────────────────────────────────────────────");
				latch.countDown();
			}

			@Override
			public void onCancelled(DatabaseError error) {
				System.out.println("Error fetching guest list: " + error.getMessage());
				latch.countDown();
			}
		});

		try { latch.await(); }
		catch (InterruptedException e) { Thread.currentThread().interrupt(); }

		if (foundGuestChats.size() == 0) {
			System.out.println("   [Info] No guest chats found.");
			return;
		}


		int selectedGuestId = Validation.getOption(scanner, foundGuestChats.size(), ">> Select Guest ID: ");

		String selectedGuestName = foundGuestChats.get(selectedGuestId - 1);






		System.out.print("\n\n");
		Validation.centerText("LIVE CHAT WITH " + selectedGuestName.toUpperCase(), 65, true);
        System.out.println("Type your message and press Enter. Type '/back' to exit.");

        DatabaseReference selectedGuestChatRef = FirebaseDatabase.getInstance()
			.getReference("chats")
			.child(selectedGuestName)
			.child("messages");

        CountDownLatch latch2 = new CountDownLatch(1);

        selectedGuestChatRef.limitToLast(25).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    String sender = child.child("sender").getValue(String.class);
                    String text = child.child("text").getValue(String.class);

                    // Identify if the message was from YOU or the RECEPTIONIST
                    String label = "";
					if (sender != null) {
						if (sender.equals(user.getUserName())) { label = "YOU"; }
						else if (sender.equals(selectedGuestName)) { label = selectedGuestName.toUpperCase(); }
						else { label = "RECEPTIONIST"; }// When the message is sent by another receptionist
					}
                    System.out.println("[" + label + "]: " + text);
                }
                latch2.countDown(); // Signal that printing is finished
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Could not load history: " + error.getMessage());
                latch2.countDown();
            }
        });

        try {
            latch2.await(); // Blocks the main thread until history is loaded
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 2. Setup real-time listener for NEW messages only
        // Note: We use the current time to avoid double-printing the history we just loaded
        long openTime = System.currentTimeMillis();

        if (activeListener == null) {
			activeListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot snapshot, String prevChildKey) {
                    Long ts = snapshot.child("timestamp").getValue(Long.class);
                    String sender = snapshot.child("sender").getValue(String.class);
                    String text = snapshot.child("text").getValue(String.class);

                    // Only print if the message is NEW and NOT from this Receptionist
					String label = "";
					if ( (ts != null && ts > openTime)  &&  (sender != null && !sender.equals(user.getUserName())) ) {
						if (sender.equals(selectedGuestName)) { label = selectedGuestName.toUpperCase(); }
						else { label = "RECEPTIONIST"; } // When the message is sent by another receptionist
						System.out.println("\n[" + label + "]: " + text);
					}

                }
                @Override public void onChildChanged(DataSnapshot s, String p) {}
                @Override public void onChildRemoved(DataSnapshot s) {}
                @Override public void onChildMoved(DataSnapshot s, String p) {}
                @Override public void onCancelled(DatabaseError e) {}
            };
            selectedGuestChatRef.addChildEventListener(activeListener);
        }

		Scanner chatScanner = new Scanner(System.in);
        while (true) {
            System.out.print("[YOU]: ");
            String input = chatScanner.nextLine();

            if (input.equalsIgnoreCase("/back")) {
				// DETACH THE LISTENERS BEFORE LEAVING
				if (activeListener != null && chatReference != null && selectedGuestChatRef != null) {
					chatReference.removeEventListener(activeListener);
					selectedGuestChatRef.removeEventListener(activeListener);
				}
				// RESET REFERENCES SO THE NEXT GUEST RE-INITIALIZES THEM
				activeListener = null;
				chatReference = null;
				selectedGuestChatRef = null;
				System.out.println("");
				break;
			}

            if (!input.trim().isEmpty()) {
                sendMessageToFirebase(input, selectedGuestChatRef);
            }
        }
	}






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
		String hoursText = this.getWorkingHours() + " hrs";
		String hoursBanner = String.format("║  %-31s %27s  ║", "RECEPTIONIST MENU", hoursText);

		System.out.println(
			"╔═══════════════════════════════════════════════════════════════╗\n" +
			hoursBanner + "\n" +
			"╠═══════════════════════════════════════════════════════════════╣\n" +
			"║ [1] Check In            [2] Check Out                         ║\n" +
			"║ [3] View Pending        [4] Accept Pending                    ║\n" +
			"║ [5] Chat                [6] Exit                              ║\n" +
			"╚═══════════════════════════════════════════════════════════════╝"
		);

		String prompt = ">> Select an option: ";
		int inputOption = Validation.getOption(scanner, 6, prompt);

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
			case 5: // ── Chat ─────────────────────────────────────────────────
				startChat(this);
				break;
			case 6: // ── Exit ─────────────────────────────────────────────────
				logOut(this); break;
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
		int d = Validation.getIntInRange(scanner, "   Day   (1~30):  ", 1, 30);
		int m = Validation.getIntInRange(scanner, "   Month (1~12):  ", 1, 12);
		int y = Validation.getIntInRange(scanner, "   Year  (2026~2028): ", 2026, 2028);
		Calendar cal = Calendar.getInstance();
		cal.set(y, m - 1, d);
		return cal.getTime();
	}
}