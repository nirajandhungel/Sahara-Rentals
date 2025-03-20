package com.sahara.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "vehiclerentalsystem";
    private static final String USER = "root";
    private static final String PASS = "2005subash0910";

    // This method returns a Connection object (with DB name)
    public static Connection getConnection() {
        try {
            // Connecting directly to the database with the name included
            return DriverManager.getConnection(DB_URL + DB_NAME, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    // Optional: If you ever want to connect **without** the DB name (e.g., to create a new database)
    public static Connection getConnectionWithoutDB() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Connection failed (without DB): " + e.getMessage());
            return null;
        }
    }
 
}

