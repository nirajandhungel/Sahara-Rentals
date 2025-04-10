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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;

public class RegistrationController {

    public BorderPane getView(BorderPane rootLayout) {
        BorderPane registrationLayout = new BorderPane();
        registrationLayout.getStyleClass().add("registration-page");

        // Add the top bar
        LoginController loginController = new LoginController();
        registrationLayout.setTop(loginController.createTopBar());

        // Left Section (Registration Form)
        VBox formContainer = new VBox(20);
        formContainer.getStyleClass().add("form-container");
        formContainer.setMaxWidth(600);
        formContainer.setAlignment(Pos.TOP_LEFT);
        formContainer.setPadding(new Insets(100, 0, 50, 200));

        Label titleLabel = new Label("Create Your Account");
        titleLabel.getStyleClass().add("login-header-text");

        GridPane grid = new GridPane();
        grid.getStyleClass().add("registration-grid");
        grid.setHgap(20);
        grid.setVgap(15);

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

        // Terms and conditions
        HBox termsBox = new HBox(5);
        termsBox.setAlignment(Pos.CENTER_LEFT);
        CheckBox termsCheck = new CheckBox();
        Label termsLabel = new Label("I agree to the Terms and Conditions");
        termsLabel.getStyleClass().add("terms-label");
        termsBox.getChildren().addAll(termsCheck, termsLabel);

        // Buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        Button submitButton = new Button("Register Now");
        submitButton.getStyleClass().add("primary-button");

        Button backButton = new Button("Back to Login");
        backButton.getStyleClass().add("secondary-button");
        backButton.setOnAction(_ -> AppNavigator.loadView(new LoginController().getView(rootLayout)));

        buttonBox.getChildren().addAll(backButton, submitButton);

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

        formContainer.getChildren().addAll(titleLabel, grid, termsBox, buttonBox);

        // Right Section (Vehicle Image)
        ImageView vehicleImage = new ImageView(new Image(getClass().getResourceAsStream("/registration-vehicles.png")));
        vehicleImage.setPreserveRatio(true);
        vehicleImage.setFitHeight(650);
        vehicleImage.getStyleClass().add("registration-vehicle-image");

        VBox rightSection = new VBox(vehicleImage);
        rightSection.setAlignment(Pos.CENTER_RIGHT);
        rightSection.setPadding(new Insets(80, 0, 0, 0));

        // Add sections to the layout
        registrationLayout.setLeft(formContainer); // Registration form on the left
        registrationLayout.setRight(rightSection); // Vehicle image on the right

        return registrationLayout;
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