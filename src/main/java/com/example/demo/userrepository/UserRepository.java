package com.example.demo.userrepository;

import com.example.demo.model.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@org.springframework.stereotype.Repository
public class UserRepository implements Repository<Task>{
    Map<UUID, Task> userTasks = new HashMap<>();


    @Override
    public Map<UUID, Task> getUserTasks() {
        return userTasks;
    }
}
