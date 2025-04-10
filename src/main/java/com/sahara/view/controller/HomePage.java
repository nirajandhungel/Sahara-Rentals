package com.sahara.view.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import com.sahara.view.util.AppNavigator;

public class HomePage {

    public VBox getView(BorderPane rootLayout) {
        // Main container VBox
        VBox homePageLayout = new VBox();
        homePageLayout.getStyleClass().add("home-page");

        // Top Navigation
        Label logoLabel = new Label("Sahara Rentals");
        logoLabel.getStyleClass().add("logo-text");

        Button signUpBtn = new Button("Sign up");
        signUpBtn.getStyleClass().add("transparent-btn");
        Button logInBtn = new Button("Log in");
        logInBtn.getStyleClass().add("home-primary-btn");

        HBox topRight = new HBox(15, signUpBtn, logInBtn);
        topRight.setAlignment(Pos.CENTER_RIGHT);
        topRight.setPadding(new Insets(0, 30, 0, 0));

        BorderPane topBar = new BorderPane();
        topBar.setLeft(logoLabel);
        topBar.setRight(topRight);
        topBar.getStyleClass().add("top-bar");
        topBar.setPadding(new Insets(15, 0, 15, 50));

        // Add top bar to the main VBox
        homePageLayout.getChildren().add(topBar);

        // Main Content
        Text heading = new Text("Welcome to\nSahara Rentals");
        heading.getStyleClass().add("header-text");

        Text subheading = new Text("Rent Your Perfect Ride\nin Just Minutes!");
        subheading.getStyleClass().add("subheader-text");

        Button bookNowBtn = new Button("Book Now");
        bookNowBtn.getStyleClass().add("cta-btn");
        bookNowBtn.setPrefSize(220, 60);

        // Social Media Icons
        ImageView fbIcon = createSocialIcon("/images/fb-icon.png");
        ImageView igIcon = createSocialIcon("/images/ig-icon.png");
        ImageView waIcon = createSocialIcon("/images/wa-icon.png");

        HBox socialMediaIcons = new HBox(25, fbIcon, igIcon, waIcon);
        socialMediaIcons.setAlignment(Pos.CENTER_LEFT);
        socialMediaIcons.getStyleClass().add("social-media-container");

        VBox leftSection = new VBox(0, heading, subheading, bookNowBtn, socialMediaIcons);
        leftSection.setAlignment(Pos.CENTER_LEFT);
        leftSection.setPadding(new Insets(0, 0, 0, 20));
        VBox.setMargin(heading, new Insets(0, 0, 15, 0));
        VBox.setMargin(subheading, new Insets(0, 0, 30, 0));

        // Vehicle Image
        ImageView vehicleImage = new ImageView(new Image(getClass().getResourceAsStream("/images/t-box-cacao.png")));
        vehicleImage.setPreserveRatio(true);
        vehicleImage.setFitHeight(650);
        vehicleImage.getStyleClass().add("home-vehicle-image");

        HBox contentArea = new HBox(80, leftSection, vehicleImage);
        contentArea.setAlignment(Pos.CENTER);
        contentArea.setPadding(new Insets(80, 0, 40, 90));
        HBox.setHgrow(leftSection, Priority.ALWAYS);

        // Add content area to the main VBox and allow it to expand
        homePageLayout.getChildren().add(contentArea);
        VBox.setVgrow(contentArea, Priority.ALWAYS);

        // Button functionalities
        signUpBtn.setOnAction(_ -> AppNavigator.loadView(new RegistrationController().getView(rootLayout)));
        logInBtn.setOnAction(_ -> AppNavigator.loadView(new LoginController().getView(rootLayout)));
        bookNowBtn.setOnAction(_ ->AppNavigator.loadView(new LoginController().getView(rootLayout)));

        return homePageLayout;
    }

    private ImageView createSocialIcon(String path) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(path)));
        icon.setFitWidth(30);
        icon.setFitHeight(30);
        icon.setPickOnBounds(true);
        icon.setOnMouseEntered(_ -> icon.setStyle("-fx-opacity: 0.7;"));
        icon.setOnMouseExited(_ -> icon.setStyle("-fx-opacity: 1.0;"));
        return icon;
    }
}