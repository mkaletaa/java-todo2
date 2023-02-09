package com.example.demo.userrepository;

import com.example.demo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface Repository<U, T> {
     void addUser(U user);
     Map<UUID, T> getUserTasks();
     Map<UUID, U> getUsers();

}
