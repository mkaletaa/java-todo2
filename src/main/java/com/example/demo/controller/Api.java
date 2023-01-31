package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskMongoRepository;
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

//    class AnotherContainer {
//        private final int id = 2;
//        private final String name;
//    }

    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task task) {

        taskMongoRepository.add(task);

        return task;
    }

//    public static void main(String[] args) {
//        SpringApplication.run(Api.class, args);
//    }
}

