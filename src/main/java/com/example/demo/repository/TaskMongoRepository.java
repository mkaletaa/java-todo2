package com.example.demo.repository;

import com.example.demo.model.Task;
import com.mongodb.MongoWriteException;
import com.mongodb.client.*;
import org.bson.Document;
import com.mongodb.client.model.Filters;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@org.springframework.stereotype.Repository
public class TaskMongoRepository implements Repository<Task> {
    private final MongoDatabase database;

    public TaskMongoRepository(MongoClient mongoClient) {
        this.database = mongoClient.getDatabase("mydb");
    }



//    @Override
    public Task getSingleTaskById(UUID id) {
        MongoCollection<Document> collection = database.getCollection("tasks");

//        Document document = collection.find().skip(nr-1).first();
        Document document = collection.find(Filters.eq("id", id)).first();
        Task task = new Task(document.get("id", UUID.class), document.get("name", String.class), document.get("description", String.class));
        return task;
    }

    public Task getSingleTask(int nr) {
        MongoCollection<Document> collection = database.getCollection("tasks");

        Document document = collection.find().skip(nr-1).first();
//        Document document = collection.find(Filters.eq("id", id)).first();
        Task task = new Task(document.get("id", UUID.class), document.get("name", String.class), document.get("description", String.class));
        return task;
    }

    @Override
    public Task get(UUID id){
        return null;
    }
    @Override
    public List<Task> getAllItems() {
        MongoCollection<Document> collection = database.getCollection("tasks");

        FindIterable<Document> documents = collection.find();
        List<Task> taskList = new ArrayList<>();

        for (Document doc : documents) {
            taskList.add(new Task(doc.get("id", UUID.class), doc.get("name", String.class), doc.get("description", String.class)));
        }

        return taskList;
    }

    @Override
    public void add(Task task) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = mongoClient.getDatabase("mydb");
        MongoCollection<Document> collection = db.getCollection("tasks");


        Document document = new Document("id", UUID.randomUUID())
                .append("name", task.getName())
                .append("description", task.getDescription());

        try {
            collection.insertOne(document);
        } catch (MongoWriteException e) {
            e.printStackTrace();
        }
    }

}
