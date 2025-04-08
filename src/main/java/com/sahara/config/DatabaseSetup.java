package com.sahara.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    // Create the database if it does not exist
    public static void createDatabase() {
        try (Connection conn = DatabaseConfig.getConnectionWithoutDB();
                Statement stmt = conn.createStatement()) {

            String query = "CREATE DATABASE IF NOT EXISTS vehiclerentalsystem";
            stmt.executeUpdate(query);
            System.out.println("Database created successfully!");

        } catch (SQLException e) {
            System.out.println("Error creating database: " + e.getMessage());
        }
    }

    public static void createTables() {
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
    
            String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(255), " +
                    "email VARCHAR(255) UNIQUE, " +
                    "password VARCHAR(255), " +
                    "role ENUM('customer','vmt','admin'), " +
                    "phone VARCHAR(255) UNIQUE, " +
                    "address VARCHAR(255)" +
                    ")";
            stmt.executeUpdate(usersTable);
            System.out.println("Users table created successfully!");
    
            String vehiclesTable = "CREATE TABLE IF NOT EXISTS vehicles (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "type ENUM('Car', 'Bike', 'ElectricVehicle') NOT NULL, " +
                    "brand_model VARCHAR(100) NOT NULL, " +
                    "model_year INT NOT NULL, " +
                    "price_per_day DECIMAL(10,2) NOT NULL, " +
                    "number VARCHAR(255) NOT NULL, " +
                    "image_path VARCHAR(255), " +
                    "status ENUM('Available','inProcess', 'Rented', 'Maintenance') DEFAULT 'Available', " +
                    "details VARCHAR(100) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            stmt.executeUpdate(vehiclesTable);
            System.out.println("Vehicles table created successfully!");
    
            String rentalsTable = "CREATE TABLE IF NOT EXISTS rentals (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "user_id INT NOT NULL, " +
                    "vehicle_id INT NOT NULL, " +
                    "rental_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "return_date TIMESTAMP NULL, " +
                    "total_cost DECIMAL(10,2), " +
                    "status ENUM('Pending','Approved','Active', 'Completed', 'Cancelled') DEFAULT 'Pending', " +
                    "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            stmt.executeUpdate(rentalsTable);
            System.out.println("Rentals table created successfully!");

           // Creating payments table
           String paymentsTable = "CREATE TABLE IF NOT EXISTS payments (" +
           "id INT AUTO_INCREMENT PRIMARY KEY, " +
           "rental_id INT NOT NULL, " +
           "user_id INT NOT NULL, " +
           "vehicle VARCHAR(255) NOT NULL, " +
           "amount DECIMAL(10, 2) NOT NULL, " +
           "payment_method VARCHAR(50) NOT NULL, " +
           "payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
           "status VARCHAR(50) NOT NULL, " +
           "processed_by VARCHAR(255) NOT NULL, " +
           "FOREIGN KEY (rental_id) REFERENCES rentals(id) ON DELETE CASCADE, " +
           "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
           ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
   stmt.executeUpdate(paymentsTable);
   System.out.println("Payments table created successfully!");
    
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

}
