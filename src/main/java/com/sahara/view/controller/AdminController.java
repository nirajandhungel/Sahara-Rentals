package com.sahara.view.controller;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.sahara.model.Rentals;
import com.sahara.model.User;
import com.sahara.model.Vehicle;
import com.sahara.repository.PaymentDAO;
import com.sahara.repository.RentalsDAO;
import com.sahara.repository.UserDAO;
import com.sahara.repository.VehicleDAO;
import com.sahara.service.payment.CashPayment;
import com.sahara.service.payment.OnlinePayment;
import com.sahara.service.payment.Payment;
import com.sahara.view.util.AppNavigator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class AdminController {
    private final User currentUser;
    private final UserDAO userDAO = new UserDAO();
    private final VehicleDAO vehicleDAO = new VehicleDAO();
    private final RentalsDAO RentalsDAO = new RentalsDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();
    private BorderPane rootLayout;
    private Label totalRevenueLabel;
    private Label totalPaymentsLabel;

    public AdminController(User user) {
        this.currentUser = user;
    }

    public BorderPane getView(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
        BorderPane adminDashboard = new BorderPane();
        adminDashboard.getStyleClass().add("admin-dashboard-root");

        // Header Section
        adminDashboard.setTop(createHeader());

        // Main Content - Tabbed Interface
        TabPane mainTabs = new TabPane();
        mainTabs.getStyleClass().add("admin-tab-pane");

        // Users Management Tab
        Tab usersTab = new Tab("Total Users");
        usersTab.setContent(createUsersManagementTab());
        usersTab.setClosable(false);

        // Vehicles Management Tab
        Tab vehiclesTab = new Tab("Total Vehicles");
        vehiclesTab.setContent(createVehiclesManagementTab());
        vehiclesTab.setClosable(false);

        // Rentals Management Tab
        Tab rentalsTab = new Tab("All Rentals");
        rentalsTab.setContent(createRentalsManagementTab());
        rentalsTab.setClosable(false);

        // Payments Management Tab
        Tab paymentsTab = new Tab("Total Payments");
        paymentsTab.setContent(createPaymentsManagementTab()); // Ensure this is called
        paymentsTab.setClosable(false);

        // updateSummaryStats();

        mainTabs.getTabs().addAll(usersTab, vehiclesTab, rentalsTab, paymentsTab);
        adminDashboard.setCenter(mainTabs);

        return adminDashboard;
    }

    private HBox createHeader() {
        HBox header = new HBox(20);
        header.getStyleClass().add("admin-header");
        header.setPadding(new Insets(15, 25, 15, 25));
        header.setAlignment(Pos.CENTER_LEFT);

        // Logo/Title
        ImageView logo = new ImageView(new Image("/images/logo.png"));
        logo.setFitHeight(40);
        logo.setPreserveRatio(true);

        Label title = new Label("SAHARA ADMIN DASHBOARD");
        title.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold; -fx-font-size: 18px;");

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // User Info
        Label welcomeLabel = new Label("Welcome, Admin " + currentUser.getUsername());
        welcomeLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

        // Logout Button
        Button logoutBtn = new Button("Logout");
        logoutBtn.getStyleClass().add("admin-logout-btn");
        logoutBtn.setOnAction(e -> handleLogout());

        header.getChildren().addAll(logo, title, spacer, welcomeLabel, logoutBtn);
        return header;
    }

    private ScrollPane createUsersManagementTab() {
        VBox usersManagementTab = new VBox(20);
        usersManagementTab.setPadding(new Insets(20));
        usersManagementTab.setAlignment(Pos.TOP_CENTER);

        // Section Title
        Label title = new Label("User Management");
        title.getStyleClass().add("admin-section-title");

        // Users Table
        TableView<User> usersTable = createUsersTable();
        refreshUsersTable(usersTable);

        // Action Buttons
        HBox actionButtons = new HBox(15);
        actionButtons.setAlignment(Pos.CENTER);

        Button refreshBtn = new Button("Refresh");
        refreshBtn.getStyleClass().add("admin-secondary-btn");
        refreshBtn.setGraphic(new ImageView(new Image("/icons/refresh.png", 20, 20, true, true)));
        refreshBtn.setOnAction(_ -> refreshUsersTable(usersTable));

        actionButtons.getChildren().addAll(refreshBtn);
        usersManagementTab.getChildren().addAll(title, usersTable, actionButtons);

        ScrollPane scrollPane = new ScrollPane(usersManagementTab);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private TableView<User> createUsersTable() {
        TableView<User> table = new TableView<>();
        table.getStyleClass().add("admin-table");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Columns
        TableColumn<User, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> roleCol = new TableColumn<>("Phone Number");
        roleCol.setCellValueFactory(cellData -> cellData.getValue().roleProperty());

        TableColumn<User, Boolean> activeCol = new TableColumn<>("Status");
        activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        activeCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean active, boolean empty) {
                super.updateItem(active, empty);
                if (empty || active == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(active ? "Active" : "Inactive");
                    setTextFill(active ? Color.web("#27ae60") : Color.web("#e74c3c"));
                }
            }
        });

        TableColumn<User, String> phoneCol = new TableColumn<>("Role");
        phoneCol.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());

        table.getColumns().addAll(idCol, usernameCol, emailCol, roleCol, activeCol, phoneCol);
        return table;
    }

    private ScrollPane createVehiclesManagementTab() {
        VBox vehiclesManagementTab = new VBox(20);
        vehiclesManagementTab.setPadding(new Insets(20));
        vehiclesManagementTab.setAlignment(Pos.TOP_CENTER);

        // Section Title
        Label title = new Label("Vehicle Inventory");
        title.getStyleClass().add("admin-section-title");

        // Vehicle Table
        TableView<Vehicle> vehicleTable = createVehiclesTable();
        refreshVehiclesTable(vehicleTable);

        // Action Buttons
        HBox actionButtons = new HBox(15);
        actionButtons.setAlignment(Pos.CENTER);

        Button refreshBtn = new Button("Refresh");
        refreshBtn.getStyleClass().add("admin-secondary-btn");
        refreshBtn.setGraphic(new ImageView(new Image("/icons/refresh.png", 20, 20, true, true)));
        refreshBtn.setOnAction(e -> refreshVehiclesTable(vehicleTable));

        actionButtons.getChildren().addAll(refreshBtn);
        vehiclesManagementTab.getChildren().addAll(title, vehicleTable, actionButtons);

        ScrollPane scrollPane = new ScrollPane(vehiclesManagementTab);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private TableView<Vehicle> createVehiclesTable() {
        TableView<Vehicle> table = new TableView<>();
        table.getStyleClass().add("admin-table");
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

    private ScrollPane createRentalsManagementTab() {
        VBox rentalsManagementTab = new VBox(20);
        rentalsManagementTab.setPadding(new Insets(20));
        rentalsManagementTab.setAlignment(Pos.TOP_CENTER);

        // Section Title
        Label title = new Label("All Rentals");
        title.getStyleClass().add("admin-section-title");

        // Filter Controls
        HBox filterControls = new HBox(15);
        filterControls.setAlignment(Pos.CENTER);

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.getItems().addAll("All", "Pending", "Approved", "Active", "Completed", "Cancelled");
        statusFilter.setValue("All");

        DatePicker fromDate = new DatePicker(LocalDate.now().minusDays(30));
        DatePicker toDate = new DatePicker(LocalDate.now());

        Button filterBtn = new Button("Apply Filters");
        filterBtn.getStyleClass().add("admin-action-btn");
        filterBtn.setOnAction(
                _ -> refreshRentalsTable(null, statusFilter.getValue(), fromDate.getValue(), toDate.getValue()));

        filterControls.getChildren().addAll(
                new Label("Status:"), statusFilter,
                new Label("From:"), fromDate,
                new Label("To:"), toDate,
                filterBtn);

        // Rentals Table
        TableView<Rentals> rentalsTable = createRentalsTable();
        refreshRentalsTable(rentalsTable, "All", LocalDate.now().minusDays(30), LocalDate.now());

        // Action Buttons
        HBox actionButtons = new HBox(15);
        actionButtons.setAlignment(Pos.CENTER);

        Button refreshBtn = new Button("Refresh");
        refreshBtn.getStyleClass().add("admin-secondary-btn");
        refreshBtn.setOnAction(_ -> refreshRentalsTable(rentalsTable, statusFilter.getValue(), fromDate.getValue(),
                toDate.getValue()));

        Button approveBtn = new Button("Approve Rental");
        approveBtn.getStyleClass().add("admin-action-btn");
        approveBtn.setOnAction(_ -> {
            Rentals selected = rentalsTable.getSelectionModel().getSelectedItem();
            if (selected != null && selected.getStatus().equals("Pending")) {
                selected.setStatus("Approved");
                boolean updated = RentalsDAO.updateRentals(selected);
                if (updated) {
                    showAlert("Success", "Rental approved successfully.");
                    refreshRentalsTable(rentalsTable, statusFilter.getValue(), fromDate.getValue(), toDate.getValue());
                } else {
                    showAlert("Error", "Failed to approve rental.");
                }
            } else {
                showAlert("Invalid Selection", "Please select a pending rental to approve.");
            }
        });

        Button activateBtn = new Button("Activate Rental");
        activateBtn.getStyleClass().add("admin-action-btn");
        activateBtn.setOnAction(_ -> {
            Rentals selected = rentalsTable.getSelectionModel().getSelectedItem();
            if (selected != null && selected.getStatus().equals("Approved")) {
                selected.setStatus("Active");
                boolean updated = RentalsDAO.updateRentals(selected);
                if (updated) {
                    // Update vehicle status to Available
                    Vehicle vehicle = selected.getVehicle();
                    vehicle.setStatus("Rented");
                    boolean vehicleUpdated = VehicleDAO.updateVehicle(vehicle);
                    if (vehicleUpdated) {
                        // showAlert("Success", "Rented successfully.");
                        refreshRentalsTable(rentalsTable, statusFilter.getValue(), fromDate.getValue(),
                                toDate.getValue());
                    } else {
                        showAlert("Error", "Failed to update vehicle status.");
                    }

                    showAlert("Success", "Rental activated successfully.");
                    refreshRentalsTable(rentalsTable, statusFilter.getValue(), fromDate.getValue(), toDate.getValue());
                } else {
                    showAlert("Error", "Failed to activate rental.");
                }
            } else {
                showAlert("Invalid Selection", "Please select an approved rental to activate.");
            }
        });

        Button completeBtn = new Button("Complete Rental");
        completeBtn.getStyleClass().add("admin-action-btn");
        completeBtn.setOnAction(_ -> {
            Rentals selected = rentalsTable.getSelectionModel().getSelectedItem();
            if (selected != null && selected.getStatus().equals("Active")) {
                showCompleteRentalDialog(selected, rentalsTable);
            } else {
                showAlert("Invalid Selection", "Please select an active rental to complete.");
            }
        });

        Button cancelBtn = new Button("Cancel Rental");
        cancelBtn.getStyleClass().add("admin-danger-btn");
        cancelBtn.setOnAction(e -> {
            Rentals selected = rentalsTable.getSelectionModel().getSelectedItem();
            if (selected != null
                    && (selected.getStatus().equals("Pending") || selected.getStatus().equals("Approved"))) {
                selected.setStatus("Cancelled");
                boolean updated = RentalsDAO.updateRentals(selected);
                if (updated) {
                    // Update vehicle status to Available
                    Vehicle vehicle = selected.getVehicle();
                    vehicle.setStatus("Available");
                    boolean vehicleUpdated = VehicleDAO.updateVehicle(vehicle);
                    if (vehicleUpdated) {
                        showAlert("Success", "Rental canceled successfully.");
                        refreshRentalsTable(rentalsTable, statusFilter.getValue(), fromDate.getValue(),
                                toDate.getValue());
                    } else {
                        showAlert("Error", "Failed to update vehicle status.");
                    }
                } else {
                    showAlert("Error", "Failed to cancel rental.");
                }
            } else {
                showAlert("Invalid Selection", "Only pending or approved rentals can be canceled.");
            }
        });

        actionButtons.getChildren().addAll(refreshBtn, approveBtn, activateBtn, completeBtn, cancelBtn);
        rentalsManagementTab.getChildren().addAll(title, filterControls, rentalsTable, actionButtons);

        ScrollPane scrollPane = new ScrollPane(rentalsManagementTab);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private TableView<Rentals> createRentalsTable() {
        TableView<Rentals> table = new TableView<>();
        table.getStyleClass().add("admin-table");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Columns
        TableColumn<Rentals, Integer> idCol = new TableColumn<>("Rental ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Rentals, String> customerCol = new TableColumn<>("Customer ID");
        customerCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<Rentals, String> vehicleCol = new TableColumn<>("Vehicle");
        vehicleCol.setCellValueFactory(cell -> cell.getValue().getVehicle().makeProperty().concat(" ")
                .concat(cell.getValue().getVehicle().modelProperty()));

        TableColumn<Rentals, String> datesCol = new TableColumn<>("Rental Period");
        datesCol.setCellValueFactory(cell -> cell.getValue().startDateProperty().asString().concat(" to ")
                .concat(cell.getValue().endDateProperty().asString()));

        TableColumn<Rentals, Double> totalCol = new TableColumn<>("Total Cost");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        TableColumn<Rentals, String> statusCol = new TableColumn<>("Status");
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
                    switch (status) {
                        case "Pending":
                            setTextFill(Color.web("#f39c12"));
                            break;
                        case "Approved":
                            setTextFill(Color.web("#3498db"));
                            break;
                        case "Active":
                            setTextFill(Color.web("#27ae60"));
                            break;
                        case "Completed":
                            setTextFill(Color.web("#2c3e50"));
                            break;
                        case "Cancelled":
                            setTextFill(Color.web("#e74c3c"));
                            break;
                    }
                }
            }
        });

        table.getColumns().addAll(idCol, customerCol, vehicleCol, datesCol, totalCol, statusCol);
        return table;
    }

    private ScrollPane createPaymentsManagementTab() {
        // totalRevenueLabel = new Label("Total Revenue: $0.00");
        // totalRevenueLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        // totalPaymentsLabel = new Label("Total Payments: 0");
        // totalPaymentsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
       
       
        // updateSummaryStats();
        VBox paymentsManagementTab = new VBox(20);
        paymentsManagementTab.setPadding(new Insets(20));
        paymentsManagementTab.setAlignment(Pos.TOP_CENTER);

        // Section Title
        Label title = new Label("Payment Records");
        title.getStyleClass().add("admin-section-title");

        // Filter Controls
        HBox filterControls = new HBox(15);
        filterControls.setAlignment(Pos.CENTER);

        DatePicker fromDate = new DatePicker(LocalDate.now().minusDays(30));
        DatePicker toDate = new DatePicker(LocalDate.now());

        Button filterBtn = new Button("Apply Filters");
        filterBtn.getStyleClass().add("admin-action-btn");
        filterBtn.setOnAction(_ -> refreshPaymentsTable(null, fromDate.getValue(), toDate.getValue()));

        filterControls.getChildren().addAll(
                new Label("From:"), fromDate,
                new Label("To:  "), toDate,
                filterBtn);

        // Payments Table
        TableView<Payment> paymentsTable = createPaymentsTable();
        refreshPaymentsTable(paymentsTable, LocalDate.now().minusDays(30), LocalDate.now());

        // Summary Stats
        HBox statsBox = new HBox(20);
        statsBox.setAlignment(Pos.CENTER);

        // Update summary stats

        totalRevenueLabel = new Label("Total Revenue: $0.00"); // Initialize the field
        totalRevenueLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        totalPaymentsLabel = new Label("Total Payments: 0"); // Initialize the field
        totalPaymentsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        statsBox.getChildren().addAll(totalRevenueLabel, totalPaymentsLabel);

        updateSummaryStats();

        // Action Buttons
        HBox actionButtons = new HBox(15);
        actionButtons.setAlignment(Pos.CENTER);

        Button refreshBtn = new Button("Refresh");
        refreshBtn.getStyleClass().add("admin-secondary-btn");
        refreshBtn.setOnAction(_ -> refreshPaymentsTable(paymentsTable, fromDate.getValue(), toDate.getValue()));

        Button exportBtn = new Button("Export to CSV");
        exportBtn.getStyleClass().add("admin-action-btn");
        exportBtn.setOnAction(_ -> exportPaymentsToCSV());

        actionButtons.getChildren().addAll(refreshBtn, exportBtn);
        paymentsManagementTab.getChildren().addAll(title, filterControls, statsBox, paymentsTable, actionButtons);

        ScrollPane scrollPane = new ScrollPane(paymentsManagementTab);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private TableView<Payment> createPaymentsTable() {
        TableView<Payment> table = new TableView<>();
        table.getStyleClass().add("admin-table");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Columns
        TableColumn<Payment, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Payment, Integer> rentalIdCol = new TableColumn<>("Rental ID");
        rentalIdCol.setCellValueFactory(new PropertyValueFactory<>("rentalId"));

        TableColumn<Payment, Integer> userIdCol = new TableColumn<>("User ID");
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<Payment, String> vehicleCol = new TableColumn<>("Vehicle");
        vehicleCol.setCellValueFactory(new PropertyValueFactory<>("vehicleName"));

        TableColumn<Payment, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty || amount == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.format("$%.2f", amount));
                    setTextFill(Color.web("#27ae60"));
                }
            }
        });

        TableColumn<Payment, String> methodCol = new TableColumn<>("Method");
        methodCol.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        TableColumn<Payment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

        TableColumn<Payment, String> statusCol = new TableColumn<>("Status");
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
                    setTextFill(status.equals("Completed") ? Color.web("#27ae60") : Color.web("#e74c3c"));
                }
            }
        });

        TableColumn<Payment, String> processedByCol = new TableColumn<>("Processed By");
        processedByCol.setCellValueFactory(new PropertyValueFactory<>("processedBy"));

        table.getColumns().addAll(idCol, rentalIdCol, userIdCol, vehicleCol, amountCol,
                methodCol, dateCol, statusCol, processedByCol);
        return table;
    }

    // Helper methods for operations
    private void refreshUsersTable(TableView<User> table) {
        ObservableList<User> users = FXCollections.observableArrayList(userDAO.getAllUsers());
        table.setItems(users);
    }

    private void refreshVehiclesTable(TableView<Vehicle> table) {
        ObservableList<Vehicle> vehicles = FXCollections.observableArrayList(vehicleDAO.getAllVehicles());
        table.setItems(vehicles);
    }

    private void refreshRentalsTable(TableView<Rentals> table, String statusFilter, LocalDate fromDate,
            LocalDate toDate) {
        List<Rentals> rentals;
        if (statusFilter.equals("All")) {
            rentals = RentalsDAO.getRentalssBetweenDates(fromDate, toDate);
        } else {
            rentals = RentalsDAO.getRentalssByStatusAndDate(statusFilter, fromDate, toDate);
        }
        table.setItems(FXCollections.observableArrayList(rentals));
    }

    private void refreshPaymentsTable(TableView<Payment> table, LocalDate fromDate, LocalDate toDate) {
        // Update summary stats
        updateSummaryStats();
        if (table == null) {
            System.err.println("Error: TableView is null. Cannot refresh payments table.");
            return;
        }

        List<Payment> payments = paymentDAO.getPaymentsBetweenDates(fromDate, toDate);
        table.setItems(FXCollections.observableArrayList(payments));

        // Update summary stats
        // double total = payments.stream().mapToDouble(Payment::getAmount).sum();

        // if (totalRevenueLabel != null) {
        // totalRevenueLabel.setText(String.format("Total Revenue: $%.2f", total));
        // } else {
        // System.err.println("Label with id 'totalRevenueLabel' not found.");
        // }

        // if (totalPaymentsLabel != null) {
        // totalPaymentsLabel.setText(String.format("Total Payments: %d",
        // payments.size()));
        // } else {
        // System.err.println("Label with id 'totalPaymentsLabel' not found.");
        // }
    }

    private void showCompleteRentalDialog(Rentals Rentals, TableView<Rentals> table) {
       
        Dialog<Payment> dialog = new Dialog<>();
        dialog.setTitle("Complete Rental");
        dialog.setHeaderText("Enter payment details to complete rental");

        // Set up form fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Label RentalsLabel = new Label("Rentals ID: " + Rentals.getId());
        Label customerLabel = new Label("Customer ID: " + Rentals.getUserId());
        Label vehicleLabel = new Label(
                "Vehicle: " + Rentals.getVehicle().getMake() + " " + Rentals.getVehicle().getModel());
        Label periodLabel = new Label("Period: " + Rentals.getRentalDate() + " to " + Rentals.getReturnDate());
        Label amountLabel = new Label("Amount Due: $" + Rentals.getTotalCost());

        TextField amountField = new TextField(String.valueOf(Rentals.getTotalCost()));
        ComboBox<String> methodCombo = new ComboBox<>();
        methodCombo.getItems().addAll("Cash", "Online");
        methodCombo.setValue("Cash");

        grid.add(RentalsLabel, 0, 0, 2, 1);
        grid.add(customerLabel, 0, 1, 2, 1);
        grid.add(vehicleLabel, 0, 2, 2, 1);
        grid.add(periodLabel, 0, 3, 2, 1);
        grid.add(amountLabel, 0, 4, 2, 1);
        grid.add(new Separator(), 0, 5, 2, 1);
        grid.add(new Label("Payment Amount:"), 0, 6);
        grid.add(amountField, 1, 6);
        grid.add(new Label("Payment Method:"), 0, 7);
        grid.add(methodCombo, 1, 7);

        dialog.getDialogPane().setContent(grid);

        // Buttons
        ButtonType completeButton = new ButtonType("Complete", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(completeButton, ButtonType.CANCEL);

        // Result conversion
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == completeButton) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    Payment payment;

                    // Determine payment type
                    if (methodCombo.getValue().equalsIgnoreCase("Cash")) {
                        payment = new CashPayment();
                    } else {
                        payment = new OnlinePayment();
                    }

                    // Set payment details
                    payment.setRentalId(Rentals.getId());
                    payment.setUserId(Rentals.getUserId());
                    payment.setVehicleName(Rentals.getVehicleName());
                    payment.setAmount(amount);
                    payment.setPaymentMethod(methodCombo.getValue());
                    payment.setPaymentDate(LocalDate.now().toString());
                    payment.setStatus("Completed");
                    payment.setProcessedBy(currentUser.getUsername());

                    // Add payment to the database
                    boolean paymentAdded = PaymentDAO.addPayment(payment);
                    if (!paymentAdded) {
                        showAlert("Payment Error", "Failed to add payment to the database.");
                        return null;
                    }

                    // Update Rentals status
                    Rentals.setStatus("Completed");
                    boolean rentalUpdated = RentalsDAO.updateRentals(Rentals);
                    if (!rentalUpdated) {
                        showAlert("Rental Update Error", "Failed to update rental status.");
                        return null;
                    }

                    // Update vehicle status to Available
                    Vehicle vehicle = Rentals.getVehicle();
                    vehicle.setStatus("Available");
                    boolean vehicleUpdated = VehicleDAO.updateVehicle(vehicle);
                    if (!vehicleUpdated) {
                        showAlert("Vehicle Update Error", "Failed to update vehicle status.");
                        return null;
                    }

                    return payment;

                } catch (NumberFormatException e) {
                    showAlert("Input Error", "Please enter a valid payment amount.");
                    return null;
                }
            }
            return null;
        });

        Optional<Payment> result = dialog.showAndWait();
        result.ifPresent(payment -> {
            // Refresh rentals table
            refreshRentalsTable(table, "All", LocalDate.now().minusDays(30), LocalDate.now());

            // Safely access the TabPane in the root layout
            Node centerNode = rootLayout.getCenter();
            if (centerNode instanceof TabPane) {
                TabPane tabPane = (TabPane) centerNode;

                // Access the payments tab
                if (tabPane.getTabs().size() > 3) { // Ensure the tab exists
                    ScrollPane paymentsScroll = (ScrollPane) tabPane.getTabs().get(3).getContent();
                    VBox paymentsContent = (VBox) paymentsScroll.getContent();
                    TableView<Payment> paymentsTable = (TableView<Payment>) paymentsContent.getChildren().get(3);
                    refreshPaymentsTable(paymentsTable, LocalDate.now().minusDays(30), LocalDate.now());
                } else {
                    System.err.println("Error: Payments tab not found.");
                }
            } else {
                System.err.println("Error: Center node is not a TabPane.");
            }
        });
    }

    private void cancelRental(Rentals Rentals, TableView<Rentals> table) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancellation");
        alert.setHeaderText("Cancel Rental #" + Rentals.getId());
        alert.setContentText("Are you sure you want to cancel this rental?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Rentals.setStatus("Cancelled");
            RentalsDAO.updateRentals(Rentals);
            refreshRentalsTable(table, "All", LocalDate.now().minusDays(30), LocalDate.now());
        }
    }

    private void exportPaymentsToCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Payments");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(rootLayout.getScene().getWindow());
        if (file != null) {
            // Implementation would export payments to CSV
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateSummaryStats() {
        double[] stats = PaymentDAO.getTotalRevenueAndPayments();
        double totalRevenue = stats[0];
        int totalPayments = (int) stats[1];

        // Update the labels
        if (totalRevenueLabel != null) {
            totalRevenueLabel.setText(String.format("Total Revenue: $%.2f", totalRevenue));
        } else {
            System.err.println("Label with id 'totalRevenueLabel' not found.");
        }

        if (totalPaymentsLabel != null) {
            totalPaymentsLabel.setText(String.format("Total Payments: %d", totalPayments));
        } else {
            System.err.println("Label with id 'totalPaymentsLabel' not found.");
        }
    }

    private void handleLogout() {
        AppNavigator.loadView(new LoginController().getView(new BorderPane()));
    }
}