package com.sahara.model;

import java.sql.Timestamp;

import com.sahara.repository.VehicleDAO;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Rentals {
    private int id;
    private int userId;
    private int vehicleId;
    private String vehicleName;
    private Timestamp rentalDate;
    private Timestamp returnDate;
    private double totalCost;
    private String status;
    private String vehicleImagePath;

    // Constructor
    public Rentals(int id, int userId, int vehicleId, String vehicleName,
            Timestamp rentalDate, Timestamp returnDate,
            double totalCost, String status, String vehicleImagePath) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.totalCost = totalCost;
        this.status = status;
        this.vehicleImagePath = vehicleImagePath;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public Timestamp getRentalDate() {
        return rentalDate;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getStatus() {
        return status;
    }

    public String getImagePath() {
        return vehicleImagePath;
    }



    // Setter for status
    public void setStatus(String status) {
        this.status = status;
    }

    // Setter userId
    public void setUserId(int userId) {
        this.userId = userId;
    }
   // Setter for vehicleId
   public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
    // Setter for vehicleName
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    // Setter for rentalDate
    public void setRentalDate(Timestamp rentalDate) {
        this.rentalDate = rentalDate;
    }
    // Setter for returnDate
    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }
    // Setter for totalCost
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    // Setter for vehicleImagePath
    public void setVehicleImagePath(String vehicleImagePath) {
        this.vehicleImagePath = vehicleImagePath;
    }

    public Vehicle getVehicle() {
    // Fetch the vehicle from the database using the vehicleId
    return VehicleDAO.getVehicleById(vehicleId);
}
   
    

    // Additional methods for `VMTController`
    public ObjectProperty<Timestamp> startDateProperty() {
        return new SimpleObjectProperty<>(rentalDate);
    }

    public ObjectProperty<Timestamp> endDateProperty() {
        return new SimpleObjectProperty<>(returnDate);
    }

    // // Additional methods for `UserController`
    // public Vehicle getVehicleByRentalID(int id) {

        
    // }

    public Timestamp startDate() {
        return getRentalDate();
    }

    public Timestamp endDate() {
        return getReturnDate();
    }

    public String status() {
        return getStatus();
    }
}