package com.example.demo.model;

import java.util.*;

public class User {
    private String name;
    private String surname;
    private UUID id;
    private Map<Integer, Task> taskList = new HashMap<>();

    public User(String name, String surname, UUID id, Map<Integer, Task> taskList) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.taskList = taskList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(taskList, user.taskList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, taskList);
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public Map<Integer, Task> getTaskList() {
        return taskList;
    }
}
