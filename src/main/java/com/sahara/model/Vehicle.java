package com.sahara.model;

import java.io.ObjectInputFilter;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Vehicle {
    private final IntegerProperty id;
    private final StringProperty type;
    private final StringProperty brand_model;
    private final IntegerProperty model_year;
    private final DoubleProperty price;
    private final StringProperty number;
    private final StringProperty imagePath;
    private final StringProperty status;
    private final StringProperty details;

    // Constructor
    public Vehicle(int id, String type, String brand_model, int model_year, double price, String number, String imagePath, String status, String details) {
        this.id = new SimpleIntegerProperty(id);
        this.type = new SimpleStringProperty(type);
        this.brand_model = new SimpleStringProperty(brand_model);
        this.model_year = new SimpleIntegerProperty(model_year);
        this.price = new SimpleDoubleProperty(price);
        this.number = new SimpleStringProperty(number);
        this.imagePath = new SimpleStringProperty(imagePath);
        this.status = new SimpleStringProperty(status);
        this.details = new SimpleStringProperty(details);
    }

    // Abstract method (must be implemented in subclasses)
    public abstract double calculateRentalPrice(int days);

    public StringProperty makeProperty() {
        return new SimpleStringProperty(getBrand().split(" ")[0]); // Assuming "make" is the first word of the brand
    }
    
    public StringProperty modelProperty() {
        return new SimpleStringProperty(getBrand().split(" ")[1]); // Assuming "model" is the second word of the brand
    }


     // Additional methods for extracting make and model
     public String getMake() {
        String[] parts = getBrand().split(" ", 2);
        return parts.length > 0 ? parts[0] : ""; // Return the first word as the make
    }

    public String getModel() {
        String[] parts = getBrand().split(" ", 2);
        return parts.length > 1 ? parts[1] : ""; // Return the second word as the model
    }

    // Getters for properties
    public int getId() { return id.get(); }
    public String getType() { return type.get(); }
    public String getBrand() { return brand_model.get(); }
    public int getYear() { return model_year.get(); }
    public double getPrice() { return price.get(); }
    public String getNumber() { return number.get(); }
    public String getImagePath() { return imagePath.get(); }
    public String getStatus() { return status.get(); }
    public String getDetails() { return details.get(); }

    // Additional methods for `UserController`
    public String imagePath() { return getImagePath(); }
    public String model() { return getBrand(); }
    public String type() { return getType(); }
    public String transmission() { return "Automatic"; } // Placeholder
    public int seats() { return 4; } // Placeholder
    public double dailyRate() { return getPrice(); }
     // Setter for status
     public void setStatus(String status) {
        this.status.set(status); // ✅ Correct way to update a StringProperty
    }

}