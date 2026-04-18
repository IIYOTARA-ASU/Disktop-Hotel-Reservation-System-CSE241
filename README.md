[README.md](https://github.com/user-attachments/files/26858994/README.md)
# 🏨 Palisade Hotel Reservation System

<div align="center">

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-17.0.2-1B6AC6?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![OOP](https://img.shields.io/badge/OOP-Architecture-4CAF50?style=for-the-badge)
![Course](https://img.shields.io/badge/CSE241-Ain_Shams_University-0066CC?style=for-the-badge)

A full-featured desktop hotel reservation system built with **Java 17 + JavaFX**, featuring a dual-interface design — a rich graphical GUI layered over a fully operational console application — with complete role-based management for Guests, Receptionists, and Administrators.

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [System Architecture](#-system-architecture)
- [Class Structure](#-class-structure)
- [Role Capabilities](#-role-capabilities)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Getting Started](#-getting-started)
- [Default Demo Accounts](#-default-demo-accounts)
- [Tech Stack](#-tech-stack)
- [Design Patterns](#-design-patterns)
- [Academic Context](#-academic-context)

---

## 🔍 Overview

The **Palisade Hotel Reservation System** is a desktop application that simulates a complete hotel management workflow. The system supports three distinct user roles — Guest, Receptionist, and Admin — each with a dedicated menu and set of capabilities.

The project was implemented with a **dual-interface approach**:

- A **Console UI** (terminal-based) providing all core system functionality through clean, formatted menus.
- A **JavaFX GUI** providing a visual interface for login, registration, and core guest/receptionist interactions.

All data is managed in-memory via a central static `DataBase` class and pre-populated with realistic demo data on startup.

---

## ✨ Features

### 👤 Guest
- Browse all available rooms with type, amenities, and pricing
- Make room reservations with check-in / check-out date selection
- View all personal reservations with status tracking
- Cancel pending reservations
- Pay invoices and complete checkout with balance deduction

### 🛎️ Receptionist
- Check guests in directly (creates a CONFIRMED reservation)
- Check guests out with payment collection (Cash or Credit Card)
- View all pending reservation requests
- Accept/confirm pending reservations submitted by guests
- Working-hours tracking per receptionist session

### 🔧 Admin
- **Room Management** — Create, view, update, and delete rooms with full amenity configuration
- **Amenity Management** — Add, update, and delete amenities; changes propagate to all rooms automatically
- **Room Type Management** — Define and manage room categories (Single, Double, Suite, etc.) with referential integrity checks

### 🔐 System-Wide
- Secure login with username/password validation
- Guest self-registration with balance setup
- Protected Staff registration (Admin code required)
- Real-time room occupancy tracking based on active reservations
- Input validation with meaningful error messages throughout

---

## 🏗️ System Architecture

```
┌──────────────────────────────────────────────────────┐
│                    Entry Point (Main)                 │
│          Login / Register / Role Dispatch            │
└──────────────────┬───────────────────────────────────┘
                   │
       ┌───────────┼───────────┐
       ▼           ▼           ▼
  ┌─────────┐ ┌──────────┐ ┌───────┐
  │  Guest  │ │Reception-│ │ Admin │
  │  Menu   │ │  ist     │ │ Menu  │
  └────┬────┘ └────┬─────┘ └───┬───┘
       │           │           │
       └───────────┴───────────┘
                   │
           ┌───────▼────────┐
           │    DataBase     │
           │  (In-Memory)   │
           │  rooms         │
           │  reservations  │
           │  invoices      │
           │  people        │
           │  amenities     │
           │  roomTypes     │
           └────────────────┘
```

---

## 🗂️ Class Structure

```
User  (base class)
├── Guest       — extends User, implements users
└── Staff       — abstract, extends User
    ├── Admin         — extends Staff
    └── Receptionist  — extends Staff

Room            — implements roomstuff
RoomType        — implements roomstuff
Amenity         — implements roomstuff
Reservation     — implements reservationProcess
Invoice         — implements reservationProcess
DataBase        — central static data store
Validation      — input validation utilities
Main            — application entry point

── GUI Layer ─────────────────────────────────
Controllers/
  ├── theGOATcontroller   — Login/Register controller
  ├── GuestController     — Guest GUI actions
  └── Receptionist_Controller — Receptionist GUI
Screens/
  ├── LoginPage           — JavaFX login screen
  ├── AdminDashboard      — Admin GUI launcher
  └── ScreenUtility       — Shared screen helpers
```

---

## 👥 Role Capabilities

| Capability | Guest | Receptionist | Admin |
|---|:---:|:---:|:---:|
| View Available Rooms | ✅ | ✅ | ✅ |
| Make Reservation | ✅ | — | — |
| Cancel Reservation | ✅ | — | — |
| View Own Reservations | ✅ | — | — |
| Pay Invoice / Checkout | ✅ | ✅ | — |
| Check Guest In | — | ✅ | — |
| Accept Pending Requests | — | ✅ | — |
| Manage Rooms (CRUD) | — | — | ✅ |
| Manage Amenities (CRUD) | — | — | ✅ |
| Manage Room Types (CRUD) | — | — | ✅ |

---

## 📁 Project Structure

```
Disktop-Hotel-Reservation-System-CSE241-master/
│
├── pom.xml                          # Maven build config
│
└── src/
    └── main/
        ├── java/
        │   ├── Screens/
        │   │   ├── AdminDashboard.java
        │   │   ├── LoginPage.java
        │   │   └── ScreenUtility.java
        │   │
        │   └── com/mycompany/desktophotelreservationsystem/
        │       ├── Main.java                  # App entry point & role dispatch
        │       ├── DataBase.java              # Central in-memory data store
        │       ├── User.java                  # Base user class (login/register)
        │       ├── Guest.java                 # Guest role & actions
        │       ├── Staff.java                 # Abstract staff base class
        │       ├── Admin.java                 # Admin CRUD operations
        │       ├── Receptionist.java          # Receptionist workflow
        │       ├── Room.java                  # Room entity
        │       ├── RoomType.java              # Room category entity
        │       ├── Amenity.java               # Amenity entity
        │       ├── Reservation.java           # Reservation lifecycle
        │       ├── Invoice.java               # Payment invoice
        │       ├── Validation.java            # Input sanitization utilities
        │       ├── users.java                 # User interface
        │       ├── roomstuff.java             # Room-related interface
        │       ├── reservationProcess.java    # Reservation interface
        │       │
        │       └── Controllers/
        │           ├── theGOATcontroller.java      # Login/Register GUI
        │           ├── GuestController.java         # Guest GUI controller
        │           └── Receptionist_Controller.java # Receptionist GUI
        │
        └── resources/
            ├── Style.css                  # Main stylesheet
            ├── receptionist.css           # Receptionist-specific styles
            ├── hadi.css                   # Additional styles
            ├── Login.fxml                 # Login screen layout
            ├── Register.fxml              # Registration screen
            ├── theGoat.fxml               # Main controller view
            ├── Receptionists.fxml         # Receptionist dashboard
            ├── ReceptionistViewrooms.fxml
            ├── ReceptionistViewroomtypes.fxml
            ├── guestscenebuilder.fxml     # Guest dashboard
            ├── guestMakeReservations.fxml
            ├── guestViewReservation.fxml
            ├── guestCancelReservation.fxml
            ├── guestPayInvoice.fxml
            ├── guestViewRooms.fxml
            ├── adminAmenities.fxml        # Admin amenity management
            ├── adminRooms.fxml            # Admin room management
            ├── adminRoomTypes.fxml        # Admin room-type management
            └── Images/                   # UI assets
```

---

## ✅ Prerequisites

- **Java JDK 17** or higher — [Download](https://www.oracle.com/java/technologies/downloads/#java17)
- **Apache Maven 3.6+** — [Download](https://maven.apache.org/download.cgi)
- **JavaFX SDK 17.0.2** — Managed automatically via Maven

Verify your setup:

```bash
java -version     # Should output: openjdk 17.x.x
mvn -version      # Should output: Apache Maven 3.x.x
```

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/Disktop-Hotel-Reservation-System-CSE241.git
cd Disktop-Hotel-Reservation-System-CSE241
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn javafx:run
```

> **Note:** The app launches the JavaFX GUI by default. If running the console version, ensure you're running `Main.java` directly through your IDE (IntelliJ IDEA or Eclipse).

### 4. Run via IDE (Alternative)

1. Open the project in **IntelliJ IDEA** or **Eclipse**
2. Import as a **Maven project**
3. Run `Main.java` for the console interface
4. Run `LoginPage.java` for the JavaFX GUI interface

---

## 🔑 Default Demo Accounts

The system auto-populates demo data on startup via `DataBase.demoFill()`.

| Role | Username | Password |
|---|---|---|
| Admin | `Ahmed` | `67` |
| Guest | `Baraa` | `67` |
| Receptionist | `Youssef` | `67` |

### Demo Rooms

| Room # | Type | Price/Night | Amenities |
|---|---|---|---|
| 67 | Suite | $670 | Gym, Coffee Machine |
| 108 | Single | $100 | Gym |
| 123 | Double | $150 | Free WiFi, Pool |

### Demo Amenities

| Name | Price |
|---|---|
| Pool | $50 |
| Gym | $30 |
| Free WiFi | $10 |
| Coffee Machine | $5 |

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 17 | Core application language |
| JavaFX | 17.0.2 | Desktop GUI framework |
| FXML | — | Declarative UI layout |
| CSS | — | JavaFX scene styling |
| Apache Maven | 3.x | Build automation & dependency management |
| IntelliJ IDEA / Eclipse | — | Recommended IDEs |

---

## 🧱 Design Patterns & OOP Concepts

This project demonstrates several core Object-Oriented Programming principles as part of the CSE241 curriculum:

**Inheritance**
- `Guest` and `Staff` both extend `User`
- `Admin` and `Receptionist` both extend the abstract `Staff` class

**Polymorphism**
- `Main` dispatches to role-specific menus using `instanceof` checks
- `viewRooms()` is overridden in `Guest` while being defined in `User`

**Encapsulation**
- All entity fields are private with clean getter/setter contracts
- `Validation` class centralizes all input sanitization

**Interfaces**
- `users` — implemented by all user-facing classes
- `roomstuff` — implemented by `Room`, `RoomType`, `Amenity`
- `reservationProcess` — implemented by `Reservation` and `Invoice`

**Method Chaining**
- `Room.addAmenity()` returns `this`, enabling fluent room construction:
  ```java
  new Room(67, suite, 670).addAmenity(gym).addAmenity(coffee);
  ```

**Enum-Based State Management**
- `Reservation.ReservationStatus` — `PENDING`, `CONFIRMED`, `CANCELLED`, `COMPLETED`
- `Invoice.PaymentMethod` — `CASH`, `CREDIT_CARD`, `ONLINE`

---

## 🎓 Academic Context

This project was developed as a course assignment for:

**CSE241 — Object-Oriented Programming**  
Faculty of Engineering, Ain Shams University

The system demonstrates practical application of OOP principles including inheritance hierarchies, interface-based contracts, polymorphic dispatch, and encapsulation — applied to a real-world hotel management domain.

---

## 👨‍💻 Authors

> Add your team member names and IDs here.

| Name | Student ID |
|---|---|
| Yousef Abdullah | 25P0436 |
| Ahmed Ramy| 25P0187 |
| Hadi Mohamed | 25P0162 |
| Baraa Khaled | 25P0104 |
| Youssef Dorgham | 25P0208 |
---

## 📄 License

This project is submitted as academic coursework for Ain Shams University. All rights reserved by the authors.
