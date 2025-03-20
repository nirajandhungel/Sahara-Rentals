package com.sahara.service.payment;


public class CashPaymentService implements PaymentService {

    @Override
    public boolean processPayment(double amount, String userId) {
        // Cash payment might just be a placeholder until admin confirms
        System.out.println("Cash payment recorded for user: " + userId + ", amount: " + amount);
        return true;
    }

    @Override
    public String getPaymentType() {
        return "Cash";
    }
}

