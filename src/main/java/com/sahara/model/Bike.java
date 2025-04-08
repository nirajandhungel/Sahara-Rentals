package com.sahara.model;

public class Bike extends Vehicle {
    public Bike(int id, String type, String brand_model, int model_year, double price, String number, String imagePath, String status, String details) {
        super(id, type, brand_model, model_year, price, number, imagePath, status, details);
    }

    @Override
    public double calculateRentalPrice(int days) {
        return (getPrice() * days) * 0.8; // Bikes get a 20% discount
    }
}