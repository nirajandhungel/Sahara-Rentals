package com.sahara.service.payment;

public interface PaymentService {
    boolean processPayment(double amount, String userId);
    String getPaymentType();  // Optional: for identifying payment method
}
