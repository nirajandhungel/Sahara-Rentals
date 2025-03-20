package com.sahara.view.components;

import com.sahara.model.User;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class HeaderView extends HBox {
    public HeaderView(User currentUser, Runnable logoutAction) {
        super(20);
        getStyleClass().add("header");
        setAlignment(Pos.CENTER_LEFT);

        Label welcomeLabel = new Label("Welcome, !");
        // Label welcomeLabel = new Label("Welcome, " + currentUser.getUsername() + "!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("action-button");
        logoutButton.setOnAction(e -> logoutAction.run());

        getChildren().addAll(welcomeLabel, logoutButton);
    }
}