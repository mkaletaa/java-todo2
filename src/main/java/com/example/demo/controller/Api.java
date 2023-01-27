package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskMongoRepository;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@SpringBootApplication
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
    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task task) {

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = mongoClient.getDatabase("mydb");
        MongoCollection<Document> collection = db.getCollection("tasks");


        Document document = new Document("id", task.getId())
                .append("name", task.getName())
                .append("description", task.getDescription());

        try {
            collection.insertOne(document);
        } catch (MongoWriteException e) {
            e.printStackTrace();
        }

//        return "Post request";
        return task;
    }

    public static void main(String[] args) {
        SpringApplication.run(Api.class, args);
    }
}

