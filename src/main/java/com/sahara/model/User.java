package com.sahara.model;

// package model;


public class User {
    @SuppressWarnings("FieldMayBeFinal")
    private String username;
    @SuppressWarnings("FieldMayBeFinal")
    private String hashedPassword;
    @SuppressWarnings("FieldMayBeFinal")
    private String email;
    @SuppressWarnings("FieldMayBeFinal")
    private String role;

    public User(String username, String hashedPassword, String email,String role) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.email = email;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getEmail() {
        return email;
    }
    public String role() {
        return role;
    }
}

