package com.sahara.view.controller;

import com.sahara.service.Registration;
import com.sahara.view.util.AppNavigator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import javafx.scene.text.Font;
// import javafx.scene.text.FontWeight;

public class RegistrationController {

    public VBox getView(BorderPane rootLayout) {
        // Main container
        VBox registrationView = new VBox();
        registrationView.getStyleClass().add("registration-view");
        registrationView.setAlignment(Pos.CENTER);
        
        // Form container with shadow effect
        VBox formContainer = new VBox(20);
        formContainer.getStyleClass().add("form-container");
        formContainer.setMaxWidth(600);
        formContainer.setAlignment(Pos.TOP_CENTER);
        
        // Header with logo
        HBox headerBox = new HBox(15);
        headerBox.setAlignment(Pos.CENTER);
        
        ImageView logo = new ImageView(new Image("/sahara-icon.png"));
        logo.setFitHeight(50);
        logo.setFitWidth(50);
        logo.setPreserveRatio(true);
        
        Label titleLabel = new Label("Create Your Account");
        titleLabel.getStyleClass().add("title-label");
        
        headerBox.getChildren().addAll(logo, titleLabel);
        
        // Form grid
        GridPane grid = new GridPane();
        grid.getStyleClass().add("registration-grid");
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setPadding(new Insets(25));
        
        // Form fields
        TextField nameField = createFormField("Username", "Enter your username");
        TextField addressField = createFormField("Address", "Enter your address");
        TextField phoneField = createFormField("Phone", "Enter your phone number");
        TextField emailField = createFormField("Email", "Enter your email");
        PasswordField passwordField = createPasswordField("Password", "Create a password");
        PasswordField confirmPasswordField = createPasswordField("Confirm Password", "Re-enter your password");
        
        // Add fields to grid
        grid.add(createFormLabel("Username:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(createFormLabel("Address:"), 0, 1);
        grid.add(addressField, 1, 1);
        grid.add(createFormLabel("Phone:"), 0, 2);
        grid.add(phoneField, 1, 2);
        grid.add(createFormLabel("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(createFormLabel("Password:"), 0, 4);
        grid.add(passwordField, 1, 4);
        grid.add(createFormLabel("Confirm:"), 0, 5);
        grid.add(confirmPasswordField, 1, 5);
        
        // Password requirements
        Label passwordRequirements = new Label("Password must be at least 8 characters long");
        passwordRequirements.getStyleClass().add("password-requirements");
        grid.add(passwordRequirements, 1, 6);
        
        // Button container
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button submitButton = new Button("Register Now");
        submitButton.getStyleClass().add("primary-button");
        
        Button backButton = new Button("Back to Login");
        backButton.getStyleClass().add("secondary-button");
        backButton.setOnAction(_ -> AppNavigator.loadView(new LoginController().getView(rootLayout)));
        
        buttonBox.getChildren().addAll(backButton, submitButton);
        
        // Terms and conditions
        HBox termsBox = new HBox(5);
        termsBox.setAlignment(Pos.CENTER);
        
        CheckBox termsCheck = new CheckBox();
        Label termsLabel = new Label("I agree to the Terms and Conditions");
        termsLabel.getStyleClass().add("terms-label");
        
        termsBox.getChildren().addAll(termsCheck, termsLabel);
       
       
       
        // Form submission
        submitButton.setOnAction(_ -> {
            Boolean success = Registration.registrationHandler(
                    nameField.getText(),
                    passwordField.getText(),
                    phoneField.getText(),
                    emailField.getText(),
                    addressField.getText(),
                    "customer");
            if (success) {
                AppNavigator.loadView(new LoginController().getView(rootLayout));
            }
        });

       // Add all components to form container
       formContainer.getChildren().addAll(
        headerBox,
        grid,
        termsBox,
        buttonBox
    );
    
    registrationView.getChildren().add(formContainer);
    return registrationView;
}

private TextField createFormField(String labelText, String promptText) {
    TextField field = new TextField();
    field.setPromptText(promptText);
    field.getStyleClass().add("form-field");
    return field;
}

private PasswordField createPasswordField(String labelText, String promptText) {
    PasswordField field = new PasswordField();
    field.setPromptText(promptText);
    field.getStyleClass().add("form-field");
    return field;
}

private Label createFormLabel(String text) {
    Label label = new Label(text);
    label.getStyleClass().add("form-label");
    return label;
}
}