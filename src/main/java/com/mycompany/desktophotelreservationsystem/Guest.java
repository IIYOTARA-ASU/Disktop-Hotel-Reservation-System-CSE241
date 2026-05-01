package com.mycompany.desktophotelreservationsystem;
// Firebase Core Imports
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

// Realtime Database Specifics
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;

// Java Standard IO and Utilities
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Guest extends User implements users {
	public static Guest currentLoggedInGuest;

	/// ///////////////////////////////// GUI methods to access reservation class
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
				}else if (statusStr.equals("COMPLETED")) {
					statusColor = "#3498db"; // Blue
				}

				String info = "Reservation: "+res.getReservationId()+"\nRoom: " + res.getRoom().getRoomNumber() + "\nCheck-in: " + res.getCheckInDate() + "\nStatus: " + statusStr;

				javafx.scene.control.Label card = new javafx.scene.control.Label(info);

				card.setStyle(
						"-fx-text-fill: beige; " +
								"-fx-font-size: 15px; " +
								"-fx-font-weight: bold; " +
								"-fx-background-color: #24423f; " + // Teal
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
	public boolean processCancellation(int roomID) {
		for (int i = 0; i < DataBase.reservations.size(); i++) {
			Reservation res = DataBase.reservations.get(i);

			if (res.getGuest().equals(this) && res.getReservationId() == roomID && !res.getReservationStatus().equals("CANCELLED")) {
				this.cancelReservation(res);
				return true;
			}
		}
		return false; // No match found
	}
/// //////////////////////////////////////////////////////////////handle PayInvoice for GUI
	public boolean payInvoiceByRoomNumber(int roomNumber) {
		for (int i = 0; i < DataBase.reservations.size(); i++) {
			Reservation r = DataBase.reservations.get(i);

			if (r.getGuest().equals(this) && r.getRoom().getRoomNumber() == roomNumber && r.getReservationStatus().toString().equals("CONFIRMED")) {

				// Call checkout/payInvoice logic homa nafs e7aga m4 fahem eh el e5telaf aslan
				this.checkout(r);
				return true;
			}
		}
		return false;
	}

	/// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
		String format = "%-10s %-30s %-30s %12s%n";
		System.out.printf(format, "ROOM", "CHECK-IN DATE", "CHECK-OUT DATE", "STATUS");
		System.out.println("─────────────────────────────────────────────────────────────────────────────────────");
		System.out.printf(format,
			reservation.getRoom().getRoomNumber(),
			reservation.getCheckInDate(),
			reservation.getCheckOutDate(),
			reservation.getReservationStatus());
		System.out.println("─────────────────────────────────────────────────────────────────────────────────────");
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
    //  Firebase Chat Logic
    // ─────────────────────────────────────────────────────────────────────────

    private DatabaseReference chatRef;
    private ChildEventListener activeListener; 

    private void initChatRef() {
        // Points to chats/[username]/messages. 
        // Instance-based to prevent cross-user data leakage.
        if (chatRef == null) {
            chatRef = FirebaseDatabase.getInstance()
                    .getReference("chats")
                    .child(this.getUserName())
                    .child("messages");
        }
    }

    public void sendMessageToFirebase(String text) {
        initChatRef();
        
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("sender", this.getUserName());
        messageData.put("text", text);
        messageData.put("timestamp", System.currentTimeMillis());

        // push() creates a unique ID so messages are sorted chronologically
        chatRef.push().setValueAsync(messageData);
    }

	private void startChat(User user) {
		System.out.println();
		Validation.centerText("LIVE CHAT WITH RECEPTIONISTS", 65, true);
        System.out.println("Type your message and press Enter. Type '/back' to exit.");
        
        initChatRef();
        
        // We use a CountDownLatch to force the console to wait until history is printed
        CountDownLatch latch = new CountDownLatch(1);
        chatRef.limitToLast(25).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    String sender = child.child("sender").getValue(String.class);
                    String text = child.child("text").getValue(String.class);
                    
                    // Identify if the message was from YOU or the RECEPTIONIST
                    String label = (sender != null && sender.equals(user.getUserName())) ? "YOU" : "RECEPTIONIST";
                    System.out.println("[" + label + "]: " + text);
                }
                latch.countDown(); // Signal that printing is finished
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Could not load history: " + error.getMessage());
                latch.countDown();
            }
        });

        try {
            latch.await(); // Blocks the main thread until history is loaded
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
                    
                    // Only print if the message is NEW and NOT from the guest themselves
                    if (ts != null && ts > openTime) {
                        if (sender != null && !sender.equals(user.getUserName())) {
                            System.out.println("\n[RECEPTIONIST]: " + text);
                        }
                    }
                }
                @Override public void onChildChanged(DataSnapshot s, String p) {}
                @Override public void onChildRemoved(DataSnapshot s) {}
                @Override public void onChildMoved(DataSnapshot s, String p) {}
                @Override public void onCancelled(DatabaseError e) {}
            };
            chatRef.addChildEventListener(activeListener);
        }

		Scanner chatScanner = new Scanner(System.in);
        while (true) {
            System.out.print("[YOU]: ");
            String input = chatScanner.nextLine();
            
            if (input.equalsIgnoreCase("/back")) {
				// DETACH THE LISTENER BEFORE LEAVING
				if (activeListener != null && chatRef != null) {
					chatRef.removeEventListener(activeListener);
				}
				// RESET REFERENCES SO THE NEXT GUEST RE-INITIALIZES THEM
				activeListener = null;
				chatRef = null; 
				System.out.println("");
				break;
			}

            if (!input.trim().isEmpty()) {
                sendMessageToFirebase(input);
            }
        }
    }

	
	// ─────────────────────────────────────────────────────────────────────────
	//  Interface
	// ─────────────────────────────────────────────────────────────────────────
	public void guestInterface() {
		Scanner scanner = new Scanner(System.in);
		String balanceBanner = String.format( "║  %-31s %27s  ║", "USER MENU", balance + "$" );
		System.out.println(
			"╔═══════════════════════════════════════════════════════════════╗\n" +
										balanceBanner						+"\n" +
			"╠═══════════════════════════════════════════════════════════════╣\n" +
			"║ [1] Available Rooms     [2] Make Reservation                  ║\n" +
			"║ [3] View Reservations   [4] Cancel Reservation                ║\n" +
			"║ [5] Checkout            [6] Pay Invoice                       ║\n" +
			"║ [7] Chat                [8] Log Out                           ║\n" +
			"╚═══════════════════════════════════════════════════════════════╝"
		);

		String prompt = ">> Select an option: ";
		int inputOption = Validation.getOption(scanner, 8, prompt);
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
				startChat(this); 
				break;
			case 8:
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