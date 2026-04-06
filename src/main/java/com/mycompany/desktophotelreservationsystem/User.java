package com.mycompany.desktophotelreservationsystem;
import java.util.*;
public abstract class User {
    String userName;
    String password;
    Date dateOfBirth;

    public abstract void viewRooms();
    public abstract boolean login();
    public abstract void register();
    public abstract void viewReservation();
    }
