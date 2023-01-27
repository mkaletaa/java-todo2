package com.example.demo.userrepository;

import com.example.demo.model.Task;

import java.util.Map;

public interface Repository<T> {
    public Map<Integer, T> getUserTasks();
}
