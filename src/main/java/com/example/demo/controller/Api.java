package com.example.demo.controller;

import com.mongodb.MongoWriteException;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.model.Task;

import java.util.ArrayList;
import java.util.List;

@RestController
@SpringBootApplication
public class Api {
    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/tasks")
    public List<Document> getTasks() {

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = mongoClient.getDatabase("mydb");
        MongoCollection<Document> collection = db.getCollection("tasks");

        FindIterable<Document> documents = collection.find();
        List<Document> taskList = new ArrayList<>();

        for (Document doc : documents) {
            taskList.add(doc);
        }

        return taskList;
    }

    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/tasks")
    public String addTask(@RequestBody Task task) {

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

        return "Post request";
    }

    public static void main(String[] args) {
        SpringApplication.run(Api.class, args);
    }
}

