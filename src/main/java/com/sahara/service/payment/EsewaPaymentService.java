package com.sahara.service.payment;

public class EsewaPaymentService implements PaymentService {

    @Override
    public boolean processPayment(double amount, String userId) {
        // Add eSewa API integration here (simulated for now)
        System.out.println("eSewa payment processed for user: " + userId + ", amount: " + amount);
        return true; // true if payment successful
    }

    @Override
    public String getPaymentType() {
        return "eSewa";
    }
}

