package com.example.demo.userrepository;

import com.example.demo.model.Task;

import java.util.Map;
import java.util.UUID;

public interface Repository<T> {
    public Map<UUID, T> getUserTasks();
}
