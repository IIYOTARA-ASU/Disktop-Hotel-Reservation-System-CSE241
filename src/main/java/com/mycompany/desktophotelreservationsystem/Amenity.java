package com.mycompany.desktophotelreservationsystem;

public class Amenity {
    private double price;
    private String name;

    Amenity() {
    }

    public Amenity(String name, double price) {
        this.name = name;
        this.price = price;
    }

    double getprice() {
        return this.price;
    }
    public String getName() {
        return this.name;
    }

    void setName(String s) {
        this.name = s;
    }
    void setPrice(double p) {
        this.price = p;
    }
}
