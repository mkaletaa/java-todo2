package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.mongodb.MongoWriteException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        // update every property of the user (name, surname, id, taskList)
        MongoCollection<Document> collection = database.getCollection("users");
//        collection.updateOne(eq("userId", user.getId()),
//                combine(
//                set("name", user.getName()),
//                set("surname", user.getSurname()),
//                set("userId", user.getId()),
//                set("taskList", user.getTaskList())
//                ));

        collection.updateOne(eq("userId", user.getId()),
                        set("taskList", user.getTaskList()));

    }

    //not for production
    public void deleteAll(){
        MongoCollection<Document> collection = database.getCollection("users");
        collection.deleteMany(new Document());
    }

}
