package com.sahara.service;

// package com.sahara.Middleware;

import org.mindrot.jbcrypt.BCrypt;

import com.sahara.model.User;
import com.sahara.view.util.AlertUtils;
import javafx.scene.control.Alert;
import com.sahara.repository.UserDAO;

/**
 * AuthenticationService is responsible for handling business logic
 * related to user authentication. It interacts with the Data Layer (UserDAO)
 * to retrieve user information and performs password verification.
 */

public class Authenticator {
    public static User authenticate(String username, String password) {

        // Username validation
        if (username.isEmpty()) {
            // Error message
            AlertUtils.showAlert(Alert.AlertType.ERROR, "Login Failed", "Username is required.");
            return null;
        }

        // Password validation
        if (password.isEmpty()) {
            // Error message
            AlertUtils.showAlert(Alert.AlertType.ERROR, "Login Failed", "Password is required.");
            return null;
        }

        //  Fetch user from database using UserDAO
        User user = UserDAO.getUserByUsername(username);

        // If no user is found, authentication fails immediately
        if (user == null) {
            // Error message using AlertUtils
            AlertUtils.showAlert(Alert.AlertType.ERROR, "Invalid ", "User not found");

            return null;
        }

        //  Hash the input password
        String storedHashedPassword = user.getHashedPassword();

        // Use BCrypt to compare the plain-text password with the hashed password
        boolean passwordMatches = BCrypt.checkpw(password, storedHashedPassword);

        // Return the result and optionally perform other actions
        if (passwordMatches) {
            // success login

            AlertUtils.showAlert(Alert.AlertType.INFORMATION,"Success","Successfull login for user: "+username+".");

            return user;
        } else {
            AlertUtils.showAlert(Alert.AlertType.ERROR, "Invalid ", "Incorrect password for user: "+username);

            return null;
        }
    }

    
    //   Helper method for hashing a password before saving it to the database.
   
    public static String hashPassword(String plainPassword) {
        // BCrypt gensalt generates a random salt for each password hash.
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12)); // 12 is the work factor (cost). You can increase for
        
        return hashedPassword;                                                       // more security.
    }
}

