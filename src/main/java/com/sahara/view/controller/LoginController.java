package com.sahara.view.controller;

import com.sahara.model.User;
import com.sahara.service.Authenticator;
import com.sahara.view.util.AppNavigator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
public class LoginController {

    public BorderPane getView(BorderPane rootLayout) {
        BorderPane loginLayout = new BorderPane();
        loginLayout.getStyleClass().add("login-page");

        // Use the Top Bar from HomePage
        loginLayout.setTop(createTopBar());

        // Left Section
        ImageView vehicleImage = new ImageView(new Image(getClass().getResourceAsStream("/login-vehicles.png")));
        vehicleImage.setPreserveRatio(true);
        vehicleImage.setFitHeight(660);
        vehicleImage.getStyleClass().add("login-vehicle-image");

        VBox leftSection = new VBox(vehicleImage);
        leftSection.setAlignment(Pos.CENTER_LEFT);
        leftSection.setPadding(new Insets(80, 0, 0, 0)); // Top, Right, Bottom, Left
        leftSection.getStyleClass().add("login-left-section");

        // Right Section
        Text title = new Text("Welcome Back!");
        title.getStyleClass().add("login-header-text");

        Label subTitle = new Label("Please sign in to continue");
        subTitle.getStyleClass().add("login-subheader-text");

        TextField userNameTextField = new TextField();
        userNameTextField.getStyleClass().add("login-form-field");
        userNameTextField.setPromptText("Username");
        userNameTextField.setMaxWidth(350);

        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("login-form-field");
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(350);

        Button loginButton = new Button("Sign In");
        loginButton.getStyleClass().add("login-primary-btn");
        loginButton.setPrefSize(350, 45);

        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");

        Label signUpLabel = new Label("Don't have an account? Sign Up");
        signUpLabel.getStyleClass().add("registration-link");

        VBox formSection = new VBox(10, title, subTitle, userNameTextField, passwordField, errorLabel, loginButton, signUpLabel);
        formSection.setAlignment(Pos.CENTER);
        formSection.setStyle("-fx-background-color:rgb(229, 243, 243); -fx-border-color:rgb(229, 243, 243); -fx-border-radius: 10; -fx-background-radius: 10;");
        formSection.setMaxHeight(500); // Limit the width of the card
        formSection.setPadding(new Insets(10, 60, 40, 60));

        HBox mainContent = new HBox(formSection);
        mainContent.setAlignment(Pos.CENTER);
        mainContent.getStyleClass().add("main-content-container");

        loginLayout.setLeft(leftSection); // Vehicle image at bottom-left
        loginLayout.setCenter(mainContent);

        // Login button action
        loginButton.setOnAction(_ -> {
            String username = userNameTextField.getText();
            String password = passwordField.getText();

            User authenticatedUser = Authenticator.authenticate(username, password);
            if (authenticatedUser != null) {
                System.out.println("Authenticated User: " + authenticatedUser.getRole());
                if (authenticatedUser.getRole().equals("admin")) {
                    AdminController adminController = new AdminController(authenticatedUser);
                    AppNavigator.loadView(adminController.getView(rootLayout));
                } else if (authenticatedUser.getRole().equals("vmt")) {
                    VMTController vmtController = new VMTController(authenticatedUser);
                    AppNavigator.loadView(vmtController.getView(rootLayout));
                } else {
                    UserController dashboardController = new UserController(authenticatedUser);
                    AppNavigator.loadView(dashboardController.getView(rootLayout));
                }
            }
        });

        signUpLabel.setOnMouseClicked(_ -> {
            AppNavigator.loadView(new RegistrationController().getView(rootLayout));
        });

        return loginLayout;
    }
    // Reusable method to create the Top Bar
    public BorderPane createTopBar() {
        Label logoLabel = new Label("Sahara Rentals");
        logoLabel.getStyleClass().add("logo-text");

        // Button signUpBtn = new Button("Sign up");
        // signUpBtn.getStyleClass().add("transparent-btn");
        Button logInBtn = new Button("Home Page");
        logInBtn.getStyleClass().add("home-primary-btn");

        HBox topRight = new HBox(15, logInBtn);
        topRight.setAlignment(Pos.CENTER_RIGHT);
        topRight.setPadding(new Insets(0, 30, 0, 0));

        BorderPane topBar = new BorderPane();
        topBar.setLeft(logoLabel);
        topBar.setRight(topRight);
        topBar.getStyleClass().add("top-bar");
        topBar.setPadding(new Insets(15, 0, 15, 50));

        // Button actions
        logInBtn.setOnAction(_ -> AppNavigator.loadView(new HomePage().getView(new BorderPane())));

        return topBar;
    }
}