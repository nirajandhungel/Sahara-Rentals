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
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(vehicle.getImagePath())));
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
        actionButton.setOnAction(e -> buttonAction.run());

        getChildren().addAll(imageView, nameLabel, detailsLabel, actionButton);
    }
}