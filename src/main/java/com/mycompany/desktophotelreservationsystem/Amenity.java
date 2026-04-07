package com.mycompany.desktophotelreservationsystem;

public class Amenity {
    private double price;
    private String name;
    private int number;

    Amenity() {
    }

    Amenity(String name, double price, int number) {
        this.name = name;
        this.price = price;
        this.number = number;
    }

    double getprice() {
        return this.price;
    }

    int getNumber() {
        return this.number;
    }

    String getName() {
        return this.name;
    }

    void setName(String s) {
        this.name = s;
    }

    void setNumber(int n) {
        this.number = n;
    }

    void setPrice(double p) {
        this.price = p;
    }
}
