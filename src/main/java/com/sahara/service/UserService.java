package com.sahara.service;

import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

import com.sahara.notification.Notifier;
import com.sahara.repository.UserDAO;

public class UserService {
   

    // Regular expressions for email and phone validations
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");

    public static boolean updateUser(String id, String username, String unhashPassword, String hashedPassword,
            String phone, String email,
            String address) {
        try {
            // Username validation
            if (username.isEmpty()) {
                // System.out.println("Username is required.");
                Notifier.showError("❌ Error ! Username is required.");

                return false;
            }
            if (username.length() < 4 || username.length() > 20) {
                // System.out.println("Username must be between 4 and 20 characters.");
                Notifier.showError("❌ Error ! Username must be between 4 and 20 characters.");

                return false;
            }

            // Password validation
            boolean passwordMatches = BCrypt.checkpw(unhashPassword, hashedPassword);

            // Email validation
            if (email.isEmpty()) {
                Notifier.showError("❌ Error ! Email is required.");
                return false;
            }
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                Notifier.showError("❌ Error ! Invalid email format.");
                return false;
            }

            // Phone validation
            if (phone.isEmpty()) {
                Notifier.showError("❌ Error ! Phone number is required.");
                return false;
            }
            if (!PHONE_PATTERN.matcher(phone).matches()) {
                Notifier.showError("❌ Error ! Phone number must be 10 digits.");

                return false;
            }

            // Address validation
            if (address.isEmpty()) {
                Notifier.showError("❌ Error ! Address is required.");
                return false;
            }
            if (address.length() < 5) {
                Notifier.showError("❌ Error ! Address must be at least 5 characters long.");
                return false;
            }

            // 5️⃣ Step 5: Return the result and optionally perform other actions
            if (!passwordMatches) {
                Notifier.showError("❌ Error ! Password does not match.");

                return false;
            }

            UserDAO.updateUserProfile(id, username, email, phone, address);

            // Notifier.showSuccess("Profile updated successfully!");
            // System.out.println("User registered successfully!");
            return true;
        }

        catch (Exception e) {
            Notifier.showError("❌ Error ! An error occurred during validation: " + e.getMessage());
            return false;
        }
    }

}
