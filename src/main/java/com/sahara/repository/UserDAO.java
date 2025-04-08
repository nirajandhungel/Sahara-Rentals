package com.sahara.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sahara.config.DatabaseConfig;
import com.sahara.model.User;

public class UserDAO {

    public static User getUserByUsername(String username) {
        String query = "SELECT id, username, password, role, email, phone, address FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String userID = rs.getString("id");
                String dbUsername = rs.getString("username");
                String hashedPassword = rs.getString("password");
                String email = rs.getString("email");
                String role = rs.getString("role");
                String phone = rs.getString("phone");
                String address = rs.getString("address");

                return new User(userID, dbUsername, hashedPassword, email,role,phone,address);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
        }

        return null; // No user found
    }


    public static boolean updateUserProfile(String userId, String username, String email, String phone, String address) {
        String query = "UPDATE users SET username = ?, email = ?, phone = ?, address = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, address);
            stmt.setString(5, userId);
    
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user profile: " + e.getMessage());
            return false;
        }
    }


    public static List<User> getAllUsers() {
        String query = "SELECT id, username, password, role, email, phone,address FROM users";
        List<User> users = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {  // Execute query inside try-with-resources

            while (rs.next()) {  // Loop through all users
                String userID = rs.getString("id");
                String dbUsername = rs.getString("username");
                String hashedPassword = rs.getString("password");
                String email = rs.getString("email");
                String role = rs.getString("role");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                users.add(new User(userID, dbUsername, hashedPassword, email,phone, role, address)); // Add user to list
            }

        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }

        return users; // Return list of users (could be empty if no users exist)
    }
}

