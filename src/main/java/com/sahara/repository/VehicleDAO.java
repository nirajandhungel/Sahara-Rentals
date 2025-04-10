package com.sahara.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sahara.config.DatabaseConfig;
import com.sahara.model.Bike;
import com.sahara.model.Car;
import com.sahara.model.ElectricVehicle;
import com.sahara.model.Vehicle;

public class VehicleDAO {

    // Fetch all available vehicles from the database
    public static List<Vehicle> getAvailableVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE status = 'Available'";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
                Vehicle vehicle;

                // Instantiate the appropriate subclass based on the type
                switch (type) {
                    case "Car":
                        vehicle = new Car(
                                rs.getInt("id"),
                                rs.getString("type"),
                                rs.getString("brand_model"),
                                rs.getInt("model_year"),
                                rs.getDouble("price_per_day"),
                                rs.getString("number"),
                                rs.getString("image_path"),
                                rs.getString("status"),
                                rs.getString("details")
                        );
                        break;
                    case "Bike":
                        vehicle = new Bike(
                                rs.getInt("id"),
                                rs.getString("type"),
                                rs.getString("brand_model"),
                                rs.getInt("model_year"),
                                rs.getDouble("price_per_day"),
                                rs.getString("number"),
                                rs.getString("image_path"),
                                rs.getString("status"),
                                rs.getString("details")
                        );
                        break;
                    case "ElectricVehicle":
                        vehicle = new ElectricVehicle(
                                rs.getInt("id"),
                                rs.getString("type"),
                                rs.getString("brand_model"),
                                rs.getInt("model_year"),
                                rs.getDouble("price_per_day"),
                                rs.getString("number"),
                                rs.getString("image_path"),
                                rs.getString("status"),
                                rs.getString("details")
                        );
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown vehicle type: " + type);
                }

                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching available vehicles: " + e.getMessage());
        }

        return vehicles;
    }

    // Fetch all available vehicles from the database
    public static List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
                Vehicle vehicle;

                // Instantiate the appropriate subclass based on the type
                switch (type) {
                    case "Car":
                        vehicle = new Car(
                                rs.getInt("id"),
                                rs.getString("type"),
                                rs.getString("brand_model"),
                                rs.getInt("model_year"),
                                rs.getDouble("price_per_day"),
                                rs.getString("number"),
                                rs.getString("image_path"),
                                rs.getString("status"),
                                rs.getString("details")
                        );
                        break;
                    case "Bike":
                        vehicle = new Bike(
                                rs.getInt("id"),
                                rs.getString("type"),
                                rs.getString("brand_model"),
                                rs.getInt("model_year"),
                                rs.getDouble("price_per_day"),
                                rs.getString("number"),
                                rs.getString("image_path"),
                                rs.getString("status"),
                                rs.getString("details")
                        );
                        break;
                    case "ElectricVehicle":
                        vehicle = new ElectricVehicle(
                                rs.getInt("id"),
                                rs.getString("type"),
                                rs.getString("brand_model"),
                                rs.getInt("model_year"),
                                rs.getDouble("price_per_day"),
                                rs.getString("number"),
                                rs.getString("image_path"),
                                rs.getString("status"),
                                rs.getString("details")
                        );
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown vehicle type: " + type);
                }

                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching available vehicles: " + e.getMessage());
        }

        return vehicles;
    }



    public static Vehicle getVehicleById(int vehicleId) {
        String query = "SELECT * FROM vehicles WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, vehicleId);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                // Determine the type of vehicle and return the appropriate subclass
                String type = rs.getString("type");
                switch (type) {
                    case "Car":
                        return new Car(
                            rs.getInt("id"),
                            rs.getString("type"),
                            rs.getString("brand_model"),
                            rs.getInt("model_year"),
                            rs.getDouble("price_per_day"),
                            rs.getString("number"),
                            rs.getString("image_path"),
                            rs.getString("status"),
                            rs.getString("details")
                        );
                    case "Bike":
                        return new Bike(
                            rs.getInt("id"),
                            rs.getString("type"),
                            rs.getString("brand_model"),
                            rs.getInt("model_year"),
                            rs.getDouble("price_per_day"),
                            rs.getString("number"),
                            rs.getString("image_path"),
                            rs.getString("status"),
                            rs.getString("details")
                        );
                    case "ElectricVehicle":
                        return new ElectricVehicle(
                            rs.getInt("id"),
                            rs.getString("type"),
                            rs.getString("brand_model"),
                            rs.getInt("model_year"),
                            rs.getDouble("price_per_day"),
                            rs.getString("number"),
                            rs.getString("image_path"),
                            rs.getString("status"),
                            rs.getString("details")
                        );
                    default:
                        throw new IllegalArgumentException("Unknown vehicle type: " + type);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching vehicle by ID: " + e.getMessage());
        }
        return null;
    }



    public static boolean updateVehicleStatus(Vehicle vehicle) {
        String query = "UPDATE vehicles SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            System.out.println("Updating vehicle with ID: " + vehicle.getId() + ", Status: " + vehicle.getStatus());
    
            stmt.setString(1, vehicle.getStatus());
            stmt.setInt(2, vehicle.getId());
    
            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
    
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating vehicle: " + e.getMessage());
            return false;
        }
    }

    public boolean updateVehicle(Vehicle vehicle) {
        String query = "UPDATE vehicles SET type = ?, brand_model = ?, model_year =?, price_per_day=?, number=?, image_path=?, status=?, details=? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, vehicle.getType());
            stmt.setString(2, vehicle.getBrand());
            stmt.setInt(3, vehicle.getYear());
            stmt.setDouble(4, vehicle.getPrice());
            stmt.setString(5, vehicle.getNumber());
            stmt.setString(6, vehicle.getImagePath());
            stmt.setString(7, vehicle.getStatus());
            stmt.setString(8, vehicle.getDetails());
            stmt.setInt(9, vehicle.getId());
    
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating vehicle: " + e.getMessage());
            return false;
        }
    }


    public static boolean addVehicle(Vehicle vehicle) {
        String query = "INSERT INTO vehicles (type, brand_model, model_year, price_per_day, number, image_path, status, details) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, vehicle.getType());
            stmt.setString(2, vehicle.getBrand());
            stmt.setInt(3, vehicle.getYear());
            stmt.setDouble(4, vehicle.getPrice());
            stmt.setString(5, vehicle.getNumber());
            stmt.setString(6, vehicle.getImagePath());
            stmt.setString(7, vehicle.getStatus());
            stmt.setString(8, vehicle.getDetails());
    
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean removeVehicle(int vehicleId) {
        String query = "DELETE FROM vehicles WHERE id = ?";
    
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, vehicleId);
    
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Error removing vehicle: " + e.getMessage());
            return false;
        }
    }


    
}