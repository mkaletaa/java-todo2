package com.example.demo.repository;


import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface Repository<T> {

    void add(T item);
    T getTaskById(UUID id);

    T getTaskByIndex(int nr);
    List<T> getAllItems();

}
