package com.sahara.view.components;

import com.sahara.model.Vehicle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class VehicleCard extends VBox {
    public VehicleCard(Vehicle vehicle, String buttonText, Runnable buttonAction) {
        super(10);
        getStyleClass().add("vehicle-card");

        // Vehicle Image
        ImageView imageView;
        try {
            imageView = new ImageView(new Image(getClass().getResourceAsStream(vehicle.getImagePath())));
        } catch (NullPointerException e) {
            System.err.println("Image not found: " + vehicle.getImagePath() + ". Using default image.");
            imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/default.png")));
        }
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        imageView.getStyleClass().add("vehicle-image");

        // Vehicle Type
        Label nameLabel = new Label(vehicle.getType());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        // Vehicle Details
        Label detailsLabel = new Label(vehicle.getDetails());
        detailsLabel.setWrapText(true);

        // Action Button
        Button actionButton = new Button(buttonText);
        actionButton.getStyleClass().add("action-button");
        actionButton.setOnAction(_ -> buttonAction.run());

        getChildren().addAll(imageView, nameLabel, detailsLabel, actionButton);
    }
}