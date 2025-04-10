package com.sahara.service.payment;

import java.sql.Timestamp;

public interface Payment {
    int getId();
    int getRentalId();
    int getUserId();
    String getVehicleName();
    double getAmount();
    String getPaymentMethod();
    Timestamp getPaymentDate();
    String getStatus();
    String getProcessedBy();

    void setId(int id);
    void setRentalId(int rentalId);
    void setUserId(int userId);
    void setVehicleName(String vehicleName);
    void setAmount(double amount);
    void setPaymentMethod(String paymentMethod);
    void setPaymentDate(Timestamp paymentDate);
    void setStatus(String status);
    void setProcessedBy(String processedBy);
}