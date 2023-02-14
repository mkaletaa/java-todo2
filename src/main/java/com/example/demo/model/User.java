package com.example.demo.model;

import java.util.*;

public class User {
    private String name;
    private String surname;
    private UUID userId;
    private List<Task> taskList = new ArrayList<>();

    public User(String name, String surname, UUID userId, List<Task> taskList) {
        this.name = name;
        this.surname = surname;
        this.userId = userId;
        this.taskList = taskList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(name, user.name) && Objects.equals(taskList, user.taskList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userId, taskList);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public UUID getId() {
        return userId;
    }

    public List<Task> getTaskList() {
        return taskList;
    }
}
