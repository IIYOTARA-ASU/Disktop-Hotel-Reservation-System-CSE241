package com.mycompany.desktophotelreservationsystem;

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

    private Date paymentDate;
    private double amount;

    
}
