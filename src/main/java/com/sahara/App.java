package com.sahara;

import com.sahara.config.AppConfig;
import com.sahara.view.controller.LoginController;
import com.sahara.view.util.AppNavigator;

import javafx.application.Application;
import javafx.scene.Scene;
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
            
            // Set up navigator with root layout and primary stage
            AppNavigator.initialize(rootLayout, primaryStage);
            
            // Configure primary stage
            primaryStage.setTitle("Vehicle Rental System");
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            
            // Load initial view
            AppNavigator.loadView(new LoginController().getView(rootLayout));
            
            // Create scene and show stage
            Scene scene = new Scene(rootLayout, 1000, 700);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());


            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}