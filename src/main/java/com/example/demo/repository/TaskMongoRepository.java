package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import com.mongodb.MongoWriteException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

@org.springframework.stereotype.Repository
public class TaskMongoRepository implements Repository<Task> {
    private final MongoDatabase database;
    private final TaskService taskService;

    public MongoDatabase getDatabase() {
        return database;
    }

    @Autowired
    public TaskMongoRepository(MongoDatabase mongoDatabase, TaskService taskService) {
        this.database = mongoDatabase;
        this.taskService = taskService;
    }

    @Override
    public List<Task> getAllItems() {
        MongoCollection<Document> collection = database.getCollection("tasks");

        FindIterable<Document> documents = collection.find();
        List<Task> taskList = new ArrayList<>();

        for (Document doc : documents) {
            taskList.add(new Task(doc.get("id", UUID.class), doc.get("name", String.class), doc.get("description", String.class), doc.get("userId", UUID.class)));
        }

        return taskList;
    }

    @Override
    public Task getItemById(UUID id) {
        MongoCollection<Document> collection = database.getCollection("tasks");

        Document document = collection.find(Filters.eq("id", id)).first();
        Task task = new Task(document.get("id", UUID.class),
                document.get("name", String.class),
                document.get("description", String.class),
                document.get("userId", UUID.class));
        return task;
    }
    @Override
    public Task getItemByIndex(int nr) {
        MongoCollection<Document> collection = database.getCollection("tasks");

        Document document = collection.find().skip(nr-1).first();
//        Document document = collection.find(Filters.eq("id", id)).first();
        Task task = new Task(document.get("id", UUID.class), document.get("name", String.class), document.get("description", String.class), document.get("userId", UUID.class));
        return task;
    }


    @Override
    public void addItem(Task task) {
        MongoCollection<Document> collection = database.getCollection("tasks");

        Document document = new Document("id", task.getId())
                .append("name", task.getName())
                .append("description", task.getDescription())
                .append("userId", task.getUserId());

        try {
            collection.insertOne(document);
            taskService.addTaskToUser(task);
        }  catch (MongoWriteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteItem(UUID id){
        MongoCollection<Document> collection = database.getCollection("tasks");
        collection.deleteOne(Filters.eq("id", id));
    }

    @Override
    public void updateItem(Task task) {
        MongoCollection<Document> collection = database.getCollection("tasks");

        Document newDocument = new Document("id", task.getId())
                .append("name", task.getName())
                .append("description", task.getDescription())
                .append("userId", task.getUserId());

        Bson filter = Filters.eq("id", task.getId());
        collection.replaceOne(filter, newDocument);

    }

    //not for production
    public void deleteAll(){
        MongoCollection<Document> collection = database.getCollection("tasks");
        collection.deleteMany(new Document());
    }


}

