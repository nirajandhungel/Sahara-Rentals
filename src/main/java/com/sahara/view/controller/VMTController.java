package com.sahara.view.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sahara.config.DatabaseConfig;
import com.sahara.model.Car;
import com.sahara.model.User;
import com.sahara.model.Vehicle;
import com.sahara.view.components.Footer;
import com.sahara.view.components.HeaderView;
import com.sahara.view.components.VehicleCard;
import com.sahara.view.util.AppNavigator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class VMTController {

    @SuppressWarnings("unused")
    private final User currentUser;
    private final ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();
    private GridPane vehiclesGrid; // Store the grid for dynamic updates

    public VMTController(User user) {
        this.currentUser = user;
    }

    public BorderPane getView(BorderPane rootLayout) {
        BorderPane vmtDashboard = new BorderPane();
        vmtDashboard.getStyleClass().add("vmt-dashboard-root");

        // Header Section
        vmtDashboard.setTop(new HeaderView(currentUser, this::handleLogout));

        // Main Content
        TabPane mainContent = new TabPane();
        Tab browseTab = createBrowseTab();
        Tab addVehicleTab = createAddVehicleTab();

        // Add a listener to refresh the "Browse Vehicles" tab when selected
        mainContent.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == browseTab) {
                refreshBrowseTab();
            }
        });

        mainContent.getTabs().addAll(browseTab, addVehicleTab);
        vmtDashboard.setCenter(mainContent);

        // Footer Section
        vmtDashboard.setBottom(new Footer());

        return vmtDashboard;
    }

    private Tab createBrowseTab() {
        Tab browseTab = new Tab("Browse Vehicles");
        ScrollPane scrollPane = new ScrollPane();
        vehiclesGrid = new GridPane();
        vehiclesGrid.setPadding(new Insets(15));
        vehiclesGrid.setHgap(20);
        vehiclesGrid.setVgap(20);

        // Initial load of vehicles
        refreshBrowseTab();

        scrollPane.setContent(vehiclesGrid);
        browseTab.setContent(scrollPane);
        return browseTab;
    }

    private void refreshBrowseTab() {
        // Clear the grid and reload vehicles
        vehiclesGrid.getChildren().clear();
        loadVehicles();
    
        // Add vehicles to the grid
        for (Vehicle vehicle : vehicleList) {
            vehiclesGrid.add(
                    new VehicleCard(vehicle, "See Details", () -> showVehicleDetails(vehicle)),
                    vehicleList.indexOf(vehicle) % 3,
                    vehicleList.indexOf(vehicle) / 3);
        }
    }

    private Tab createAddVehicleTab() {
        Tab addVehicleTab = new Tab("Add Vehicle");
        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(15));

        // Form Fields
        TextField typeField = new TextField();
        typeField.setPromptText("Type (e.g., Car, Bike)");
        TextField brandField = new TextField();
        brandField.setPromptText("Brand/Model");
        TextField yearField = new TextField();
        yearField.setPromptText("Model Year");
        TextField priceField = new TextField();
        priceField.setPromptText("Price per Day");
        TextField numberField = new TextField();
        numberField.setPromptText("Vehicle Number");
        TextField imagePathField = new TextField();
        imagePathField.setPromptText("Image Path");
        TextField detailsField = new TextField();
        detailsField.setPromptText("Details");

        Button addButton = new Button("Add Vehicle");
        addButton.setOnAction(e -> {
            addVehicle(
                    typeField.getText(),
                    brandField.getText(),
                    yearField.getText(),
                    priceField.getText(),
                    numberField.getText(),
                    imagePathField.getText(),
                    detailsField.getText());
            // Clear form fields after adding the vehicle
            typeField.clear();
            brandField.clear();
            yearField.clear();
            priceField.clear();
            numberField.clear();
            imagePathField.clear();
            detailsField.clear();
        });

        formLayout.getChildren().addAll(
                new Label("Add a New Vehicle"),
                typeField, brandField, yearField, priceField, numberField, imagePathField, detailsField, addButton);

        addVehicleTab.setContent(formLayout);
        return addVehicleTab;
    }

    private void loadVehicles() {
        vehicleList.clear();
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM vehicles")) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                vehicleList.add(new Car(
                        rs.getString("type"),
                        rs.getString("brand_model"),
                        rs.getInt("model_year"),
                        rs.getDouble("price_per_day"),
                        rs.getString("number"),
                        rs.getString("image_path"),
                        rs.getString("status"),
                        rs.getString("details")
                ));
            }
        } catch (SQLException e) {
            // e.printStackTrace();
        }
    }

    private void addVehicle(String type, String brand, String year, String price, String number, String imagePath,
                            String details) {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO vehicles (type, brand_model, model_year, price_per_day, number, image_path, status, details) "
                             + "VALUES (?, ?, ?, ?, ?, ?, 'Available', ?)")) {

            stmt.setString(1, type);
            stmt.setString(2, brand);
            stmt.setInt(3, Integer.parseInt(year));
            stmt.setDouble(4, Double.parseDouble(price));
            stmt.setString(5, number);
            stmt.setString(6, imagePath);
            stmt.setString(7, details);

            stmt.executeUpdate();
        } catch (SQLException | NumberFormatException e) {
            // e.printStackTrace();
        }
    }

    private void showVehicleDetails(Vehicle vehicle) {
        // Implementation for showing vehicle details (e.g., in a dialog or new view)
        System.out.println("Showing details for: " + vehicle.getBrand());
    }

    private void handleLogout() {
        // Implementation for handling logout (e.g., navigating to the login view)
        AppNavigator.loadView(new LoginController().getView(new BorderPane()));
    }
}