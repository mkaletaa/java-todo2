package com.example.demo.repository;


import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface Repository<T> {

    void add(T item);
    T get(UUID id);

    List<T> getAllItems();

}
