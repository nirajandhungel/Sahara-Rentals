package com.sahara;

import com.sahara.config.AppConfig;
import com.sahara.view.controller.LoginController;
import com.sahara.view.util.AppNavigator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        try {
            AppConfig.configuration();
            // Initialize root layout
            rootLayout = new BorderPane();

            // Initialize TabPane and set it as the center of the root layout
            TabPane mainTabs = new TabPane();
            rootLayout.setCenter(mainTabs);

            // Set up navigator with root layout and primary stage
            AppNavigator.initialize(rootLayout, primaryStage);

            // Set the window title
            primaryStage.setTitle("Sahara - Vehicle Rental ");
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);

            // Load the icon image
            Image icon = new Image(getClass().getResourceAsStream("/sahara-icon.png"));

            // Check if loading the icon failed
            if (icon.isError()) {
                System.err.println("Failed to load icon: " + icon.getException().getMessage());
            } else {
                // Get the original width and height of the icon
                double width = icon.getWidth();
                double height = icon.getHeight();

                // Optionally, resize it to its original size or apply any custom size
                Image resizedIcon = new Image(getClass().getResourceAsStream("/sahara-icon.png"), width, height, false,
                        true);

                // Set the icon with its original dimensions (or resized)
                primaryStage.getIcons().add(resizedIcon);
            }



            // Load initial view
            AppNavigator.loadView(new LoginController().getView(rootLayout));

            // Create scene and show stage
            Scene scene = new Scene(rootLayout, 1000, 700);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            // e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
