package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.User;

import java.util.List;
import java.util.UUID;

public class UserRepository implements Repository<User>{

    @Override
    public void add(User item) {

    }

    @Override
    public User getItemById(UUID id) {
        return null;
    }

    @Override
    public User getItemByIndex(int nr) {
        return null;
    }

    @Override
    public List<User> getAllItems() {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
