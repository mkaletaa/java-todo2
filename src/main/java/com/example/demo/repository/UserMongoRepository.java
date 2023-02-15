package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.mongodb.MongoWriteException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.bson.Document;

import java.util.*;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

@org.springframework.stereotype.Repository
public class UserMongoRepository implements Repository<User> {
    private final MongoDatabase database;

    public MongoDatabase getDatabase() {
        return database;
    }

    @Autowired
    public UserMongoRepository(MongoDatabase mongoDatabase) {
        this.database = mongoDatabase;
    }

    @Override
    public List<User> getAllItems() {
        MongoCollection<Document> collection = database.getCollection("users");
        FindIterable<Document> documents = collection.find();
        List<User> userList = new ArrayList<>();

        for (Document doc : documents) {
            userList.add(new User(doc.get("name", String.class),
                    doc.get("surname", String.class),
                    doc.get("userId", UUID.class),
                    doc.get("taskList", List.class)));
        }

        return userList;
    }

    @Override
    public User getItemById(UUID id) {
        MongoCollection<Document> collection = database.getCollection("users");

        Document query = new Document("userId", id);
        Document userDoc = collection.find(query).first();

        if (userDoc != null) {
            String name = userDoc.getString("name");
            String surname = userDoc.getString("surname");
            List<Document> taskDocs = (List<Document>) userDoc.get("taskList");
            List<Task> tasks = taskDocs.stream()
                    .map(doc -> new Task(doc.get("id", UUID.class), doc.getString("name"), doc.getString("description"), id))
                    .collect(Collectors.toList());

            return new User(name, surname, id, tasks);
        }

        return null;
    }
    @Override
    public User getItemByIndex(int nr) {
        return null;
    }


    @Override
    public void addItem(User user) {
        MongoCollection<Document> collection = database.getCollection("users");

        Document document = new Document("name", user.getName())
                .append("surname", user.getSurname())
                .append("userId", user.getId())
                .append("taskList", user.getTaskList());

        try {
            collection.insertOne(document);
        } catch (MongoWriteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteItem(UUID id){

    }

    @Override
    public void updateItem(User user) {
        MongoCollection<Document> collection = database.getCollection("users");
//this peace of code updates whole user
//        Bson filter = eq("userId", user.getId());
//        Document newDocument = new Document("name", user.getName())
//                .append("surname", user.getSurname())
//                .append("userId", user.getId())
//                .append("taskList", user.getTaskList().stream()
//                        .map(task -> new Document("id", task.getId())
//                                .append("name", task.getName())
//                                .append("description", task.getDescription())
//                                .append("userId", task.getUserId()))
//                        .collect(Collectors.toList()));
//
//        collection.replaceOne(filter, newDocument);

////////////////////////
//this peace of code updates only user's tasks
        Bson filter = eq("userId", user.getId());
        Bson update = set("taskList", user.getTaskList().stream().map(task -> new Document("id", task.getId())
                        .append("name", task.getName())
                        .append("description", task.getDescription())
                        .append("userId", task.getUserId()))
                .collect(Collectors.toList()));
        collection.updateOne(filter, update);
    }



    //not for production
    public void deleteAll(){
        MongoCollection<Document> collection = database.getCollection("users");
        collection.deleteMany(new Document());
    }

}
