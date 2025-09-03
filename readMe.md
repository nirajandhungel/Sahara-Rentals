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
   git clone git clone https://github.com/nirajandhungel/Sahara-Rentals.git
   cd Sahara-Rentals



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


## 💻 User Interfaces

### 🧑‍💻 Main UI Screens


<img width="1431" height="801" src="https://github.com/user-attachments/assets/ee8fd578-b90f-4e83-8041-70ed9da2de65" />
<img width="1314" height="688" src="https://github.com/user-attachments/assets/58115d80-bf14-4917-926b-747aa5f682f6" />


---

### 🧍 Customer Dashboard

<img width="1380" height="707" src="https://github.com/user-attachments/assets/9b627683-4383-4a2d-8a7c-f87575ea13e6" />
<img width="1361" height="995" src="https://github.com/user-attachments/assets/57c51942-012f-4c9e-b9b0-c5c660f71bac" />
<img width="1369" height="1018" src="https://github.com/user-attachments/assets/10710d89-f71e-4398-a318-d53810d06751" />

---

### 🧑‍💼 Admin Dashboard


<img width="1380" height="707" src="https://github.com/user-attachments/assets/493b9554-0fd2-40c7-a714-a41fb52da36e" />
<img width="1361" height="995" src="https://github.com/user-attachments/assets/d985c25c-0912-4740-898d-f85ffb8a551e" />
<img width="1369" height="1018" src="https://github.com/user-attachments/assets/3badd0f2-3aed-42ab-a2fa-b9c53aa73db8" />
<img width="1369" height="1018" src="https://github.com/user-attachments/assets/db5db560-b99b-4735-b6b0-3d7b0b62884f" />

---

### 🧑‍💼 Vehicle Management Team

<img width="1380" height="707" src="https://github.com/user-attachments/assets/c002a8e9-220c-46c3-8d4d-1944404aa0e0" />
<img width="1361" height="995" src="https://github.com/user-attachments/assets/24524bf0-c5dd-48b6-9b78-796572410ae1" />
