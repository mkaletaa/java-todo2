package com.example.demo.repository;

public interface Repository<T> {

    void add(T item);
    T get(int id);
}
