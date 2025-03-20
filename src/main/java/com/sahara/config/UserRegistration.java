package com.sahara.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

// import java.sql.SQLException;

public class UserRegistration {
    // private String role = "customer";

    // Insert a user into the users table
    public static boolean registerUser(String name,  String password, String phone,String email, String address, String role) {
        // DatabaseSetup class handle the creation of the database and tables if needed
        DatabaseSetup.createDatabase();
        DatabaseSetup.createTables();

        // getConnection connects to the database
        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement()) {
            String query = "INSERT INTO users (username, email, password, phone, address,role) VALUES ('" + name + "', '" + email
                    + "', '" + password + "', '" + phone + "', '" + address + "','" + role + "')";
            stmt.executeUpdate(query);
            System.out.println("User inserted successfully");
            return true;
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
            return false;
        }
    }

}
