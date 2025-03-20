package com.sahara.service.payment;
public class AdminPaymentApprovalService {
    
    public boolean confirmPayment(PaymentService paymentService, double amount, String userId) {
        // Confirm after admin verification
        boolean confirmed = paymentService.processPayment(amount, userId);
        
        if (confirmed) {
            System.out.println("Admin confirmed payment for user: " + userId);
            // Maybe update booking/payment status in DB
        }
        
        return confirmed;
    }
}
