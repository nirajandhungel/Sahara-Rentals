package com.sahara.view.controller;

import java.util.ArrayList;
import java.util.List;

import com.sahara.model.Car;
import com.sahara.model.User;
import com.sahara.model.Vehicle;
import com.sahara.view.components.Footer;
import com.sahara.view.components.HeaderView;
import com.sahara.view.components.RentalItemView;
import com.sahara.view.components.VehicleCard;
import com.sahara.view.util.AppNavigator;
import com.sahara.view.util.ViewUtil;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class UserController {

    private final User currentUser;
    private final List<Vehicle> vehicleList = new ArrayList<>(); // Store vehicles for dynamic updates

    public UserController(User user) {
        this.currentUser = user;
        loadSampleVehicles(); // Load sample vehicles into the list
    }

    public BorderPane getView(BorderPane rootLayout) {
        BorderPane dashboard = new BorderPane();
        dashboard.getStyleClass().add("dashboard-root");

        // Header Section
        dashboard.setTop(new HeaderView(currentUser, this::handleLogout));

        // Main Content
        TabPane mainContent = new TabPane();
        mainContent.getTabs().addAll(
                createBrowseTab(),
                createRentalsTab(),
                createProfileTab());
        dashboard.setCenter(mainContent);

        // Footer Section
        dashboard.setBottom(new Footer());

        return dashboard;
    }

    private Tab createBrowseTab() {
        Tab browseTab = new Tab("Browse Vehicles");
        ScrollPane scrollPane = new ScrollPane();
        GridPane vehiclesGrid = new GridPane();
        vehiclesGrid.setPadding(new Insets(15));
        vehiclesGrid.setHgap(20);
        vehiclesGrid.setVgap(20);

        // Load vehicles dynamically
        refreshBrowseTab(vehiclesGrid);

        scrollPane.setContent(vehiclesGrid);
        browseTab.setContent(scrollPane);
        return browseTab;
    }

    private void refreshBrowseTab(GridPane vehiclesGrid) {
        // Clear the grid and reload vehicles
        vehiclesGrid.getChildren().clear();

        // Add vehicles to the grid
        for (Vehicle vehicle : vehicleList) {
            vehiclesGrid.add(
                    new VehicleCard(vehicle, "Rent Now", () -> rentVehicle(vehicle)),
                    vehicleList.indexOf(vehicle) % 3,
                    vehicleList.indexOf(vehicle) / 3);
        }
    }

    private Tab createRentalsTab() {
        Tab rentalsTab = new Tab("My Rentals");
        VBox rentalsContent = new VBox(15);
        rentalsContent.setPadding(new Insets(15));

        rentalsContent.getChildren().addAll(
                new Label("Active Rentals"),
                new RentalItemView("Toyota Camry", "3 days", "$450", "/images/car.png"),
                new RentalItemView("Honda Civic", "5 days", "$750", "/images/car.png"));

        rentalsTab.setContent(rentalsContent);
        return rentalsTab;
    }

    private Tab createProfileTab() {
        Tab profileTab = new Tab("Profile");
        VBox profileContent = new VBox(15);
        profileContent.setPadding(new Insets(20));

        TextField nameField = new TextField(currentUser.getUsername());
        TextField emailField = new TextField(currentUser.getEmail());
        PasswordField passwordField = new PasswordField();

        profileContent.getChildren().addAll(
                new Label("Profile Settings"),
                ViewUtil.createFormRow("Name:", nameField),
                ViewUtil.createFormRow("Email:", emailField),
                ViewUtil.createFormRow("Password:", passwordField),
                new Button("Save Changes"));

        profileTab.setContent(profileContent);
        return profileTab;
    }

    private void handleLogout() {
        AppNavigator.loadView(new LoginController().getView(new BorderPane()));
    }

    private void rentVehicle(Vehicle vehicle) {
        // Implementation for renting the vehicle
        System.out.println("Renting vehicle: " + vehicle.getBrand());
        // Add logic to update the database or perform other renting actions
        vehicleList.remove(vehicle); // Remove the rented vehicle from the list
    }

    private void loadSampleVehicles() {
        // Load sample vehicles into the list
        for (int i = 0; i < 6; i++) {
            vehicleList.add(new Car(
                    "Car",
                    "Brand Model " + (i + 1),
                    2025,
                    50000.0,
                    "Number " + (i + 1),
                    "/images/vehicle.png",
                    "Available",
                    "Details of Car " + (i + 1)
            ));
        }
    }

    // private void showRentalDialog(Vehicle vehicle) {
    //     // Implementation for rental dialog
    //     System.out.println("Showing rental dialog for: " + vehicle.getBrand());
    // }
}