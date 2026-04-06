package com.mycompany.desktophotelreservationsystem;
import java.util.Date;

public class Invoice {
    public enum PaymentMethod { CASH, CREDIT_CARD, ONLINE }
    Invoice() {
    }

    Invoice(Reservation reservation, PaymentMethod paymentMethod, Date paymentDate, double amount) {
        this.reservation = reservation;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    private Reservation reservation;
    private PaymentMethod paymentMethod;
    private Date paymentDate;
    private double amount;

    
}
