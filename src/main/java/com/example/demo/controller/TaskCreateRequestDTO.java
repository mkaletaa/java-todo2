package com.example.demo.controller;

import java.util.UUID;

public class TaskCreateRequestDTO {


    private final String name;
    private final String description;
    private final UUID userId;

    public TaskCreateRequestDTO(String name, String description, UUID userId) {
        this.name = name;
        this.description = description;
        this.userId = userId;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UUID getUserId() {
        return userId;
    }
}
