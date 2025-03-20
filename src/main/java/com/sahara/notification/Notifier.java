package com.sahara.notification;

import javafx.scene.control.Alert;

public class Notifier {

    // Success Notification
    public static void showSuccess(String message) {
        // Create a new Alert dialog with Information type
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null); // We don't need a header for this alert
        alert.setContentText(message); // Set the success message
        alert.showAndWait(); // Display the alert
    }

    // Error Notification
    public static void showError(String message) {
        // Create a new Alert dialog with Error type
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null); // We don't need a header for this alert
        alert.setContentText(message); // Set the error message
        alert.showAndWait(); // Display the alert
    }

    // Info Notification
    public static void showInfo(String message) {
        // Create a new Alert dialog with Information type
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null); // We don't need a header for this alert
        alert.setContentText(message); // Set the information message
        alert.showAndWait(); // Display the alert
    }

    // Warning Notification (Optional)
    public static void showWarning(String message) {
        // Create a new Alert dialog with Warning type
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null); // We don't need a header for this alert
        alert.setContentText(message); // Set the warning message
        alert.showAndWait(); // Display the alert
    }
}
