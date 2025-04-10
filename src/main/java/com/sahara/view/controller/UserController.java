package com.sahara.view.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.sahara.config.DatabaseConfig;
import com.sahara.model.Rentals; // Use Rentals instead of Rental
import com.sahara.model.User;
import com.sahara.model.Vehicle;
import com.sahara.repository.RentalsDAO;
import com.sahara.repository.VehicleDAO;
import com.sahara.service.RentalService;
import com.sahara.service.UserService;
import com.sahara.view.util.AlertUtils;
import com.sahara.view.util.AppNavigator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class UserController {
    private final User currentUser;
    private final RentalService rentalService;
    private ScrollPane rentalsScrollPane; // Class variable to reference the ScrollPane
    private VBox rentalsContainer; // Class variable to reference the content VBox
    // private VBox rentalsContainer;

    public UserController(User user) {
        this.currentUser = user;
        this.rentalService = new RentalService();
    }

    public BorderPane getView(BorderPane rootLayout) {
        BorderPane dashboard = new BorderPane();
        dashboard.getStyleClass().add("dashboard-root");

        // Top Navigation Bar
        HBox topBar = createTopBar();
        dashboard.setTop(topBar);

        // Main Content Area
        StackPane contentPane = new StackPane();

        // Create a tabbed interface for different sections
        TabPane tabPane = new TabPane();
        tabPane.getStyleClass().add("dashboard-tabs");

        // Available Vehicles Tab
        Tab vehiclesTab = new Tab("Available Vehicles", createVehiclesView());
        vehiclesTab.setClosable(false);

        // My Rentals Tab
        Tab rentalsTab = new Tab("My Rentals", createRentalsView(currentUser));
        rentalsTab.setClosable(false);

        // Profile Tab
        Tab profileTab = new Tab("My Profile", createProfileView());
        profileTab.setClosable(false);

        tabPane.getTabs().addAll(vehiclesTab, rentalsTab, profileTab);
        contentPane.getChildren().add(tabPane);

        dashboard.setCenter(contentPane);

        return dashboard;
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(20);
        topBar.getStyleClass().add("dashboard-header");
        topBar.setPadding(new Insets(15, 25, 15, 25));
        topBar.setAlignment(Pos.CENTER_LEFT);
        // topBar.setStyle("-fx-background-color: #ffffff;");

        // Logo/Title this is logo
        ImageView logo = new ImageView(new Image("sahara-icon.png"));
        logo.setFitHeight(40);
        logo.setPreserveRatio(true);

        Label title = new Label("Sahara Users Dashboard");
        title.setStyle("-fx-text-fill: #00c030; -fx-font-weight: bold; -fx-font-size: 18px;");

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // User greeting
        Label greeting = new Label("Welcome, " + currentUser.getUsername() + "!");
        greeting.setStyle("-fx-text-fill: #2c3e50; -fx-font-weight: bold;");

        // Logout button
        Button logoutBtn = new Button("Logout");
        logoutBtn.getStyleClass().add("action-button");
        logoutBtn.setOnAction(_ -> {
            AppNavigator.loadView(new LoginController().getView(new BorderPane()));
            // Handle logout logic
        });

        topBar.getChildren().addAll(title, spacer, greeting, logoutBtn);
        return topBar;
    }

    private ScrollPane createVehiclesView() {
        VBox vehiclesContainer = new VBox(20);
        vehiclesContainer.setPadding(new Insets(20));
        vehiclesContainer.setAlignment(Pos.TOP_CENTER);

        Label sectionTitle = new Label("Available Vehicles");
        sectionTitle.getStyleClass().add("section-title");

        // Get available vehicles from service
        List<Vehicle> vehicles = rentalService.getAvailableVehicles();

        // Create a grid for vehicle cards
        GridPane vehiclesGrid = new GridPane();
        vehiclesGrid.setHgap(20);
        vehiclesGrid.setVgap(20);
        vehiclesGrid.setAlignment(Pos.TOP_CENTER);

        int col = 0;
        int row = 0;
        for (Vehicle vehicle : vehicles) {
            VBox vehicleCard = createVehicleCard(vehicle);
            vehiclesGrid.add(vehicleCard, col, row);

            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }

        vehiclesContainer.getChildren().addAll(sectionTitle, vehiclesGrid);

        ScrollPane scrollPane = new ScrollPane(vehiclesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return scrollPane;
    }

    private VBox createVehicleCard(Vehicle vehicle) {
        VBox card = new VBox(15);
        card.getStyleClass().add("vehicle-card");
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.TOP_CENTER);

        // Vehicle image placeholder
        ImageView imageView = new ImageView();
        try {
            imageView.setImage(new Image(vehicle.imagePath()));
        } catch (Exception e) {
            // Default image if loading fails
            imageView.setImage(new Image("/images/vehicle.png"));
        }
        imageView.setFitWidth(200);
        imageView.setFitHeight(120);
        imageView.getStyleClass().add("vehicle-image");

        // Vehicle details
        Label modelLabel = new Label(vehicle.model());
        modelLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label detailsLabel = new Label(String.format("%s • %s ",
                vehicle.type(), vehicle.transmission()));

        Label priceLabel = new Label(String.format("$%.2f/day", vehicle.dailyRate()));
        priceLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #00c030;");

        // Rental controls
        DatePicker startDate = new DatePicker(LocalDate.now());
        DatePicker endDate = new DatePicker(LocalDate.now().plusDays(1));

        Button rentBtn = new Button("Rent Now");
        rentBtn.getStyleClass().add("action-button");

        rentBtn.setOnAction(_ -> {
            // Validate rental dates
            if (startDate.getValue() == null || endDate.getValue() == null) {
                // AlertUtils error
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Invalid Dates", "Please select valid rental dates.");
                return;
            }

            // Validation of date : cant rent a vehicle with date less than today
            if (startDate.getValue().isBefore(LocalDate.now())) {
                // AlertUtils error
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Invalid Dates", "Start date must be today or later.");
                return;
            }
            if (endDate.getValue().isBefore(LocalDate.now())) {
                // AlertUtils error#
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Invalid Dates", "End date must be today or later.");
                return;
            }

            if (endDate.getValue().isBefore(startDate.getValue())) {
                // AlertUtils error
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Invalid Dates", "End date must be after start date.");
                return;
            }

            // Calculate rental duration and cost
            long rentalDays = endDate.getValue().toEpochDay() - startDate.getValue().toEpochDay();
            if (rentalDays <= 0) {
                // AlertUtils error
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Invalid Dates", "Rental period must be at least 1 day.");
                return;
            }
            if (rentalDays > 30) {
                // AlertUtils error
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Invalid Dates", "Rental period cannot exceed 30 days.");
                return;
            }

            double totalCost = vehicle.dailyRate() * rentalDays;

            // Show confirmation dialog
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Confirm Rental");
            confirmDialog.setHeaderText("Confirm Rental for " + vehicle.model());
            confirmDialog.setContentText(String.format(
                    "Please confirm the following rental details:\n\n" +
                            "Vehicle: %s\n" +
                            "Type: %s\n" +
                            "Rental Period: %s to %s\n" +
                            "Duration: %d days\n" +
                            "Daily Rate: $%.2f\n" +
                            "Total Cost: $%.2f",
                    vehicle.model(),
                    vehicle.type(),
                    startDate.getValue().format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                    endDate.getValue().format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                    rentalDays,
                    vehicle.dailyRate(),
                    totalCost));

            // Wait for user confirmation
            Optional<ButtonType> result = confirmDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try (Connection conn = DatabaseConfig.getConnection()) {
                    // Insert rental into the database
                    String insertRentalQuery = "INSERT INTO rentals (user_id, vehicle_id, rental_date, return_date, total_cost, status) "
                            +
                            "VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(insertRentalQuery)) {
                        stmt.setInt(1, Integer.parseInt(currentUser.getId())); // User ID
                        stmt.setInt(2, vehicle.getId()); // Vehicle ID
                        stmt.setTimestamp(3, Timestamp.valueOf(startDate.getValue().atStartOfDay())); // Rental Date
                        stmt.setTimestamp(4, Timestamp.valueOf(endDate.getValue().atStartOfDay())); // Return Date
                        stmt.setDouble(5, totalCost); // Total Cost
                        stmt.setString(6, "Pending"); // Status

                        int rowsInserted = stmt.executeUpdate();
                        if (rowsInserted > 0) {
                            // Rental was successful
                            AlertUtils.showAlert(Alert.AlertType.INFORMATION, "Rental Successful",
                                    "The vehicle has been successfully rented!");

                            // Optionally, update the vehicle status to "Rented"
                            String updateVehicleQuery = "UPDATE vehicles SET status = 'inProcess' WHERE id = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateVehicleQuery)) {
                                updateStmt.setInt(1, vehicle.getId());
                                updateStmt.executeUpdate();
                            }

                            // Refresh the view
                            AppNavigator.loadView(new UserController(currentUser).getView(new BorderPane()));
                        } else {
                            // Rental failed
                            AlertUtils.showAlert(Alert.AlertType.ERROR, "Rental Failed",
                                    "An error occurred while processing your rental. Please try again.");
                        }
                    }
                } catch (SQLException e) {
                    // Show error message to the user
                    AlertUtils.showAlert(Alert.AlertType.ERROR, "Database Error",
                            "An error occurred while accessing the database: " + e.getMessage());

                }
            }

        });

        VBox dateBox = new VBox(5);
        dateBox.getChildren().addAll(
                new Label("Rental Dates:"),
                new HBox(5, new Label("From:"), startDate),
                new HBox(5, new Label("To:   "), endDate));

        card.getChildren().addAll(
                imageView, modelLabel, detailsLabel, priceLabel,
                new Separator(), dateBox, rentBtn);

        return card;
    }

    private ScrollPane createRentalsView(User currentUser) {
        // Initialize containers if they don't exist
        if (rentalsContainer == null) {
            rentalsContainer = new VBox(20);
            rentalsContainer.setPadding(new Insets(20));
            rentalsContainer.setAlignment(Pos.TOP_CENTER);
        } else {
            rentalsContainer.getChildren().clear(); // Clear existing content
        }

        // Create/reuse ScrollPane
        if (rentalsScrollPane == null) {
            rentalsScrollPane = new ScrollPane(rentalsContainer);
            rentalsScrollPane.setFitToWidth(true);
        }

        // Populate content
        Label sectionTitle = new Label("My Rentals");
        sectionTitle.getStyleClass().add("section-title");

        // Get user's rentals from service
        List<Rentals> rentals = rentalService.getUserRentals(Integer.parseInt(currentUser.getId()));

        if (rentals.isEmpty()) {
            Label noRentals = new Label("You don't have any active rentals.");
            noRentals.setStyle("-fx-font-style: italic;");
            rentalsContainer.getChildren().addAll(sectionTitle, noRentals);
        } else {
            VBox rentalsList = new VBox(15);
            for (Rentals rental : rentals) {
                rentalsList.getChildren().add(createRentalItem(rental));
            }
            rentalsContainer.getChildren().addAll(sectionTitle, rentalsList);
        }

        return rentalsScrollPane;
    }

    private HBox createRentalItem(Rentals rental) {
        HBox rentalItem = new HBox(20);
        rentalItem.getStyleClass().add("rental-item");
        rentalItem.setPadding(new Insets(15));
        rentalItem.setAlignment(Pos.CENTER_LEFT);

        // Vehicle image
        ImageView imageView = new ImageView();
        try {
            imageView.setImage(new Image(rental.getImagePath()));
        } catch (Exception e) {
            imageView.setImage(new Image("/images/vehicle-placeholder.png"));
        }
        imageView.setFitWidth(120);
        imageView.setFitHeight(80);

        // Rental details
        VBox detailsBox = new VBox(5);
        Label modelLabel = new Label(rental.getVehicleName());
        modelLabel.setStyle("-fx-font-weight: bold;");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d");

        String startDate = rental.getRentalDate()
                .toLocalDateTime()
                .toLocalDate()
                .format(formatter);

        String endDate = rental.getReturnDate() != null
                ? rental.getReturnDate().toLocalDateTime().toLocalDate().format(formatter)
                : "N/A";

        Label datesLabel = new Label(String.format("%s - %s", startDate, endDate));

        // Add total cost information
        Label costLabel = new Label(String.format("Total Cost: $%.2f", rental.getTotalCost()));
        costLabel.setStyle("-fx-font-weight: bold;");

        // Status label with color coding
        Label statusLabel = new Label("Status: " + rental.getStatus());
        statusLabel.setStyle("-fx-font-weight: bold;");

        // Apply different colors based on status
        switch (rental.getStatus()) {
            case "Pending":
                statusLabel.setTextFill(Color.web("#3498db")); // Blue
                break;
            case "Approved":
                statusLabel.setTextFill(Color.web("#dcf70c")); // yellow
                break;
            case "Ongoing":
                statusLabel.setTextFill(Color.web("#dcf70c")); // yellow
                break;
            case "Completed":
                statusLabel.setTextFill(Color.web("#06d61b")); // green
                break;
            case "Cancelled":
                statusLabel.setTextFill(Color.web("#e74c3c")); // Red
                break;
            default:
                statusLabel.setTextFill(Color.web("#7f8c8d")); // Gray
        }

        detailsBox.getChildren().addAll(modelLabel, datesLabel, costLabel, statusLabel);

        // Rental actions
        HBox actionBox = new HBox(10);
        actionBox.setAlignment(Pos.CENTER_RIGHT);

        // Only show buttons for Pending or Approved rentals
        if (rental.getStatus().equals("Pending") || rental.getStatus().equals("Approved")) {
            // Cancel button for Pending/Approved rentals
            Button cancelBtn = new Button("Cancel");
            cancelBtn.getStyleClass().add("action-button");
            cancelBtn.setStyle("-fx-background-color: #e74c3c;");

            // Add cancel functionality
            cancelBtn.setOnAction(e -> {
                // Convert rental dates to LocalDate
                LocalDate rentalStartDate = rental.getRentalDate().toLocalDateTime().toLocalDate();
                LocalDate rentalEndDate = rental.getReturnDate().toLocalDateTime().toLocalDate();

                // Calculate rental duration in days
                long rentalDays = ChronoUnit.DAYS.between(rentalStartDate, rentalEndDate);

                // Show confirmation dialog
                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDialog.setTitle("Cancel Rental");
                confirmDialog.setHeaderText("Cancel Rental for " + rental.getVehicleName());

                confirmDialog.setContentText(String.format(
                        "Please confirm the following rental details:\n\n" +
                                "Vehicle: %s\n" +
                                "Rental Period: %s to %s\n" +
                                "Duration: %d days\n" +
                // "Daily Rate: $%.2f\n" +
                                "Total Cost: $%.2f",
                        rental.getVehicleName(),
                        rentalStartDate.format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                        rentalEndDate.format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                        rentalDays,
                        rental.getTotalCost()));

                // confirmDialog.showAndWait();

                // Wait for user confirmation
                Optional<ButtonType> result = confirmDialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {

                    // Update vehicle status to Available
                    rental.setStatus("Cancelled");

                    Vehicle vehicle = rental.getVehicle(); // Assuming `getVehicle()` returns the associated vehicle
                    vehicle.setStatus("Available"); // boolean updated = RentalsDAO.updateRentals(rental);

                    // Update the database
                    boolean rentalUpdated = RentalsDAO.updateRentalsStatus(rental);
                    boolean vehicleUpdated = VehicleDAO.updateVehicleStatus(vehicle);

                    if (rentalUpdated && vehicleUpdated) {
                        // Show confirmation and refresh the view
                        AlertUtils.showAlert(Alert.AlertType.INFORMATION, "Rental Cancelled",
                                "Your rental has been successfully cancelled.");

                        // refresh
                        refreshRentalsView();
                    } else {
                        AlertUtils.showAlert(Alert.AlertType.ERROR, "Error",
                                "Could not cancel the rental. Please try again later.");
                    }
                }

            });

            actionBox.getChildren().add(cancelBtn);

            // Only show extend button for Approved rentals
            if (rental.getStatus().equals("Approved")) {
                Button extendBtn = new Button("Extend");
                extendBtn.getStyleClass().add("action-button");
                extendBtn.setStyle("-fx-background-color: #f39c12;");

                // Add extend functionality
                extendBtn.setOnAction(_ -> {
                    showExtendRentalDialog(rental);
                });

                actionBox.getChildren().add(0, extendBtn);
            }
        } else {
            // For Completed/Cancelled/Ongoing rentals, show a details button instead
            if (rental.getStatus().equals("Ongoing")) {
                Button detailsBtn = new Button("Details");
                detailsBtn.getStyleClass().add("action-button");
                detailsBtn.setStyle("-fx-background-color: #3498db;");

                detailsBtn.setOnAction(e -> {
                    showRentalDetailsDialog(rental);
                });

                actionBox.getChildren().add(detailsBtn);
            }
        }

        // Layout
        HBox.setHgrow(detailsBox, Priority.ALWAYS);
        rentalItem.getChildren().addAll(imageView, detailsBox, actionBox);

        return rentalItem;
    }

    // // Helper method to show alerts
    // private void showAlert(Alert.AlertType alertType, String title, String
    // content) {
    // Alert alert = new Alert(alertType);
    // alert.setTitle(title);
    // alert.setHeaderText(null);
    // alert.setContentText(content);
    // alert.showAndWait();
    // }

    // Helper method to refresh the rentals view
    private void refreshRentalsView() {
        createRentalsView(currentUser); // This will update the existing rentalsContainer
    }

    // Dialog to extend a rental
    private void showExtendRentalDialog(Rentals rental) {
        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Extend Rental");
        dialog.setHeaderText("Select a new return date");

        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        DatePicker datePicker = new DatePicker();
        // Convert Timestamp to LocalDate
        LocalDate returnDate = rental.getReturnDate() != null
                ? rental.getReturnDate().toLocalDateTime().toLocalDate()
                : null;

        // Set the value of the DatePicker
        datePicker.setValue(returnDate);

        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                // Disable invalid dates
                setDisable(empty || (returnDate != null && date.compareTo(returnDate) <= 0) ||
                        date.compareTo(LocalDate.now().plusMonths(3)) > 0);
            }
        });

        VBox content = new VBox(10);
        content.getChildren().addAll(
                new Label("Current return date: " + rental.getReturnDate().toString()),
                new Label("Select new return date:"),
                datePicker);
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return datePicker.getValue();
            }
            return null;
        });

        Optional<LocalDate> result = dialog.showAndWait();
        result.ifPresent(newDate -> {
            boolean success = rentalService.extendRental(rental.getId(), newDate);
            if (success) {
                AlertUtils.showAlert(Alert.AlertType.INFORMATION, "Rental Extended",
                        "Your rental has been successfully extended to " + newDate);
                refreshRentalsView();
            } else {
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Error",
                        "Could not extend the rental. Please try again later.");
            }
        });
    }

    // Dialog to show rental details
    private void showRentalDetailsDialog(Rentals rental) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Rental Details");
        dialog.setHeaderText("Details for " + rental.getVehicleName());

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Add all rental details
        int row = 0;
        grid.add(new Label("Vehicle:"), 0, row);
        grid.add(new Label(rental.getVehicleName()), 1, row++);

        grid.add(new Label("Rental Date:"), 0, row);
        grid.add(new Label(rental.getRentalDate().toString()), 1, row++);

        grid.add(new Label("Return Date:"), 0, row);
        grid.add(new Label(rental.getReturnDate() != null ? rental.getReturnDate().toString() : "N/A"), 1, row++);

        grid.add(new Label("Status:"), 0, row);
        Label statusValueLabel = new Label(rental.getStatus());
        statusValueLabel.setStyle("-fx-font-weight: bold;");
        switch (rental.getStatus()) {
            case "Ongoing":
                statusValueLabel.setTextFill(Color.web("#27ae60"));
                break;
            case "Completed":
                statusValueLabel.setTextFill(Color.web("#f39c12"));
                break;
            default:
                statusValueLabel.setTextFill(Color.web("#7f8c8d"));
        }
        grid.add(statusValueLabel, 1, row++);

        grid.add(new Label("Total Cost:"), 0, row);
        grid.add(new Label(String.format("$%.2f", rental.getTotalCost())), 1, row++);

        if (rental.getStatus().equals("Ongoing")) {
            grid.add(new Label("Days Remaining:"), 0, row);

            // Convert Timestamp to LocalDate
            LocalDate returnDate = rental.getReturnDate().toLocalDateTime().toLocalDate();

            // Calculate days remaining
            long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), returnDate);

            grid.add(new Label(String.valueOf(daysRemaining)), 1, row++);
        }

        dialog.getDialogPane().setContent(grid);
        dialog.showAndWait();
    }

    private VBox createProfileView() {
        VBox profileContainer = new VBox(20);
        profileContainer.getStyleClass().add("profile-section");
        profileContainer.setPadding(new Insets(30));
        profileContainer.setAlignment(Pos.TOP_CENTER);

        Label sectionTitle = new Label("My Profile");
        sectionTitle.getStyleClass().add("section-title");

        // Profile picture
        ImageView profilePic = new ImageView();
        profilePic.setFitWidth(100);
        profilePic.setFitHeight(100);
        profilePic.setStyle("-fx-background-radius: 50; -fx-border-radius: 50;");
        profilePic.setImage(new Image("/images/profile_img.png"));

        // Editable user information
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(15);
        infoGrid.setVgap(10);
        infoGrid.setAlignment(Pos.CENTER);

        // Username
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-weight: bold;");
        TextField usernameField = new TextField(currentUser.getUsername());
        usernameField.setPromptText("Enter your username");

        // Email
        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-font-weight: bold;");
        TextField emailField = new TextField(currentUser.getEmail());
        emailField.setPromptText("Enter your email");

        // Phone
        Label phoneLabel = new Label("Phone:");
        phoneLabel.setStyle("-fx-font-weight: bold;");
        TextField phoneField = new TextField(currentUser.getPhone());
        phoneField.setPromptText("Enter your phone number");

        // Address
        Label addressLabel = new Label("Address:");
        addressLabel.setStyle("-fx-font-weight: bold;");
        TextField addressField = new TextField(currentUser.getAddress());
        addressField.setPromptText("Enter your address");

        // Password
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-weight: bold;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.getStyleClass().add("form-field");

        infoGrid.add(usernameLabel, 0, 0);
        infoGrid.add(usernameField, 1, 0);
        infoGrid.add(emailLabel, 0, 1);
        infoGrid.add(emailField, 1, 1);
        infoGrid.add(phoneLabel, 0, 2);
        infoGrid.add(phoneField, 1, 2);
        infoGrid.add(addressLabel, 0, 3);
        infoGrid.add(addressField, 1, 3);
        infoGrid.add(passwordLabel, 0, 4);
        infoGrid.add(passwordField, 1, 4);

        // Save and Cancel Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button saveButton = new Button("Save Changes");
        saveButton.getStyleClass().add("action-button");
        saveButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("action-button");
        cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

        buttonBox.getChildren().addAll(saveButton, cancelButton);

        // Save Button Action
        saveButton.setOnAction(event -> {
            String newUsername = usernameField.getText();
            String newEmail = emailField.getText();
            String newPhone = phoneField.getText();
            String newAddress = addressField.getText();
            String unhashPassword = passwordField.getText();
            String hashedPassword = currentUser.getHashedPassword();

            Boolean success = UserService.updateUser(
                    currentUser.getId(),
                    newUsername,
                    unhashPassword,
                    hashedPassword,
                    newPhone,
                    newEmail,
                    newAddress);

            if (success) {
                // UserDAO.updateUserProfile(currentUser.getId(), newUsername, newEmail,
                // newPhone, newAddress);
                // AlertUtils
                AlertUtils.showAlert(Alert.AlertType.INFORMATION, " Success: ",
                        "Profile updated successfully!");
                currentUser.setUsername(newUsername);
                currentUser.setEmail(newEmail);
                currentUser.setPhone(newPhone);
                currentUser.setAddress(newAddress);
            } else {
                // AlertUtils error
                AlertUtils.showAlert(Alert.AlertType.ERROR, "Error", "Failed to update profile. Please try again.");
            }
        });

        // Cancel Button Action
        cancelButton.setOnAction(event -> {
            usernameField.setText(currentUser.getUsername());
            emailField.setText(currentUser.getEmail());
            phoneField.setText(currentUser.getPhone());
            addressField.setText(currentUser.getAddress());
        });

        profileContainer.getChildren().addAll(sectionTitle, profilePic, infoGrid, buttonBox);
        return profileContainer;
    }

}