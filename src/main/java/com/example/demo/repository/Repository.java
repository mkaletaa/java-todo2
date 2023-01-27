package com.example.demo.repository;


import java.util.List;
import java.util.Map;

public interface Repository<T> {

    void add(T item);
    T get(int id);

    List<T> getAllItems();

}
