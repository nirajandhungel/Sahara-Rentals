package com.sahara.service;

import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

import com.sahara.view.util.AlertUtils;
import com.sahara.repository.UserDAO;

import javafx.scene.control.Alert;

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
                // Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Failed", "Username is required.");

                return false;
            }

           if (username.length() < 4 || username.length() > 20) {
                //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Failed", "Username must be between 4 and 20 characters.");

                return false;
            }
            // Password validation
            if (unhashPassword.isEmpty()) {
                // Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Failed", "Password is required.");
                return false;
            }
           
             // Email validation
             if (email.isEmpty()) {
                //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Failed", "Email is required.");
                return false;
            }
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Failed", "Invalid email format.");
                return false;
            }

             // Phone validation
             if (phone.isEmpty()) {
                //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Failed", "Phone number is required.");
                return false;
            }
            if (!PHONE_PATTERN.matcher(phone).matches()) {
                //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Failed", "Invalid phone number format. Must be 10 digits.");

                return false;
            }

              // Address validation
              if (address.isEmpty()) {
                //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Failed", "Address is required.");
                return false;
            }
            if (address.length() < 5) {
                //Error message
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Failed", "Address must be at least 5 characters long.");
                return false;
            }

             // Password validation
             boolean passwordMatches = BCrypt.checkpw(unhashPassword, hashedPassword);

            // 5️⃣ Step 5: Return the result and optionally perform other action
            if (!passwordMatches) {
                AlertUtils.showAlert(Alert.AlertType.ERROR, " Failed", "Password doesnot match.");
                return false;
            }

            UserDAO.updateUserProfile(id, username, email, phone, address);
            return true;
        }

        catch (Exception e) {
            AlertUtils.showAlert(Alert.AlertType.ERROR, " Error", " An error occurred !"+e.getMessage());
            return false;
        }
    }

}
