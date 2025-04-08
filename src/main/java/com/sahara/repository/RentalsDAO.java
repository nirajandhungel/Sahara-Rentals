package com.sahara.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.sahara.config.DatabaseConfig;
import com.sahara.model.Rentals;





public class RentalsDAO {
    
    /**
     * Creates a new rental record in the database
     * @param rental The rental object to be created
     * @return true if creation was successful, false otherwise
     */
    public static boolean createRental(Rentals rental) {
        String query = "INSERT INTO rentals (user_id, vehicle_id, rental_date, return_date, total_cost, status) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, rental.getUserId());
            stmt.setInt(2, rental.getVehicleId());
            stmt.setTimestamp(3, Timestamp.valueOf(rental.getRentalDate().toLocalDateTime().toLocalDate().atStartOfDay()));
            stmt.setTimestamp(4, Timestamp.valueOf(rental.getReturnDate().toLocalDateTime().toLocalDate().atStartOfDay()));
            stmt.setDouble(5, rental.getTotalCost());
            stmt.setString(6, rental.getStatus());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating rental: " + e.getMessage());
            return false;
        }
    }

    /**
     * Fetches rental details for a specific user
     * @param userId The ID of the user
     * @return List of rentals for the user
     */
    public static List<Rentals> getRentalsByUserId(int userId) {
        List<Rentals> rentals = new ArrayList<>();
        String query = "SELECT r.id, r.vehicle_id, r.rental_date, r.return_date, r.total_cost, r.status, " +
                      "v.brand_model, v.image_path " +
                      "FROM rentals r " +
                      "JOIN vehicles v ON r.vehicle_id = v.id " +
                      "WHERE r.user_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Rentals rental = new Rentals(
                    rs.getInt("id"),
                    userId,
                    rs.getInt("vehicle_id"),
                    rs.getString("brand_model"),
                    rs.getTimestamp("rental_date"),
                    rs.getTimestamp("return_date"),
                    rs.getDouble("total_cost"),
                    rs.getString("status"),
                    rs.getString("image_path")
                );
                rentals.add(rental);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching rentals: " + e.getMessage());
        }
        return rentals;
    }

    /**
     * Updates an existing rental record
     * @param rental The rental object to be updated
     * @return true if update was successful, false otherwise
     */
    public static boolean updateRentals(Rentals rental) {
        String query = "UPDATE rentals SET status = ?, return_date = ?, total_cost = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, rental.getStatus());
            stmt.setTimestamp(2, Timestamp.valueOf(rental.getReturnDate().toLocalDateTime().toLocalDate().atStartOfDay()));
            stmt.setDouble(3, rental.getTotalCost());
            stmt.setInt(4, rental.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating rental: " + e.getMessage());
            return false;
        }
    }

    /**
     * Fetches rentals between specified dates
     * @param fromDate Start date
     * @param toDate End date
     * @return List of rentals within the date range
     */
    public static List<Rentals> getRentalssBetweenDates(LocalDate fromDate, LocalDate toDate) {
        List<Rentals> rentals = new ArrayList<>();
        String query = "SELECT r.*, v.brand_model, v.image_path " +
                      "FROM rentals r " +
                      "JOIN vehicles v ON r.vehicle_id = v.id " +
                      "WHERE r.rental_date BETWEEN ? AND ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, Timestamp.valueOf(fromDate.atStartOfDay()));
            stmt.setTimestamp(2, Timestamp.valueOf(toDate.atStartOfDay()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Rentals rental = new Rentals(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("vehicle_id"),
                    rs.getString("brand_model"),
                    rs.getTimestamp("rental_date"),
                    rs.getTimestamp("return_date"),
                    rs.getDouble("total_cost"),
                    rs.getString("status"),
                    rs.getString("image_path")
                );
                rentals.add(rental);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching rentals between dates: " + e.getMessage());
        }
        return rentals;
    }

    /**
     * Fetches rentals by status and date range
     * @param status Rental status
     * @param fromDate Start date
     * @param toDate End date
     * @return List of rentals matching the criteria
     */
    public static List<Rentals> getRentalssByStatusAndDate(String status, LocalDate fromDate, LocalDate toDate) {
        List<Rentals> rentals = new ArrayList<>();
        String query = "SELECT r.*, v.brand_model, v.image_path " +
                      "FROM rentals r " +
                      "JOIN vehicles v ON r.vehicle_id = v.id " +
                      "WHERE r.status = ? AND r.rental_date BETWEEN ? AND ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status);
            stmt.setTimestamp(2, Timestamp.valueOf(fromDate.atStartOfDay()));
            stmt.setTimestamp(3, Timestamp.valueOf(toDate.atStartOfDay()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Rentals rental = new Rentals(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("vehicle_id"),
                    rs.getString("brand_model"),
                    rs.getTimestamp("rental_date"),
                    rs.getTimestamp("return_date"),
                    rs.getDouble("total_cost"),
                    rs.getString("status"),
                    rs.getString("image_path")
                );
                rentals.add(rental);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching rentals by status and date: " + e.getMessage());
        }
        return rentals;
    }

    public static boolean updateRentalsStatus(Rentals rental) {
        String query = "UPDATE rentals SET status = ? WHERE id = ?";
    
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, rental.getStatus());
            stmt.setInt(2, rental.getId());
    
          stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error updating rental: " + e.getMessage());
            return false;
        }
    }




    // Update the return date of a rental
    public static boolean updateRentalReturnDate(Rentals rental) {
        String query = "UPDATE rentals SET return_date = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, rental.getReturnDate());
            stmt.setInt(2, rental.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating rental return date: " + e.getMessage());
            return false;
        }
    }


     // Fetch rental by ID
     public static Rentals getRentalById(int rentalId) {
        String query = "SELECT * FROM rentals WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, rentalId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Rentals(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("vehicle_id"),
                    rs.getString("vehicle_name"),
                    rs.getTimestamp("rental_date"),
                    rs.getTimestamp("return_date"),
                    rs.getDouble("total_cost"),
                    rs.getString("status"),
                    rs.getString("image_path")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching rental by ID: " + e.getMessage());
        }
        return null;
    }
}