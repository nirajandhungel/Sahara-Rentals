package com.sahara.view.util;


import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AppNavigator {
    private static BorderPane rootLayout;
    private static Stage primaryStage;

    public static void initialize(BorderPane root, Stage stage) {
        rootLayout = root;
        primaryStage = stage;
    }

    public static void loadView(Node view) {
        rootLayout.setCenter(view);
    }

    // public static void loadView(Node view, double width, double height) {
    //     primaryStage.setWidth(width);
    //     primaryStage.setHeight(height);
    //     rootLayout.setCenter(view);
    // }

    public static void setTitle(String title) {
        primaryStage.setTitle(title);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
