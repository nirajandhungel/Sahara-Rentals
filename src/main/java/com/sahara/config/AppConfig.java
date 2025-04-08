package com.sahara.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sahara.service.Authenticator;

public class AppConfig extends DatabaseConfig {

    public static void configuration() {
        // Setup database
        DatabaseSetup.createDatabase();
        DatabaseSetup.createTables();

        Connection connection = null;
        PreparedStatement userStmt = null;
        PreparedStatement vehicleStmt = null;

        try {
            connection = DatabaseConfig.getConnection();

            /** -------------------- Insert Users -------------------- **/
            String userSql = "INSERT INTO users (username, email, password, role, phone, address) VALUES "
                    + "(?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?)";

            userStmt = connection.prepareStatement(userSql);

            // Admin users
            userStmt.setString(1, "admin1");
            userStmt.setString(2, "admin1@example.com");
            String admin1Password = Authenticator.hashPassword("admin1");
            userStmt.setString(3, admin1Password);
            userStmt.setString(4, "admin");
            userStmt.setString(5, "1234567890");
            userStmt.setString(6, "Address 1");

            userStmt.setString(7, "admin2");
            userStmt.setString(8, "admin2@example.com");
            String admin2Password = Authenticator.hashPassword("admin2");
            userStmt.setString(9, admin2Password);
            userStmt.setString(10, "admin");
            userStmt.setString(11, "0987654321");
            userStmt.setString(12, "Address 2");

            // VMT users
            userStmt.setString(13, "vmt1");
            userStmt.setString(14, "vmt1@example.com");
            String vmt1Password = Authenticator.hashPassword("vmt1");
            userStmt.setString(15, vmt1Password);
            userStmt.setString(16, "vmt");
            userStmt.setString(17, "1122334455");
            userStmt.setString(18, "Address 3");

            userStmt.setString(19, "vmt2");
            userStmt.setString(20, "vmt2@example.com");
            String vmt2Password = Authenticator.hashPassword("vmt2");
            userStmt.setString(21, vmt2Password);
            userStmt.setString(22, "vmt");
            userStmt.setString(23, "2233445566");
            userStmt.setString(24, "Address 4");

            userStmt.setString(25, "vmt3");
            userStmt.setString(26, "vmt3@example.com");
            String vmt3Password = Authenticator.hashPassword("vmt3");
            userStmt.setString(27, vmt3Password);
            userStmt.setString(28, "vmt");
            userStmt.setString(29, "3344556677");
            userStmt.setString(30, "Address 5");

            int userRows = userStmt.executeUpdate();
            System.out.println(userRows + " user rows inserted.");

            /** -------------------- Insert Vehicles -------------------- **/
            String vehicleSql = "INSERT INTO vehicles (type, brand_model, model_year, price_per_day, number, image_path, status, details) VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?), "
                    + "(?, ?, ?, ?, ?, ?, ?, ?)";

            vehicleStmt = connection.prepareStatement(vehicleSql);

            // Vehicle 1 (Bike)
            vehicleStmt.setString(1, "Bike");
            vehicleStmt.setString(2, "Yamaha FZ-S FI Ver 4.0 DLX");
            vehicleStmt.setInt(3, 2022);
            vehicleStmt.setBigDecimal(4, new java.math.BigDecimal("20.00"));
            vehicleStmt.setString(5, "XYZ001");
            vehicleStmt.setString(6, "/images/vermillion-re.png");
            vehicleStmt.setString(7, "Available");
            vehicleStmt.setString(8, "Sport bike");

            // Vehicle 2 (Bike)
            vehicleStmt.setString(9, "Bike");
            vehicleStmt.setString(10, "Kawasaki Ninja");
            vehicleStmt.setInt(11, 2021);
            vehicleStmt.setBigDecimal(12, new java.math.BigDecimal("25.00"));
            vehicleStmt.setString(13, "XYZ002");
            vehicleStmt.setString(14, "/images/kawasaki-ninja400.png");
            vehicleStmt.setString(15, "Available");
            vehicleStmt.setString(16, "Sport bike");

            // Vehicle 3 (Bike)
            vehicleStmt.setString(17, "Bike");
            vehicleStmt.setString(18, "Honda CBR 500R");
            vehicleStmt.setInt(19, 2023);
            vehicleStmt.setBigDecimal(20, new java.math.BigDecimal("30.00"));
            vehicleStmt.setString(21, "XYZ003");
            vehicleStmt.setString(22, "/images/honda-cbr500.jpg");
            vehicleStmt.setString(23, "Available");
            vehicleStmt.setString(24, "Sport bike");

            // Vehicle 4 (Bike)
            vehicleStmt.setString(25, "Bike");
            vehicleStmt.setString(26, "Ducati Monster");
            vehicleStmt.setInt(27, 2022);
            vehicleStmt.setBigDecimal(28, new java.math.BigDecimal("35.00"));
            vehicleStmt.setString(29, "XYZ004");
            vehicleStmt.setString(30, "/images/ducati-monster-re.png");
            vehicleStmt.setString(31, "Available");
            vehicleStmt.setString(32, "Naked bike");

            // Vehicle 5 (Bike)
            vehicleStmt.setString(33, "Bike");
            vehicleStmt.setString(34, "Suzuki GSX-R750");
            vehicleStmt.setInt(35, 2020);
            vehicleStmt.setBigDecimal(36, new java.math.BigDecimal("28.00"));
            vehicleStmt.setString(37, "XYZ005");
            vehicleStmt.setString(38, "/images/suzuki-gsx.jpg");
            vehicleStmt.setString(39, "Available");
            vehicleStmt.setString(40, "Sport bike");

            // Vehicle 6 (Bike)
            vehicleStmt.setString(41, "Bike");
            vehicleStmt.setString(42, "Royal Enfield Classic 350");
            vehicleStmt.setInt(43, 2021);
            vehicleStmt.setBigDecimal(44, new java.math.BigDecimal("22.00"));
            vehicleStmt.setString(45, "XYZ006");
            vehicleStmt.setString(46, "/images/enfield-classic.png");
            vehicleStmt.setString(47, "Available");
            vehicleStmt.setString(48, "Cruiser");

            // Vehicle 7 (Bike)
            vehicleStmt.setString(49, "Bike");
            vehicleStmt.setString(50, "Harley Davidson Iron 883");
            vehicleStmt.setInt(51, 2022);
            vehicleStmt.setBigDecimal(52, new java.math.BigDecimal("40.00"));
            vehicleStmt.setString(53, "XYZ007");
            vehicleStmt.setString(54, "/images/harley-iron.png");
            vehicleStmt.setString(55, "Available");
            vehicleStmt.setString(56, "Cruiser");

            // Vehicle 8 (Bike)
            vehicleStmt.setString(57, "Bike");
            vehicleStmt.setString(58, "BMW S1000RR");
            vehicleStmt.setInt(59, 2023);
            vehicleStmt.setBigDecimal(60, new java.math.BigDecimal("50.00"));
            vehicleStmt.setString(61, "XYZ008");
            vehicleStmt.setString(62, "/images/bmw-s1000rr.jpeg");
            vehicleStmt.setString(63, "Available");
            vehicleStmt.setString(64, "Sport bike");

            // Vehicle 9 (Car)
            vehicleStmt.setString(65, "Car");
            vehicleStmt.setString(66, "Toyota Corolla");
            vehicleStmt.setInt(67, 2020);
            vehicleStmt.setBigDecimal(68, new java.math.BigDecimal("50.00"));
            vehicleStmt.setString(69, "CAR001");
            vehicleStmt.setString(70, "/images/toyota-corolla.jpeg");
            vehicleStmt.setString(71, "Available");
            vehicleStmt.setString(72, "Sedan");

            // Vehicle 10 (Car)J
            vehicleStmt.setString(73, "Car");
            vehicleStmt.setString(74, "Honda Civic");
            vehicleStmt.setInt(75, 2021);
            vehicleStmt.setBigDecimal(76, new java.math.BigDecimal("55.00"));
            vehicleStmt.setString(77, "CAR002");
            vehicleStmt.setString(78, "/images/honda-civic.jpeg");
            vehicleStmt.setString(79, "Available");
            vehicleStmt.setString(80, "Sedan");

            // Vehicle 11 (Car)
            vehicleStmt.setString(81, "Car");
            vehicleStmt.setString(82, "Ford Mustang");
            vehicleStmt.setInt(83, 2022);
            vehicleStmt.setBigDecimal(84, new java.math.BigDecimal("80.00"));
            vehicleStmt.setString(85, "CAR003");
            vehicleStmt.setString(86, "/images/ford-mustang.jpeg");
            vehicleStmt.setString(87, "Available");
            vehicleStmt.setString(88, "Sports car");

            // Vehicle 12 (Car)
            vehicleStmt.setString(89, "Car");
            vehicleStmt.setString(90, "Chevrolet Tahoe");
            vehicleStmt.setInt(91, 2021);
            vehicleStmt.setBigDecimal(92, new java.math.BigDecimal("70.00"));
            vehicleStmt.setString(93, "CAR004");
            vehicleStmt.setString(94, "/images/chevy-tahoe.jpeg");
            vehicleStmt.setString(95, "Available");
            vehicleStmt.setString(96, "SUV");

            // Vehicle 13 (Car)
            vehicleStmt.setString(97, "Car");
            vehicleStmt.setString(98, "BMW 5 Series");
            vehicleStmt.setInt(99, 2023);
            vehicleStmt.setBigDecimal(100, new java.math.BigDecimal("90.00"));
            vehicleStmt.setString(101, "CAR005");
            vehicleStmt.setString(102, "/images/bmw5series.png");
            vehicleStmt.setString(103, "Available");
            vehicleStmt.setString(104, "Luxury Sedan");

            // Vehicle 14 (Car)
            vehicleStmt.setString(105, "Car");
            vehicleStmt.setString(106, "Mercedes-Benz C-Class");
            vehicleStmt.setInt(107, 2022);
            vehicleStmt.setBigDecimal(108, new java.math.BigDecimal("85.00"));
            vehicleStmt.setString(109, "CAR006");
            vehicleStmt.setString(110, "/images/mercedes-benz.jpg");
            vehicleStmt.setString(111, "Available");
            vehicleStmt.setString(112, "Luxury Sedan");

            // Vehicle 15 (Car)
            vehicleStmt.setString(113, "Car");
            vehicleStmt.setString(114, "Audi Q5");
            vehicleStmt.setInt(115, 2021);
            vehicleStmt.setBigDecimal(116, new java.math.BigDecimal("75.00"));
            vehicleStmt.setString(117, "CAR007");
            vehicleStmt.setString(118, "/images/audi-qf.jpg");
            vehicleStmt.setString(119, "Available");
            vehicleStmt.setString(120, "Luxury SUV");

            // Vehicle 16 (Car)
            vehicleStmt.setString(121, "Car");
            vehicleStmt.setString(122, "Jeep Wrangler");
            vehicleStmt.setInt(123, 2023);
            vehicleStmt.setBigDecimal(124, new java.math.BigDecimal("65.00"));
            vehicleStmt.setString(125, "CAR008");
            vehicleStmt.setString(126, "/images/jeep-wrangler.png");
            vehicleStmt.setString(127, "Available");
            vehicleStmt.setString(128, "Off-road SUV");

            // Vehicle 17 (Electric Vehicle)
            vehicleStmt.setString(129, "ElectricVehicle");
            vehicleStmt.setString(130, "Tesla Model 3");
            vehicleStmt.setInt(131, 2023);
            vehicleStmt.setBigDecimal(132, new java.math.BigDecimal("100.00"));
            vehicleStmt.setString(133, "EV001");
            vehicleStmt.setString(134, "/images/tesla-model3.png");
            vehicleStmt.setString(135, "Available");
            vehicleStmt.setString(136, "Electric Sedan");

            // Vehicle 18 (Electric Vehicle)
            vehicleStmt.setString(137, "ElectricVehicle");
            vehicleStmt.setString(138, "Tesla Model Y");
            vehicleStmt.setInt(139, 2023);
            vehicleStmt.setBigDecimal(140, new java.math.BigDecimal("110.00"));
            vehicleStmt.setString(141, "EV002");
            vehicleStmt.setString(142, "/images/tesla-modely.jpg");
            vehicleStmt.setString(143, "Available");
            vehicleStmt.setString(144, "Electric SUV");

            // Vehicle 19 (Electric Vehicle)
            vehicleStmt.setString(145, "ElectricVehicle");
            vehicleStmt.setString(146, "Nissan Leaf");
            vehicleStmt.setInt(147, 2022);
            vehicleStmt.setBigDecimal(148, new java.math.BigDecimal("75.00"));
            vehicleStmt.setString(149, "EV003");
            vehicleStmt.setString(150, "/images/nissan-leaf.png");
            vehicleStmt.setString(151, "Available");
            vehicleStmt.setString(152, "Electric Hatchback");

            // Vehicle 20 (Electric Vehicle)
            vehicleStmt.setString(153, "ElectricVehicle");
            vehicleStmt.setString(154, "Chevrolet Bolt EV");
            vehicleStmt.setInt(155, 2023);
            vehicleStmt.setBigDecimal(156, new java.math.BigDecimal("80.00"));
            vehicleStmt.setString(157, "EV004");
            vehicleStmt.setString(158, "/images/cheverlot-bolt.jpg");
            vehicleStmt.setString(159, "Available");
            vehicleStmt.setString(160, "Electric Hatchback");

            // Vehicle 21 (Electric Vehicle)
            vehicleStmt.setString(161, "ElectricVehicle");
            vehicleStmt.setString(162, "Ford Mustang Mach-E");
            vehicleStmt.setInt(163, 2023);
            vehicleStmt.setBigDecimal(164, new java.math.BigDecimal("95.00"));
            vehicleStmt.setString(165, "EV005");
            vehicleStmt.setString(166, "/images/ford-mustang-matche.jpeg");
            vehicleStmt.setString(167, "Available");
            vehicleStmt.setString(168, "Electric SUV");

            // Vehicle 22 (Electric Vehicle)
            vehicleStmt.setString(169, "ElectricVehicle");
            vehicleStmt.setString(170, "Hyundai Kona Electric");
            vehicleStmt.setInt(171, 2022);
            vehicleStmt.setBigDecimal(172, new java.math.BigDecimal("85.00"));
            vehicleStmt.setString(173, "EV006");
            vehicleStmt.setString(174, "/images/hyundai-kona.jpeg");
            vehicleStmt.setString(175, "Available");
            vehicleStmt.setString(176, "Electric SUV");

            // Vehicle 23 (Electric Vehicle)
            vehicleStmt.setString(177, "ElectricVehicle");
            vehicleStmt.setString(178, "Kia EV6");
            vehicleStmt.setInt(179, 2023);
            vehicleStmt.setBigDecimal(180, new java.math.BigDecimal("90.00"));
            vehicleStmt.setString(181, "EV007");
            vehicleStmt.setString(182, "/images/kia-ev6.jpg");
            vehicleStmt.setString(183, "Available");
            vehicleStmt.setString(184, "Electric Crossover");

            // Vehicle 24 (Electric Vehicle)
            vehicleStmt.setString(185, "ElectricVehicle");
            vehicleStmt.setString(186, "Rivian R1T");
            vehicleStmt.setInt(187, 2023);
            vehicleStmt.setBigDecimal(188, new java.math.BigDecimal("150.00"));
            vehicleStmt.setString(189, "EV008");
            vehicleStmt.setString(190, "/images/rivian-r1t.jpeg");
            vehicleStmt.setString(191, "Available");
            vehicleStmt.setString(192, "Electric Truck");

            int vehicleRows = vehicleStmt.executeUpdate();
            System.out.println(vehicleRows + " vehicle rows inserted.");

        } catch (SQLException e) {
            // e.printStackTrace();
        } finally {
            try {
                if (userStmt != null)
                    userStmt.close();
                if (vehicleStmt != null)
                    vehicleStmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // e.printStackTrace();
            }
        }
    }
}
