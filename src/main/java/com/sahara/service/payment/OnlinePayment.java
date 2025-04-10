package com.sahara.service.payment;

import java.sql.Timestamp;


public class OnlinePayment implements Payment {
    private int id;
    private int rentalId;
    private int userId;
    private String vehicleName;
    private double amount;
    private String paymentMethod;
    private Timestamp paymentDate;
    private String status;
    private String processedBy;

    // @Override
    // public boolean processPayment(double amount, int userId, int rentalId, String vehicleName, String paymentMethod) {
    //     // Simulate online payment processing (e.g., eSewa, PayPal)
    //     System.out.println("Online payment processed for user: " + userId + ", amount: " + amount);
    //     return true; // Assume online payment is successful
    // }

    // Getters and setters
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getRentalId() {
        return rentalId;
    }

    @Override
    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getVehicleName() {
        return vehicleName;
    }

    @Override
    public void setVehicleName(String customerName) {
        this.vehicleName = customerName;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    @Override
    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getProcessedBy() {
        return processedBy;
    }

    @Override
    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }
}