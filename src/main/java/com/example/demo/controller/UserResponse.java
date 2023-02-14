package com.example.demo.controller;

public class UserResponse {
    private final String name;
    private final String surname;
    public UserResponse(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
}
