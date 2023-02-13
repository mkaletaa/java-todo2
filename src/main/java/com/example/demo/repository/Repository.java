package com.example.demo.repository;


import java.util.List;
import java.util.UUID;

public interface Repository<T> {

    void add(T item);
    T getItemById(UUID id);

    T getItemByIndex(int nr);

    List<T> getAllItems();

    void delete(UUID id);

//TODO: update(item)

}
