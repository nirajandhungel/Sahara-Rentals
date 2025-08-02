# Vehicle Rental System

This is a JavaFX-based application for managing vehicle rentals. It includes features for user authentication, vehicle browsing, rentals, and administrative tasks.

---

## **Project Setup**

### **Prerequisites**
1. **Java Development Kit (JDK):**
   - Ensure you have JDK 17 or higher installed.
   - Set the `JAVA_HOME` environment variable to point to your JDK installation.

2. **Apache Maven:**
   - Install Maven for dependency management and project building.
   - Verify Maven installation by running `mvn -v` in the terminal.

3. **MySQL Database:**
   - Install MySQL and create a database named `vehiclerentalsystem`.
   - Update the database credentials in `DatabaseConfig.java`:
     ```java
     private static final String DB_URL = "jdbc:mysql://localhost:3306/";
     private static final String DB_NAME = "vehiclerentalsystem";
     private static final String USER = "root"; // Replace with your MySQL username
     private static final String PASS = "your_password"; // Replace with your MySQL password
     ```

4. **JavaFX SDK:**
   - Download and configure the JavaFX SDK.
   - Add the JavaFX SDK to your `PATH` or configure it in your IDE.

---

## **How to Run**

### **Using an IDE (e.g., IntelliJ, Eclipse, VS Code):**
1. Clone the repository:
   ```bash
   git clone https://github.com/nirajandhungel/vehicle_rental_system.git
   cd vehicle_rental_system



Features
1. User Authentication
Login functionality for different roles (admin, vmt, customer).
Passwords are securely hashed using BCrypt.
2. Vehicle Management
Browse available vehicles.
Rent vehicles (for customers).
Add new vehicles (for VMT users).
3. Profile Management
Update user profile information.
4. Admin Features
Manage users and vehicles.

















1. Press  : Ctrl + ,

2. Search : CSS > Lint: Vendor Prefix

3. Select : ignore
