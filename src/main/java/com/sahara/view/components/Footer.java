package com.sahara.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Footer extends HBox {
    public Footer() {
        super(10);
        setAlignment(Pos.CENTER);
        getStyleClass().add("footer");

        Label footerLabel = new Label("© 2025 Vehicle Rental System. All rights reserved.");
        footerLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50;");

        getChildren().add(footerLabel);
    }
}