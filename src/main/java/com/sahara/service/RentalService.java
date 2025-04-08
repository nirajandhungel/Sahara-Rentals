package com.sahara.service;

import java.time.LocalDate;
import java.util.List;

import com.sahara.model.Rentals;
import com.sahara.model.Vehicle;
import com.sahara.repository.RentalsDAO;
import com.sahara.repository.VehicleDAO;

public class RentalService {

    // Fetch available vehicles
    public List<Vehicle> getAvailableVehicles() {
        return VehicleDAO.getAvailableVehicles();
    }

    // Fetch rentals for a specific user
    public List<Rentals> getUserRentals(int userId) {
        return RentalsDAO.getRentalsByUserId(userId);
    }

    // Extend the rental return date
    public boolean extendRental(int rentalId, LocalDate newReturnDate) {
        // Validate the new return date
        if (newReturnDate == null || newReturnDate.isBefore(LocalDate.now())) {
            System.err.println("Invalid return date. It must be today or later.");
            return false;
        }

        // Fetch the current rental details
        Rentals rental = RentalsDAO.getRentalById(rentalId);
        if (rental == null) {
            System.err.println("Rental not found with ID: " + rentalId);
            return false;
        }

        // Ensure the new return date is after the current return date
        LocalDate currentReturnDate = rental.getReturnDate().toLocalDateTime().toLocalDate();
        if (newReturnDate.isBefore(currentReturnDate)) {
            System.err.println("New return date must be after the current return date.");
            return false;
        }

        // Update the return date in the database
        rental.setReturnDate(java.sql.Timestamp.valueOf(newReturnDate.atStartOfDay()));
        return RentalsDAO.updateRentalReturnDate(rental);
    }

    // public static boolean cancelRental(Rentals rental) {
    //     // Create a Rentals object to represent the rental with the ID and "Cancelled" status
    //     // Rentals rental = new Rentals();
    //     boolean updated = RentalsDAO.updateRentals(rental);
    //     if (updated) {
    //         return true;
    //     } else {
    //         return false;
    //     }
    // }
    
    
}