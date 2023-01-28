package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskMongoRepository;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class Api {

    private final TaskMongoRepository taskMongoRepository;

    public Api(TaskMongoRepository taskMongoRepository) {
        this.taskMongoRepository = taskMongoRepository;
    }

    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/tasks")
    public List<Task> getTasks() {

        return taskMongoRepository.getAllItems();
    }

    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/tasks/id/{id}")
    public Task getSingleTaskById(@PathVariable UUID id) {

        return taskMongoRepository.getSingleTaskById(id);
    }

    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/tasks/{nr}")
    public Task getSingleTask(@PathVariable int nr) {

        return taskMongoRepository.getSingleTask(nr);
    }

    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task task) {

        taskMongoRepository.add(task);

        return task;
    }

//    public static void main(String[] args) {
//        SpringApplication.run(Api.class, args);
//    }
}

