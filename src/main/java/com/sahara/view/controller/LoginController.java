package com.sahara.view.controller;

import com.sahara.model.User;
import com.sahara.service.Authenticator;
import com.sahara.view.util.AppNavigator;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class LoginController {

    public GridPane getView(BorderPane rootLayout) {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("login-grid");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        // Load the CSS file
        // grid.getStylesheets().add(getClass().getResource("login.css").toExternalForm());

        // Title
        Label titleLabel = new Label("Login");
        titleLabel.getStyleClass().add("title-label");
        grid.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);

        // Username
        Label userNameLabel = new Label("Username:");
        userNameLabel.getStyleClass().add("form-label");
        grid.add(userNameLabel, 0, 1);

        TextField userNameTextField = new TextField();
        userNameTextField.getStyleClass().add("form-field");
        userNameTextField.setPromptText("Enter username");
        grid.add(userNameTextField, 1, 1);

        // Password
        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("form-label");
        grid.add(passwordLabel, 0, 2);

        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("form-field");
        passwordField.setPromptText("Enter password");
        grid.add(passwordField, 1, 2);

        // Login Button
        Button loginButton = new Button("Log in");
        loginButton.getStyleClass().add("login-button");
        grid.add(loginButton, 1, 3);

        // Registration Link
        Label registrationLabel = new Label("Don't have an account? Register here.");
        registrationLabel.getStyleClass().add("registration-link");
        grid.add(registrationLabel, 1, 4);

        // Login button action
        loginButton.setOnAction(e -> {
            String username = userNameTextField.getText();
            String password = passwordField.getText();

            User authenticatedUser = Authenticator.authenticate(username, password);
            if (authenticatedUser != null) {
                System.out.println("Authenticated User: " + authenticatedUser.role());
                // if (authenticatedUser.role().equals("admin")) {
                //     AdminController adminController = new AdminController(authenticatedUser);
                //     AppNavigator.loadView(adminController.getView(rootLayout));
                //     rootLayout.setCenter(adminController.getView(rootLayout));
                // } 
                // else
                 if (authenticatedUser.role().equals("vmt")) {
                    VMTController VMTController = new VMTController(authenticatedUser);
                    AppNavigator.loadView(VMTController.getView(rootLayout));
                    rootLayout.setCenter(VMTController.getView(rootLayout));
                }
                else{
                UserController dashboardController = new UserController(authenticatedUser);
                AppNavigator.loadView(dashboardController.getView(rootLayout));
                rootLayout.setCenter(dashboardController.getView(rootLayout));
                }
            }
        });

        // Registration label click action
        registrationLabel.setOnMouseClicked(event -> {
            AppNavigator.loadView(new RegistrationController().getView(rootLayout));
        });

        return grid;
    }
}