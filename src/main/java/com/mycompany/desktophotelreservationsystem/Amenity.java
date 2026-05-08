package com.mycompany.desktophotelreservationsystem;

import java.io.Serializable;

public class Amenity implements roomstuff,Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
