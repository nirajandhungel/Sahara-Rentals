package com.sahara.model;

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

    public void setMake(String make) {
        String[] parts = getBrand().split(" ", 2);
        String model = parts.length > 1 ? parts[1] : "";
        this.brand_model.set(make + " " + model);
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


    // Getters for properties

    public void setId(int id) { this.id.set(id); }
    public void setType(String type) { this.type.set(type); }
    public void setBrand(String brand) { this.brand_model.set(brand); }
    public void setYear(int year) { this.model_year.set(year); }
    public void setPrice(double price) { this.price.set(price); }
    public void setNumber(String number) { this.number.set(number); }
    public void setImagePath(String path) { this.imagePath.set(path); }
    public void setStatus(String status) { this.status.set(status); }
    public void setDetails(String details) { this.details.set(details); }


    // Additional methods for `UserController`
    public String imagePath() { return getImagePath(); }
    public String model() { return getBrand(); }
    public String type() { return getType(); }
    public String transmission() { return "Automatic"; } // Placeholder
    public int seats() { return 4; } // Placeholder
    public double dailyRate() { return getPrice(); }
     

}