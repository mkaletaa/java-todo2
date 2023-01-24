package com.example.demo.repository;

import java.util.Map;

public interface Repository<T> {

    void add(T item);
    T get(int id);

    Map<Integer, T> getAllItems();

}
