package com.sahara.view.components;

import com.sahara.model.User;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class HeaderView extends HBox {
    public HeaderView(User currentUser, Runnable logoutAction) {
        super(20); // Spacing between elements
        getStyleClass().add("header");
        setAlignment(Pos.CENTER_LEFT); // Aligns content to the left

        // Welcome Label
        Label welcomeLabel = new Label("Welcome, " + currentUser.getUsername() + "!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Spacer to push the logout button to the right
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS); // Makes the spacer expand

        // Logout Button
        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("action-button");
        logoutButton.setOnAction(_ -> logoutAction.run());

        // Add elements to HBox
        getChildren().addAll(welcomeLabel, spacer, logoutButton);
    }
}
