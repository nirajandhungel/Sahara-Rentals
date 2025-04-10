package com.sahara.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class VehicleRentalActivity {
    private final IntegerProperty vehicleId;
    private final StringProperty vehicleName;
    private final StringProperty vehicleNumber;
    private final IntegerProperty rentalCount;
    private final DoubleProperty totalAmount;
    private final StringProperty lastRentedBy;
    private final StringProperty status;

    public VehicleRentalActivity(int vehicleId, String vehicleName, String vehicleNumber, int rentalCount, double totalAmount, String lastRentedBy, String status) {
        this.vehicleId = new SimpleIntegerProperty(vehicleId);
        this.vehicleName = new SimpleStringProperty(vehicleName);
        this.vehicleNumber = new SimpleStringProperty(vehicleNumber);
        this.rentalCount = new SimpleIntegerProperty(rentalCount);
        this.totalAmount = new SimpleDoubleProperty(totalAmount);
        this.lastRentedBy = new SimpleStringProperty(lastRentedBy);
        this.status = new SimpleStringProperty(status);
    }

    public int getVehicleId() {
        return vehicleId.get();
    }

    public String getVehicleName() {
        return vehicleName.get();
    }

    public String getVehicleNumber() {
        return vehicleNumber.get();
    }

    public int getRentalCount() {
        return rentalCount.get();
    }

    public double getTotalAmount() {
        return totalAmount.get();
    }

    public String getLastRentedBy() {
        return lastRentedBy.get();
    }

    public String getStatus() {
        return status.get();
    }
}
