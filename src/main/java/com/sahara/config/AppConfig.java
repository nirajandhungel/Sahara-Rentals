package com.sahara.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sahara.service.Authenticator;

public class AppConfig extends DatabaseConfig {

    public static void configuration() {
        // Setup database
        DatabaseSetup.createDatabase();
        DatabaseSetup.createTables();

        Connection connection = null;
        PreparedStatement userStmt = null;
        PreparedStatement vehicleStmt = null;

        try {
            connection = DatabaseConfig.getConnection();

            /** -------------------- Insert Users -------------------- **/
            String userSql = "INSERT INTO users (username, email, password, role, phone, address) VALUES "
                    + "(?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?)";

            userStmt = connection.prepareStatement(userSql);

            // Admin users
            userStmt.setString(1, "admin1");
            userStmt.setString(2, "admin1@example.com");
            String admin1Password = Authenticator.hashPassword("admin1");
            userStmt.setString(3, admin1Password);
            userStmt.setString(4, "admin");
            userStmt.setString(5, "1234567890");
            userStmt.setString(6, "Address 1");

            userStmt.setString(7, "admin2");
            userStmt.setString(8, "admin2@example.com");
            String admin2Password = Authenticator.hashPassword("admin2");
            userStmt.setString(9, admin2Password);
            userStmt.setString(10, "admin");
            userStmt.setString(11, "0987654321");
            userStmt.setString(12, "Address 2");

            // VMT users
            userStmt.setString(13, "vmt1");
            userStmt.setString(14, "vmt1@example.com");
            String vmt1Password = Authenticator.hashPassword("vmt1");
            userStmt.setString(15, vmt1Password);
            userStmt.setString(16, "vmt");
            userStmt.setString(17, "1122334455");
            userStmt.setString(18, "Address 3");

            userStmt.setString(19, "vmt2");
            userStmt.setString(20, "vmt2@example.com");
            String vmt2Password = Authenticator.hashPassword("vmt2");
            userStmt.setString(21, vmt2Password);
            userStmt.setString(22, "vmt");
            userStmt.setString(23, "2233445566");
            userStmt.setString(24, "Address 4");

            userStmt.setString(25, "vmt3");
            userStmt.setString(26, "vmt3@example.com");
            String vmt3Password = Authenticator.hashPassword("vmt3");
            userStmt.setString(27, vmt3Password);
            userStmt.setString(28, "vmt");
            userStmt.setString(29, "3344556677");
            userStmt.setString(30, "Address 5");

            int userRows = userStmt.executeUpdate();
            System.out.println(userRows + " user rows inserted.");

            /** -------------------- Insert Vehicles -------------------- **/
            String vehicleSql = "INSERT INTO vehicles (type, brand_model, model_year, price_per_day, number, image_path, status, details) VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?)";

            vehicleStmt = connection.prepareStatement(vehicleSql);

            // Vehicle 1
            vehicleStmt.setString(1, "Car");
            vehicleStmt.setString(2, "Toyota Corolla");
            vehicleStmt.setInt(3, 2020);
            vehicleStmt.setBigDecimal(4, new java.math.BigDecimal("50.00"));
            vehicleStmt.setString(5, "ABC123");
            vehicleStmt.setString(6, "/images/vehicle.png");
            vehicleStmt.setString(7, "Available");
            vehicleStmt.setString(8, "Sedan, 4-door");

            // Vehicle 2
            vehicleStmt.setString(9, "Bike");
            vehicleStmt.setString(10, "Yamaha FZ");
            vehicleStmt.setInt(11, 2022);
            vehicleStmt.setBigDecimal(12, new java.math.BigDecimal("20.00"));
            vehicleStmt.setString(13, "XYZ456");
            vehicleStmt.setString(14, "/images/vehicle.png");
            vehicleStmt.setString(15, "Available");
            vehicleStmt.setString(16, "Sport bike");

            // Vehicle 3
            vehicleStmt.setString(17, "ElectricVehicle");
            vehicleStmt.setString(18, "Tesla Model 3");
            vehicleStmt.setInt(19, 2023);
            vehicleStmt.setBigDecimal(20, new java.math.BigDecimal("100.00"));
            vehicleStmt.setString(21, "LMN789");
            vehicleStmt.setString(22, "/images/vehicle.png");
            vehicleStmt.setString(23, "Available");
            vehicleStmt.setString(24, "Electric car, Luxury");

            // Vehicle 4
            vehicleStmt.setString(25, "Car");
            vehicleStmt.setString(26, "Honda Civic");
            vehicleStmt.setInt(27, 2021);
            vehicleStmt.setBigDecimal(28, new java.math.BigDecimal("55.00"));
            vehicleStmt.setString(29, "DEF123");
            vehicleStmt.setString(30, "/images/vehicle.png");
            vehicleStmt.setString(31, "Available");
            vehicleStmt.setString(32, "Compact sedan");

            // Vehicle 5
            vehicleStmt.setString(33, "Bike");
            vehicleStmt.setString(34, "Kawasaki Ninja");
            vehicleStmt.setInt(35, 2021);
            vehicleStmt.setBigDecimal(36, new java.math.BigDecimal("25.00"));
            vehicleStmt.setString(37, "GHI456");
            vehicleStmt.setString(38, "/images/vehicle.png");
            vehicleStmt.setString(39, "Available");
            vehicleStmt.setString(40, "Sport bike");

            int vehicleRows = vehicleStmt.executeUpdate();
            System.out.println(vehicleRows + " vehicle rows inserted.");

        } catch (SQLException e) {
            // e.printStackTrace();
        } finally {
            try {
                if (userStmt != null) userStmt.close();
                if (vehicleStmt != null) vehicleStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                // e.printStackTrace();
            }
        }
    }
}
