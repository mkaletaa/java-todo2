package com.example.demo.repository;


import java.util.List;
import java.util.UUID;

public interface Repository<T> {

    void addItem(T item);
    T getItemById(UUID id);

    T getItemByIndex(int nr);

    List<T> getAllItems();

    void deleteItem(UUID id);

    void updateItem(T item);

}
