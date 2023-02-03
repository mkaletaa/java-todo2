package com.example.demo.userrepository;

import com.example.demo.model.Task;
import com.example.demo.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@org.springframework.stereotype.Repository
public class UserRepository implements Repository<User>{
    Map<UUID, User> userTasks = new HashMap<>();


    @Override
    public Map<UUID, User> getUserTasks() {
        return userTasks;
    }
}

//TODO repozutrium dla userów (name, surname, pesel, id)