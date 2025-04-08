package com.sahara.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class RentalItemView extends HBox {
    public RentalItemView(String vehicleName, String period, String cost, String imagePath) {
        super(20);
        getStyleClass().add("rental-item");
        setAlignment(Pos.CENTER_LEFT);

        ImageView carImage;
        try {
            carImage = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        } catch (NullPointerException e) {
            System.err.println("Image not found: " + imagePath + ". Using default image.");
            carImage = new ImageView(new Image(getClass().getResourceAsStream("/images/default.png")));
        }
        carImage.setFitWidth(100);
        carImage.setFitHeight(75);

        Label details = new Label(String.format(
            "Vehicle: %s\nRental Period: %s\nTotal Cost: %s",
            vehicleName, period, cost
        ));

        Button extendButton = new Button("Extend");
        extendButton.getStyleClass().add("action-button");

        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("action-button");

        getChildren().addAll(carImage, details, extendButton, cancelButton);
    }
}