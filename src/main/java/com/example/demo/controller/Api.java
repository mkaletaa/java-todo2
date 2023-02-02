package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskMongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class Api {

    private final TaskMongoRepository taskMongoRepository;

    public Api(TaskMongoRepository taskMongoRepository) {
        this.taskMongoRepository = taskMongoRepository;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @GetMapping("/tasks")
    public List<Task> getTasks() {

        return taskMongoRepository.getAllItems();
    }

    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @GetMapping("/tasks/id/{id}")
    public Task getSingleTaskById(@PathVariable UUID id) {

        return taskMongoRepository.getTaskById(id);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @GetMapping("/tasks/{index}")
    public Task getSingleTask(@PathVariable int index) {

        return taskMongoRepository.getTaskByIndex(index);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task task) {

        taskMongoRepository.add(task);

        return task;
    }
    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @DeleteMapping("/tasks/id/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {

        taskMongoRepository.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

