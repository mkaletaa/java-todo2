package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.model.Task;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldGet() {
        //given
        //taski do repozytorium do bazy danych

        //when

        // Make a GET request to the endpoint
        ResponseEntity<Task[]> response = restTemplate.getForEntity("/tasks", Task[].class);

        //then


        // Assert that the response has a status of OK
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");

        // Assert that the response body is not null
        assertNotNull(response.getBody());
//        assertThat(response.getBody()).hasSize(13);
    }

//    @Test

//    @Test
//    public void shouldPost() {
//        // Create a new Task object
//        Task task = new Task(1, "nazwa", "opis");
//
//        // Make a POST request to the endpoint
//        ResponseEntity<String> response = restTemplate.postForEntity("/tasks", task, String.class);
//
//        // Assert that the response has a status of CREATED
//        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");
//
//        // Assert that the response body is not null
//        assertNotNull(response.getBody());
//
//        // Add another test to check if the task is added to the mongodb or not
//        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
//        MongoDatabase db = mongoClient.getDatabase("mydb");
//        MongoCollection<Document> collection = db.getCollection("tasks");
//        Document taskToCheck = collection.find(new Document("id", task.getId())).first();
//
//        assertNotNull(taskToCheck);
//        assertEquals(task.getId(), taskToCheck.get("id"));
//        assertEquals(task.getName(), taskToCheck.get("name"));
//        assertEquals(task.getDescription(), taskToCheck.get("description"));
//    }


}
