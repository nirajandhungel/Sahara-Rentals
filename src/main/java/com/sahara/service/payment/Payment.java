package com.sahara.service.payment;

public interface Payment {
    int getId();
    int getRentalId();
    int getUserId();
    String getVehicleName();
    double getAmount();
    String getPaymentMethod();
    String getPaymentDate();
    String getStatus();
    String getProcessedBy();

    void setId(int id);
    void setRentalId(int rentalId);
    void setUserId(int userId);
    void setVehicleName(String vehicleName);
    void setAmount(double amount);
    void setPaymentMethod(String paymentMethod);
    void setPaymentDate(String paymentDate);
    void setStatus(String status);
    void setProcessedBy(String processedBy);
}