package com.mycompany.desktophotelreservationsystem;

public class Amenity implements roomstuff{
    private double price;
    private String name;

    Amenity() {
    }

    public Amenity(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }
    public String getName() {
        return this.name;
    }

    void setName(String s) {
        this.name = s;
    }
    public void setPrice(double p) {
        this.price = p;
    }
}
