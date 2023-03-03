package com.example.demo.controller;

import jakarta.validation.constraints.NotEmpty;

import java.util.Map;
import java.util.UUID;

public class UserCreateRequestDTO {
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String surname;
//    private final Map<UUID, String> taskList;

    public UserCreateRequestDTO(String name, String surname) {
        this.name = name;
        this.surname = surname;
//        this.taskList = taskList;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

//    public Map<UUID, String> getTaskList() {
//        return taskList;
//    }
}
