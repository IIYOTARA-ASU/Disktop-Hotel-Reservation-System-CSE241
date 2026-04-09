package com.mycompany.desktophotelreservationsystem;
import java.util.Date;

public class Invoice {
    public enum PaymentMethod { CASH, CREDIT_CARD, ONLINE }
    Invoice() {
    }

    Invoice(Reservation reservation, PaymentMethod paymentMethod, Date paymentDate) {
        this.reservation = reservation;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.amount = reservation.getRoom().getPrice() * reservation.calculateDuration();
    }

    private Reservation reservation;
    private PaymentMethod paymentMethod;
    private Date paymentDate;
    private double amount;

    // ###############################  GETTERS  ###############################
    public Reservation getReservation() { return reservation; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }

    public Date getPaymentDate() { return paymentDate; }

    public double getAmount() { return amount; }

    // ###############################  SETTERS  ###############################
    public void setReservation(Reservation reservation) { this.reservation = reservation; }

    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }

    public void setAmount(double amount) { this.amount = amount;}
}
