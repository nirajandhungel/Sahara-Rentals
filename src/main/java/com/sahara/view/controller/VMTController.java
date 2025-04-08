package com.sahara.view.controller;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;

import com.sahara.model.Bike;
import com.sahara.model.Car;
import com.sahara.model.ElectricVehicle;
import com.sahara.model.User;
import com.sahara.model.Vehicle;
import com.sahara.repository.RentalsDAO;
import com.sahara.repository.VehicleDAO;
import com.sahara.view.util.AppNavigator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class VMTController {
    private final User currentUser;
    private final VehicleDAO vehicleDAO = new VehicleDAO();
    private final RentalsDAO RentalsDAO = new RentalsDAO();
    private final BorderPane rootLayout;

    public VMTController(User user) {
        this.currentUser = user;
        this.rootLayout = new BorderPane();
    }

    public BorderPane getView(BorderPane rootLayout) {
        // this.rootLayout = rootLayout;
        BorderPane vmtDashboard = new BorderPane();
        vmtDashboard.getStyleClass().add("vmt-dashboard-root");

        // Header Section
        vmtDashboard.setTop(createHeader());

        // Main Content - Tabbed Interface
        TabPane mainTabs = new TabPane();
        mainTabs.getStyleClass().add("vmt-tab-pane");

        // Vehicle Management Tab
        Tab vehiclesTab = new Tab("Vehicle Management");
        vehiclesTab.setContent(createVehicleManagementTab());
        vehiclesTab.setClosable(false);


        // Reports Tab
        Tab reportsTab = new Tab("Reports");
        reportsTab.setContent(createReportsTab());
        reportsTab.setClosable(false);

        mainTabs.getTabs().addAll(vehiclesTab, reportsTab);
        vmtDashboard.setCenter(mainTabs);

        return vmtDashboard;
    }

    private HBox createHeader() {
        HBox header = new HBox(20);
        header.getStyleClass().add("vmt-header");
        header.setPadding(new Insets(15, 25, 15, 25));
        header.setAlignment(Pos.CENTER_LEFT);

        // Logo/Title
        ImageView logo = new ImageView(new Image("/images/logo.png"));
        logo.setFitHeight(40);
        logo.setPreserveRatio(true);

        Label title = new Label("SAHARA VEHICLE MANAGEMENT");
        title.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold; -fx-font-size: 18px;");

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // User Info
        Label welcomeLabel = new Label("Welcome, " + currentUser.getUsername());
        welcomeLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

        // Logout Button
        Button logoutBtn = new Button("Logout");
        logoutBtn.getStyleClass().add("vmt-logout-btn");
        logoutBtn.setOnAction(_ -> handleLogout());

        header.getChildren().addAll(logo, title, spacer, welcomeLabel, logoutBtn);
        return header;
    }

    private ScrollPane createVehicleManagementTab() {
        VBox vehicleManagementTab = new VBox(20);
        vehicleManagementTab.setPadding(new Insets(20));
        vehicleManagementTab.setAlignment(Pos.TOP_CENTER);

        // Section Title
        Label title = new Label("Vehicle Inventory Management");
        title.getStyleClass().add("vmt-section-title");

        // Add New Vehicle Button
        Button addVehicleBtn = new Button("Add New Vehicle");
        addVehicleBtn.getStyleClass().add("vmt-action-btn");
        addVehicleBtn.setGraphic(new ImageView(new Image("/icons/add.png", 20, 20, true, true)));
        addVehicleBtn.setOnAction(_ -> showAddVehicleDialog());

        // Vehicle Table
        TableView<Vehicle> vehicleTable = createVehicleTable();
        refreshVehicleTable(vehicleTable);

        // Action Buttons
        HBox actionButtons = new HBox(15);
        actionButtons.setAlignment(Pos.CENTER);

        Button refreshBtn = new Button("Refresh");
        refreshBtn.getStyleClass().add("vmt-secondary-btn");
        refreshBtn.setGraphic(new ImageView(new Image("/icons/refresh.png", 20, 20, true, true)));
        refreshBtn.setOnAction(_ -> refreshVehicleTable(vehicleTable));

        Button editBtn = new Button("Edit Selected");
        editBtn.getStyleClass().add("vmt-action-btn");
        editBtn.setGraphic(new ImageView(new Image("/icons/edit.png", 20, 20, true, true)));
        editBtn.setOnAction(_ -> {
            Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showEditVehicleDialog(selected);
            } else {
                showAlert("No Selection", "Please select a vehicle to edit.");
            }
        });

        Button removeBtn = new Button("Remove Selected");
        removeBtn.getStyleClass().add("vmt-danger-btn");
        removeBtn.setGraphic(new ImageView(new Image("/icons/delete.png", 20, 20, true, true)));
        removeBtn.setOnAction(_ -> {
            Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                confirmAndRemoveVehicle(selected, vehicleTable);
            } else {
                showAlert("No Selection", "Please select a vehicle to remove.");
            }
        });

        actionButtons.getChildren().addAll(refreshBtn, editBtn, removeBtn);

        vehicleManagementTab.getChildren().addAll(title, addVehicleBtn, vehicleTable, actionButtons);

        ScrollPane scrollPane = new ScrollPane(vehicleManagementTab);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private TableView<Vehicle> createVehicleTable() {
        TableView<Vehicle> table = new TableView<>();
        table.getStyleClass().add("vmt-table");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Columns
        TableColumn<Vehicle, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Vehicle, String> imageCol = new TableColumn<>("Image");
        imageCol.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
        imageCol.setCellFactory(col -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            {
                imageView.setFitWidth(80);
                imageView.setFitHeight(50);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(String url, boolean empty) {
                super.updateItem(url, empty);
                if (empty || url == null) {
                    setGraphic(null);
                } else {
                    try {
                        imageView.setImage(new Image(url));
                        setGraphic(imageView);
                    } catch (Exception e) {
                        setGraphic(new Label("No Image"));
                    }
                }
            }
        });

        TableColumn<Vehicle, String> makeCol = new TableColumn<>("Make");
        makeCol.setCellValueFactory(new PropertyValueFactory<>("make"));

        TableColumn<Vehicle, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Vehicle, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Vehicle, Double> rateCol = new TableColumn<>("Daily Rate");
        rateCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Vehicle, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    if (status.equals("Available")) {
                        setTextFill(Color.web("#27ae60"));
                    } else if (status.equals("Maintenance")) {
                        setTextFill(Color.web("#e74c3c"));
                    } else if (status.equals("Rented")) {
                        setTextFill(Color.web("#f39c12"));
                    }
                }
            }
        });

        table.getColumns().addAll(idCol, imageCol, makeCol, modelCol, typeCol, rateCol, statusCol);
        return table;
    }


    private VBox createReportsTab() {
        VBox reportsTab = new VBox(20);
        reportsTab.setPadding(new Insets(20));
        reportsTab.setAlignment(Pos.TOP_CENTER);

        // Section Title
        Label title = new Label("Reports & Analytics");
        title.getStyleClass().add("vmt-section-title");

        // Report Options
        VBox reportOptions = new VBox(15);
        reportOptions.setAlignment(Pos.TOP_CENTER);
        reportOptions.setPadding(new Insets(20));
        reportOptions.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 10;");

        ComboBox<String> reportType = new ComboBox<>();
        reportType.getItems().addAll(
            "Vehicle Utilization Report",
            "Revenue Report",
            "Rentals Trends",
            "Vehicle Status Summary"
        );
        reportType.setPromptText("Select Report Type");

        DatePicker fromDate = new DatePicker(LocalDate.now().minusMonths(1));
        DatePicker toDate = new DatePicker(LocalDate.now());

        Button generateBtn = new Button("Generate Report");
        generateBtn.getStyleClass().add("vmt-action-btn");
        generateBtn.setOnAction(e -> generateReport(reportType.getValue(), fromDate.getValue(), toDate.getValue()));

        reportOptions.getChildren().addAll(
            new Label("Select Report Type:"), reportType,
            new Label("Date Range:"), new HBox(10, fromDate, toDate),
            generateBtn
        );

        // Report Preview Area
        Label previewTitle = new Label("Report Preview");
        previewTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        TextArea reportPreview = new TextArea();
        reportPreview.setEditable(false);
        reportPreview.setWrapText(true);
        reportPreview.setPrefHeight(300);
        reportPreview.setStyle("-fx-font-family: monospace;");

        Button exportBtn = new Button("Export to PDF");
        exportBtn.getStyleClass().add("vmt-action-btn");
        exportBtn.setOnAction(e -> exportReportToPDF());

        reportsTab.getChildren().addAll(title, reportOptions, previewTitle, reportPreview, exportBtn);
        return reportsTab;
    }

    // Helper methods for operations
    private void refreshVehicleTable(TableView<Vehicle> table) {
        ObservableList<Vehicle> vehicles = FXCollections.observableArrayList(vehicleDAO.getAllVehicles());
        table.setItems(vehicles);
    }


    private void showAddVehicleDialog() {
        Dialog<Vehicle> dialog = new Dialog<>();
        dialog.setTitle("Add New Vehicle");
        dialog.setHeaderText("Enter vehicle details");
    
        // Set up form fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
    
        TextField makeField = new TextField();
        makeField.setPromptText("Make");
        TextField modelField = new TextField();
        modelField.setPromptText("Model");
        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Car", "Bike", "ElectricVehicle");
        TextField yearField = new TextField();
        yearField.setPromptText("Year");
        TextField rateField = new TextField();
        rateField.setPromptText("Daily Rate");
        TextField imageUrlField = new TextField();
        imageUrlField.setPromptText("Image URL");
    
        grid.add(new Label("Make:"), 0, 0);
        grid.add(makeField, 1, 0);
        grid.add(new Label("Model:"), 0, 1);
        grid.add(modelField, 1, 1);
        grid.add(new Label("Type:"), 0, 2);
        grid.add(typeCombo, 1, 2);
        grid.add(new Label("Year:"), 0, 3);
        grid.add(yearField, 1, 3);
        grid.add(new Label("Daily Rate:"), 0, 4);
        grid.add(rateField, 1, 4);
        grid.add(new Label("Image URL:"), 0, 5);
        grid.add(imageUrlField, 1, 5);
    
        dialog.getDialogPane().setContent(grid);
    
        // Buttons
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
    
        // Result conversion
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                try {
                    String type = typeCombo.getValue();
                    if (type == null) {
                        showAlert("Input Error", "Please select a vehicle type.");
                        return null;
                    }
    
                    int year = Integer.parseInt(yearField.getText());
                    double rate = Double.parseDouble(rateField.getText());
    
                    switch (type) {
                        case "Car":
                            return new Car(
                                0, // ID will be generated by the database
                                type,
                                makeField.getText() + " " + modelField.getText(),
                                year,
                                rate,
                                "ABC123", // Placeholder for vehicle number
                                imageUrlField.getText(),
                                "Available", // Default status
                                "Details" // Placeholder for details
                            );
                        case "Bike":
                            return new Bike(
                                0,
                                type,
                                makeField.getText() + " " + modelField.getText(),
                                year,
                                rate,
                                "ABC123",
                                imageUrlField.getText(),
                                "Available",
                                "Details"
                            );
                        case "ElectricVehicle":
                            return new ElectricVehicle(
                                0,
                                type,
                                makeField.getText() + " " + modelField.getText(),
                                year,
                                rate,
                                "ABC123",
                                imageUrlField.getText(),
                                "Available",
                                "Details"
                            );
                        default:
                            showAlert("Input Error", "Invalid vehicle type.");
                            return null;
                    }
                } catch (NumberFormatException e) {
                    showAlert("Input Error", "Please enter valid numbers for year and rate.");
                    return null;
                }
            }
            return null;
        });
    
        Optional<Vehicle> result = dialog.showAndWait();
        result.ifPresent(vehicle -> {
            vehicleDAO.addVehicle(vehicle);
            refreshVehicleTable((TableView<Vehicle>) ((ScrollPane) ((TabPane) rootLayout.getCenter())
                .getTabs().get(0).getContent()).getContent());
        });
    }
    private void showEditVehicleDialog(Vehicle vehicle) {
        // Similar to add dialog but pre-populated with vehicle data
        // Implementation would follow similar pattern as showAddVehicleDialog()
    }

    private void confirmAndRemoveVehicle(Vehicle vehicle, TableView<Vehicle> table) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText("Remove Vehicle: " + vehicle.getMake() + " " + vehicle.getModel());
        alert.setContentText("Are you sure you want to permanently remove this vehicle?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            vehicleDAO.removeVehicle(vehicle.getId());
            refreshVehicleTable(table);
        }
    }

   

    private void generateReport(String reportType, LocalDate fromDate, LocalDate toDate) {
        // Implementation would use ReportGenerator service
        // and update the report preview area
    }

    private void exportReportToPDF() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(rootLayout.getScene().getWindow());
        if (file != null) {
            // Implementation would use ReportGenerator to export to PDF
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleLogout() {
        AppNavigator.loadView(new LoginController().getView(new BorderPane()));
    }
}