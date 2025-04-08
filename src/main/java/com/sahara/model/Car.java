package com.sahara.model;

public class Car extends Vehicle {
    public Car(int id, String type, String brand_model, int model_year, double price, String number, String imagePath, String status, String details) {
        super(id, type, brand_model, model_year, price, number, imagePath, status, details);
    }

    @Override
    public double calculateRentalPrice(int days) {
        return getPrice() * days; // Basic pricing for cars
    }
}