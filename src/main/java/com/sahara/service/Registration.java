package com.sahara.service;

// package com.sahara.Middleware;

import java.util.regex.Pattern;

import com.sahara.config.UserRegistration;
import com.sahara.view.util.AlertUtils;
import javafx.scene.control.Alert;


public class Registration {

    // Regular expressions for email and phone validations
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");

    public static boolean registrationHandler(String username, String password, String phone, String email,String address, String role) {
        try {
            // Username validation
            if (username.isEmpty()) {
                // Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Username is required.");

                return false;
            }
            if (username.length() < 4 || username.length() > 20) {
                //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Username must be between 4 and 20 characters.");

                return false;
            }

            // Password validation
            if (password.isEmpty()) {
                // Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Password is required.");
                return false;
            }
            if (password.length() < 8) {
                //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Password must be at least 8 characters long.");
                return false;
            }
            if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") ||!password.matches(".*\\d.*") || !password.matches(".*[@#$%^&+=!].*")) {
               //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Password must include uppercase, lowercase, number, and special character.");
                return false;
            }

            // Email validation
            if (email.isEmpty()) {
                //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Email is required.");
                return false;
            }
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Invalid email format.");
                return false;
            }

            // Phone validation
            if (phone.isEmpty()) {
                //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Phone number is required.");
                return false;
            }
            if (!PHONE_PATTERN.matcher(phone).matches()) {
                //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Invalid phone number format. Must be 10 digits.");

                return false;
            }

            // Address validation
            if (address.isEmpty()) {
                //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Address is required.");
                return false;
            }
            if (address.length() < 5) {
                //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Address must be at least 5 characters long.");
                return false;
            }

            // 1️⃣ Hash the password using BCrypt before sending to the Data Layer
            String hashedPassword = Authenticator.hashPassword(password);

            // 2️⃣ Pass the hashed password to the Data Layer
            UserRegistration.registerUser(username, hashedPassword, phone, email, address, role);

            // 3️⃣ Notify user of success after registration
            // 4️⃣ Show success message using AlertUtils
            AlertUtils.showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User registered successfully! "+username+".");
            System.out.println("User registered successfully!");
            return true;

        } catch (Exception e) {
            System.err.println("Error registering user: " + e.getMessage());
            return false;
        }
    }
}

