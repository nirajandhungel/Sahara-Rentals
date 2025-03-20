package com.sahara.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sahara.config.DatabaseConfig;
import com.sahara.model.User;

public class UserDAO {

    public static User getUserByUsername(String username) {
        String query = "SELECT username, password, role, email FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbUsername = rs.getString("username");
                String hashedPassword = rs.getString("password");
                String email = rs.getString("email");
                String role = rs.getString("role");

                return new User(dbUsername, hashedPassword, email,role);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
        }

        return null; // No user found
    }
}


