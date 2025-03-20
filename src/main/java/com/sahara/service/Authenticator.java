package com.sahara.service;

// package com.sahara.Middleware;

import org.mindrot.jbcrypt.BCrypt;

import com.sahara.model.User;
import com.sahara.notification.Notifier;
import com.sahara.repository.UserDAO;

/**
 * AuthenticationService is responsible for handling business logic
 * related to user authentication. It interacts with the Data Layer (UserDAO)
 * to retrieve user information and performs password verification.
 */

public class Authenticator {
    public static User authenticate(String username, String password) {

        // Step 1: Fetch user from database using UserDAO
        User user = UserDAO.getUserByUsername(username);

        // Step 2: If no user is found, authentication fails immediately
        if (user == null) {
            Notifier.showError("❌ User not found !");
            return null;
        }

        // Step 2: Hash the input password
        String storedHashedPassword = user.getHashedPassword();
        // System.out.println("Stored Hashed password"+storedHashedPassword+"End");

        // 4️⃣ Step 4: Use BCrypt to compare the plain-text password with the hashed
        // password
        boolean passwordMatches = BCrypt.checkpw(password, storedHashedPassword);

        // 5️⃣ Step 5: Return the result and optionally perform other actions
        if (passwordMatches) {
            Notifier.showSuccess("✅ Successful login for user: " + username + "!");

            return user;
        } else {
            Notifier.showError("❌ Incorrect password for user: " + username);
            return null;
        }
    }

    /**
     * Helper method for hashing a password before saving it to the database.
     * Should be used during user registration.
     * 
     * @param plainPassword The user's plain-text password.
     * @return A hashed password with salt using BCrypt.
     */
    public static String hashPassword(String plainPassword) {
        // BCrypt gensalt generates a random salt for each password hash.
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12)); // 12 is the work factor (cost). You can increase for
        // System.out.println("Hashed password"+hashedPassword+"End");
        
        return hashedPassword;                                                       // more security.
    }
}

