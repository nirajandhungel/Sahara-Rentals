package com.sahara.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Vehicle {
    private final StringProperty type;
    private final StringProperty brand_model;
    private final IntegerProperty model_year;
    private final DoubleProperty price;
    private final StringProperty number; // Added field
    private final StringProperty imagePath;
    private final StringProperty status;
    private final StringProperty details;

    // Constructor
    public Vehicle(String type, String brand_model, int model_year, double price, String number, String imagePath, String status, String details) {
        this.type = new SimpleStringProperty(type);
        this.brand_model = new SimpleStringProperty(brand_model);
        this.model_year = new SimpleIntegerProperty(model_year);
        this.price = new SimpleDoubleProperty(price);
        this.number = new SimpleStringProperty(number); // Added field
        this.imagePath = new SimpleStringProperty(imagePath);
        this.status = new SimpleStringProperty(status);
        this.details = new SimpleStringProperty(details);
    }

    // Abstract method (must be implemented in subclasses)
    public abstract double calculateRentalPrice(int days);

    // JavaFX Property Getters
    public StringProperty typeProperty() { return type; }
    public StringProperty brandProperty() { return brand_model; }
    public IntegerProperty yearProperty() { return model_year; }
    public DoubleProperty priceProperty() { return price; }
    public StringProperty numberProperty() { return number; } // Added field
    public StringProperty imagePathProperty() { return imagePath; }
    public StringProperty statusProperty() { return status; }
    public StringProperty detailsProperty() { return details; }

    // Standard Getters and Setters
    public String getType() { return type.get(); }
    public String getBrand() { return brand_model.get(); }
    public int getYear() { return model_year.get(); }
    public double getPrice() { return price.get(); }
    public String getNumber() { return number.get(); } // Added field
    public String getImagePath() { return imagePath.get(); }
    public String getStatus() { return status.get(); }
    public String getDetails() { return details.get(); }

    public void setType(String type) { this.type.set(type); }
    public void setBrand(String brand_model) { this.brand_model.set(brand_model); }
    public void setYear(int year) { this.model_year.set(year); }
    public void setPrice(double price) { this.price.set(price); }
    public void setNumber(String number) { this.number.set(number); } // Added field
    public void setImagePath(String imagePath) { this.imagePath.set(imagePath); }
    public void setStatus(String status) { this.status.set(status); }
    public void setDetails(String details) { this.details.set(details); }
}