package com.example.demo.controller;

import java.util.Objects;
import java.util.UUID;

public class TaskCreateRequestDTO {


    private final String name;
    private final String description;


    public TaskCreateRequestDTO( String name, String description) {
        this.name = name;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
