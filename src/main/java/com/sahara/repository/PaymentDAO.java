package com.sahara.repository;

import java.sql.Connection;
// import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.sahara.config.DatabaseConfig;
import com.sahara.service.payment.CashPayment;
import com.sahara.service.payment.OnlinePayment;
import com.sahara.service.payment.Payment;

public class PaymentDAO {

    // Retrieve all payments
    public static List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM payments";
    
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                Payment payment;
                String paymentMethod = rs.getString("payment_method");
    
                if ("Cash".equalsIgnoreCase(paymentMethod)) {
                    payment = new CashPayment();
                } else {
                    payment = new OnlinePayment();
                }
    
                payment.setId(rs.getInt("id"));
                payment.setRentalId(rs.getInt("rental_id"));
                payment.setUserId(rs.getInt("user_id"));
                payment.setVehicleName(rs.getString("vehicle"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentMethod(paymentMethod);
                payment.setPaymentDate(rs.getTimestamp("payment_date")); // Use java.sql.Timestamp
                payment.setStatus(rs.getString("status"));
                payment.setProcessedBy(rs.getString("processed_by"));
    
                payments.add(payment);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching payments: " + e.getMessage());
        }
    
        return payments;
    }

    // Retrieve payments between two dates
    public static List<Payment> getPaymentsBetweenDates(LocalDate fromDate, LocalDate toDate) {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM payments WHERE payment_date BETWEEN ? AND ?";
    
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            // Use Timestamp with start of day for proper date range inclusion
            stmt.setTimestamp(1, Timestamp.valueOf(fromDate.atStartOfDay()));
            stmt.setTimestamp(2, Timestamp.valueOf(toDate.atStartOfDay()));
            
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                String paymentMethod = rs.getString("payment_method");
                Payment payment;
    
                if ("Cash".equalsIgnoreCase(paymentMethod)) {
                    payment = new CashPayment();
                } else if ("Online".equalsIgnoreCase(paymentMethod)) {
                    payment = new OnlinePayment();
                } else {
                    continue;
                }
    
                // Set all payment properties from the ResultSet
                payment.setId(rs.getInt("id"));
                payment.setRentalId(rs.getInt("rental_id"));
                payment.setUserId(rs.getInt("user_id"));
                payment.setVehicleName(rs.getString("vehicle"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentMethod(paymentMethod);
                payment.setPaymentDate(rs.getTimestamp("payment_date"));
                payment.setStatus(rs.getString("status"));
                payment.setProcessedBy(rs.getString("processed_by"));
    
                payments.add(payment);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching payments between dates: " + e.getMessage());
        }
    
        return payments;
    }

    public static boolean addPayment(Payment payment) {
        String query = "INSERT INTO payments (rental_id, user_id, vehicle, amount, payment_method, payment_date, status, processed_by) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
    
            stmt.setInt(1, payment.getRentalId());
            stmt.setInt(2, payment.getUserId());
            stmt.setString(3, payment.getVehicleName());
            stmt.setDouble(4, payment.getAmount());
            stmt.setString(5, payment.getPaymentMethod());
            // Convert payment date to start of day (midnight) for consistency
            stmt.setTimestamp(6, Timestamp.valueOf(
                payment.getPaymentDate().toLocalDateTime().toLocalDate().atStartOfDay()
            ));
            stmt.setString(7, payment.getStatus());
            stmt.setString(8, payment.getProcessedBy());
    
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    payment.setId(generatedKeys.getInt(1)); // Set the generated ID
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error adding payment: " + e.getMessage());
            return false;
        }
    }

    // Fetch total revenue and total payments
    public static double[] getTotalRevenueAndPayments() {
        String query = "SELECT SUM(amount) AS totalRevenue, COUNT(id) AS totalPayments FROM payments";
        double[] stats = new double[2]; // [0] = totalRevenue, [1] = totalPayments
    
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
    
            if (rs.next()) {
                stats[0] = rs.getDouble("totalRevenue"); // Total revenue
                stats[1] = rs.getInt("totalPayments");  // Total payments
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total revenue and payments: " + e.getMessage());
        }
    
        return stats;
    }
}