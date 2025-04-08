package com.sahara.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private String id;
    private String username;
    private String hashedPassword;
    private String email;
    private String address;
    private final SimpleStringProperty role;
    private final SimpleStringProperty phone;

    public User(String id, String username, String hashedPassword, String email, String role, String phone, String address) {
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.email = email;
        this.role = new SimpleStringProperty(role);
        this.phone = new SimpleStringProperty(phone);
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
    

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Role property methods
    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public StringProperty roleProperty() {
        return role;
    }

    // Phone property methods
    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    // address status methods
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Join date (generated at runtime)
    public java.sql.Timestamp joinDate() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }
}
