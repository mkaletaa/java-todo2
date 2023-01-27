package com.example.demo.repository;

import com.example.demo.model.Task;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@org.springframework.stereotype.Repository
public class TaskMongoRepository implements Repository<Task> {
    private final MongoDatabase database;

    public TaskMongoRepository(MongoClient mongoClient) {
        this.database = mongoClient.getDatabase("mydb");
    }

    @Override
    public void add(Task item) {

    }

    @Override
    public Task get(int id) {
        return null;
    }

    @Override
    public List<Task> getAllItems() {
        MongoCollection<Document> collection = database.getCollection("tasks");

        FindIterable<Document> documents = collection.find();
        List<Task> taskList = new ArrayList<>();

        for (Document doc : documents) {
            taskList.add(new Task(doc.get("id", Integer.class), doc.get("name", String.class), doc.get("description", String.class)));
        }

        return taskList;
    }
}
