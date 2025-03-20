package com.sahara.view.controller;

import com.sahara.service.Registration;
import com.sahara.view.util.AppNavigator;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class RegistrationController {

    public VBox getView(BorderPane rootLayout) {
        VBox registrationView = new VBox(10);
        registrationView.getStyleClass().add("registration-view");
        
        // Title Label
        Label titleLabel = new Label("Customer Registration Form");
        titleLabel.getStyleClass().add("title-label");

        // Create the grid for the form layout
        GridPane grid = new GridPane();
        grid.getStyleClass().add("registration-grid");

        // Column constraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(120);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(250);
        grid.getColumnConstraints().addAll(col1, col2);

        // Form fields
        Label nameLabel = createStyledLabel("Full Name:");
        TextField nameField = new TextField();
        styleTextField(nameField);

        Label addressLabel = createStyledLabel("Address:");
        TextField addressField = new TextField();
        styleTextField(addressField);

        Label phoneLabel = createStyledLabel("Phone Number:");
        TextField phoneField = new TextField();
        styleTextField(phoneField);

        Label emailLabel = createStyledLabel("Email Address:");
        TextField emailField = new TextField();
        styleTextField(emailField);

        Label passwordLabel = createStyledLabel("Password:");
        PasswordField passwordField = new PasswordField();
        styleTextField(passwordField);

        // Submit Button
        Button submitButton = new Button("Register");
        submitButton.getStyleClass().add("submit-button");

        // Back to Login Button
        Button backButton = new Button("Back to Login");
        backButton.getStyleClass().add("submit-button"); // Same styling as Register button
        backButton.setOnAction(e -> AppNavigator.loadView(new LoginController().getView(rootLayout)));
        
        // Add components to grid
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(addressLabel, 0, 1);
        grid.add(addressField, 1, 1);
        grid.add(phoneLabel, 0, 2);
        grid.add(phoneField, 1, 2);
        grid.add(emailLabel, 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(passwordLabel, 0, 4);
        grid.add(passwordField, 1, 4);
        grid.add(submitButton, 1, 5);
        
        // Form submission
        submitButton.setOnAction(e -> {
            Boolean success = Registration.registrationHandler(
                nameField.getText(),
                passwordField.getText(),
                phoneField.getText(),
                emailField.getText(),
                addressField.getText(),
                "customer"
            );
            if (success) {
                AppNavigator.loadView(new LoginController().getView(rootLayout));
            }
        });

        // VBox to hold form and back button
        VBox formContainer = new VBox(10, grid, backButton);
        formContainer.setAlignment(javafx.geometry.Pos.CENTER); // Center align
        
        registrationView.getChildren().addAll(titleLabel, formContainer);
        return registrationView;
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("form-label");
        return label;
    }

    private void styleTextField(TextField field) {
        field.getStyleClass().add("form-field");
    }
}
