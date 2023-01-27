package com.example.demo.userrepository;

import com.example.demo.model.Task;

import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Repository
public class UserRepository implements Repository<Task>{
    Map<Integer, Task> userTasks = new HashMap<>();


    @Override
    public Map<Integer, Task> getUserTasks() {
        return userTasks;
    }
}
