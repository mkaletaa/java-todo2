package com.example.demo.userrepository;

import com.example.demo.model.Task;
import com.example.demo.model.User;

import java.util.*;

@org.springframework.stereotype.Repository
public class UserRepository implements Repository<User, Task>{
    Map<UUID, Task> userTasks = new HashMap<>();
    Map<UUID, User> users = new HashMap<>();



    @Override
    public void addUser(User user) {
        users.put(user.getId(), user);
    }
    @Override
    public Map<UUID, Task> getUserTasks() {
        return userTasks;
    }
    @Override
    public Map<UUID, User> getUsers() {
        return users;
    }

    public void setUserTasks(Task task) {
        this.userTasks.put(task.getId(), task);
    }
}
