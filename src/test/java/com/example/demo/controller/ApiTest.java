package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskMongoRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value="test")
public class ApiTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskMongoRepository taskMongoRepository;

    @AfterEach
    public void deleteAll(){
        taskMongoRepository.deleteAll();
        ////
//        MongoCollection<Document> collection = taskMongoRepository.getDatabase().getCollection("tasks");
//        collection.deleteMany(new Document());
        ////
//        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
//        MongoDatabase database = mongoClient.getDatabase("mydbtest");
//        MongoCollection<Document> collection = database.getCollection("tasks");
//        collection.deleteMany(new Document());
    }

    @Test
    public void shouldGetTasks() {
        //given
        String userId = "181d0c94-ed96-41f9-9f76-8ceaa0ce59c2";
        String[] idArray = {"85be177f-5e63-44b5-bddc-894d15a4d26e",
                "bbdef43f-59a1-47fb-9804-f869e2614cbd",
                "74ad803c-d78f-4630-ae0c-7205cf5cb9c5"};
        for (String s : idArray) {
            UUID taskId = UUID.fromString(s);
            Task task = new Task(taskId, "GetTest", "get all tasks", UUID.fromString(userId));
//            restTemplate.postForEntity("/tasks", task, String.class);
            taskMongoRepository.add(task);
        }

        //when

        // Make a GET request to the endpoint
        ResponseEntity<Task[]> response = restTemplate.getForEntity("/tasks", Task[].class);

        //then

        // Assert that the response has a status of OK
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");

        // Assert that the response body is not null
        assertNotNull(response.getBody());
        assertThat(response.getBody()).hasSize(3);
    }

    @Test
    public void shouldGetTaskById() {
        //given
        String userId = "181d0c94-ed96-41f9-9f76-8ceaa0ce59c2";
        String id = "f1ecfecd-9e5b-4b5f-abc1-99978da78af1";
        UUID taskId = UUID.fromString(id);
        Task expectedTask = new Task(taskId, "GetTest", "get tasks by id", UUID.fromString(userId));
        taskMongoRepository.add(expectedTask);

        //when
        ResponseEntity<Task> response = restTemplate.getForEntity("/tasks/f1ecfecd-9e5b-4b5f-abc1-99978da78af1" , Task.class);
        Task actualTask = taskMongoRepository.getTaskById(taskId);

        //then
        assertEquals(expectedTask.getId(), actualTask.getId());
        // Assert that the response has a status of OK
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");
        // Assert that the response body is not null
        assertNotNull(response.getBody());
    }

    @Test
    public void shouldGetTaskByIndex() {
        //given
        String userId = "181d0c94-ed96-41f9-9f76-8ceaa0ce59c2";
        String index = "1";
        String id = "85be177f-5e63-44b5-bddc-894d15a4d26e";
        UUID task1Id = UUID.fromString(id);
        Task task = new Task(task1Id, "GetTest", "get tasks by index", UUID.fromString(userId));
        // Make a POST request to the endpoint
//        restTemplate.postForEntity("/tasks", task, String.class);
        taskMongoRepository.add(task);

        //when
        ResponseEntity<Task> response = restTemplate.getForEntity("/tasks/index/" + index, Task.class);

        //then
        System.out.println(response);
        // Assert that the response has a status of OK
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");

        // Assert that the response body is not null
        assertNotNull(response.getBody());
    }


    @Test
    public void shouldPost() {
        //given
        String userId = "181d0c94-ed96-41f9-9f76-8ceaa0ce59c2";
        String id = "e499b5df-e341-41c5-bf7a-06bc9c9bc4e9";
        UUID task1Id = UUID.fromString(id);
        Task task = new Task(task1Id, "PostTestxxx", "post a task", UUID.fromString(userId));
        //when
        ResponseEntity<String> response = restTemplate.postForEntity("/tasks", task, String.class);

        //then
        // Assert that the response has a status of CREATED
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");

        // Assert that the response body is not null
        assertNotNull(response.getBody());

    }

    @Test
    public void deleteTaskTest() {
        //given
        String userId = "181d0c94-ed96-41f9-9f76-8ceaa0ce59c2";
        String id = "f1ecfecd-9e5b-4b5f-abc1-99978da78af1";
        UUID task1Id = UUID.fromString(id);
        Task task = new Task(task1Id, "GetTest", "get tasks by id", UUID.fromString(userId));
        restTemplate.postForEntity("/tasks", task, String.class);

        //when
        ResponseEntity<Void> response = restTemplate.exchange("/tasks/" + id, HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()), Void.class);

        //then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


}
//
