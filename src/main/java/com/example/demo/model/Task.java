package com.example.demo.model;

import java.util.Objects;
import java.util.UUID;

public class Task {


    private final UUID id;
    private final String name;
    private final String description;
    private UUID userId;




//    public Task(UUID id, String name, String description) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//    }

    public Task(UUID id, String name, String description, UUID userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    public UUID getId() {
        return id;
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
