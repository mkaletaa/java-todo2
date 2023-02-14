package com.example.demo.controller;

import java.util.Map;
import java.util.UUID;

public class UserCreateRequestDTO {
    private final String name;
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
