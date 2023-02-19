package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskMongoRepository;
import com.example.demo.repository.UserMongoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value="test")
public class TaskApiTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskMongoRepository taskMongoRepository;

//    @Autowired
//    private UserMongoRepository userMongoRepository;
//


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
            taskMongoRepository.addItem(task);
        }

        //when
        ResponseEntity<Task[]> response = restTemplate.getForEntity("/tasks", Task[].class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");
        assertNotNull(response.getBody());
        assertThat(response.getBody()).hasSize(3);
    }

    @Nested
    class TestGetByName {

        @Test
        public void shouldGetTasksByName() {
            //given
            String userId = "181d0c94-ed96-41f9-9f76-8ceaa0ce59c2";
            String[][] tasksDataArray = {{"85be177f-5e63-44b5-bddc-894d15a4d26e", "test"},
                    {"bbdef43f-59a1-47fb-9804-f869e2614cbd", "test"},
                    {"74ad803c-d78f-4630-ae0c-7205cf5cb9c5", "xxx"}};

            for (String[] s : tasksDataArray) {
                UUID taskId = UUID.fromString(s[0]);
                String name = s[1];
                Task task = new Task(taskId, name, "get all tasks", UUID.fromString(userId));
//            restTemplate.postForEntity("/tasks", task, String.class);
                taskMongoRepository.addItem(task);
            }

            //when

            // Make a GET request to the endpoint
            ResponseEntity<TaskResponse[]> response = restTemplate.getForEntity("/tasks?name=test", TaskResponse[].class);
            TaskResponse[] tasks = response.getBody();
            List<String> taskNames = Arrays.stream(tasks)
                    .map(TaskResponse::getName)
                    .collect(Collectors.toList());

            //then

            assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");
            assertNotNull(response.getBody());
            assertThat(response.getBody()).hasSize(2);
            assertThat(taskNames.size()).isEqualTo(2);
            assertThat(response.getBody().length).isEqualTo(2);
            assertThat(response.getBody()).hasSize(2);
            assertThat(taskNames).doesNotContain("xxx");
            boolean tasksHaveCorrectName = Arrays.stream(response.getBody())
                    .allMatch(task -> task.getName().equals("test"));
            assertThat(tasksHaveCorrectName).isTrue();
        }

        @Test
        public void shouldGetTasksByNameWithSpecifiedSize() {
            //given
            String userId = "181d0c94-ed96-41f9-9f76-8ceaa0ce59c2";
            String[][] tasksDataArray = {{"85be177f-5e63-44b5-bddc-894d15a4d26e", "test"},
                    {"bbdef43f-59a1-47fb-9804-f869e2614cbd", "test"},
                    {"74ad803c-d78f-4630-ae0c-7205cf5cb9c5", "xxx"}};

            for (String[] s : tasksDataArray) {
                UUID taskId = UUID.fromString(s[0]);
                String name = s[1];
                Task task = new Task(taskId, name, "get all tasks", UUID.fromString(userId));
//            restTemplate.postForEntity("/tasks", task, String.class);
                taskMongoRepository.addItem(task);
            }

            //when

            // Make a GET request to the endpoint
            ResponseEntity<TaskResponse[]> response = restTemplate.getForEntity("/tasks?name=test&size=1", TaskResponse[].class);
            TaskResponse[] tasks = response.getBody();
            List<String> taskNames = Arrays.stream(tasks)
                    .map(TaskResponse::getName)
                    .collect(Collectors.toList());

            //then

            assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");
            assertNotNull(response.getBody());
            assertThat(response.getBody()).hasSize(1);
            assertThat(taskNames.size()).isEqualTo(1);
            assertThat(response.getBody().length).isEqualTo(1);
            assertThat(response.getBody()).hasSize(1);
            assertThat(taskNames).doesNotContain("xxx");
            boolean tasksHaveCorrectName = Arrays.stream(response.getBody())
                    .allMatch(task -> task.getName().equals("test"));
            assertThat(tasksHaveCorrectName).isTrue();
        }
    }

    @Test
    public void shouldGetTaskById() {
        //given
        String userId = "181d0c94-ed96-41f9-9f76-8ceaa0ce59c2";
        UUID taskId = UUID.fromString("f1ecfecd-9e5b-4b5f-abc1-99978da78af1");
        String name = "GetTest";
        String description = "get tasks by id";
        Task expectedTask = new Task(taskId, name, description, UUID.fromString(userId));
        taskMongoRepository.addItem(expectedTask);

        //when
        ResponseEntity<TaskResponse> response = restTemplate.getForEntity("/tasks/f1ecfecd-9e5b-4b5f-abc1-99978da78af1" , TaskResponse.class);
        Task actualTask = taskMongoRepository.getItemById(taskId);

        //then
        assertEquals(expectedTask.getId(), actualTask.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");
        assertNotNull(response.getBody());
        assertThat(response.getBody().getName()).isEqualTo(name);
        assertThat(response.getBody().getDescription()).isEqualTo(description);
        assertNotNull(response.getBody().getId());
    }

    @Test
    public void shouldGetTaskByIndex() {
        //given
        String userId = "181d0c94-ed96-41f9-9f76-8ceaa0ce59c2";
        String index = "1";
        UUID taskId = UUID.fromString("85be177f-5e63-44b5-bddc-894d15a4d26e");
        String name = "GetTest";
        String description = "get tasks by index";
        Task task = new Task(taskId, name, description, UUID.fromString(userId));
        taskMongoRepository.addItem(task);

        //when
        ResponseEntity<TaskResponse> response = restTemplate.getForEntity("/tasks?index=" + index, TaskResponse.class);

        //then
        System.out.println(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");
        assertNotNull(response.getBody());
        assertThat(response.getBody().getName()).isEqualTo(name);
        assertThat(response.getBody().getDescription()).isEqualTo(description);
        assertNotNull(response.getBody().getId());
        //TODO: sprawdź czy zwraca pustą listę jak nie ma taska
    }

//    @Test
//    public void shouldReturnEmptyListIfNoTaskForIndex() {
//        //given
//        String index = "999"; // zakładamy, że indeks nie istnieje w bazie danych
//
//        //when
//        ResponseEntity<TaskResponse> response = restTemplate.getForEntity("/tasks?index=" + index, TaskResponse.class);
//
//        //then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        System.out.println(response.getBody().hasSize());
////        assertThat(response.getBody()).hasSize(0);
//
//    }


    @Test
    public void shouldPost() {
        //given
        String id = "e499b5df-e341-41c5-bf7a-06bc9c9bc4e9";
        String name = "PostTest";
        String description = "post a task";
        UUID userId = UUID.fromString("181d0c94-ed96-41f9-9f76-8ceaa0ce59c2");

        TaskCreateRequestDTO task = new TaskCreateRequestDTO( name, description, userId);
        //when
        ResponseEntity<TaskResponse> response = restTemplate.postForEntity("/tasks", task, TaskResponse.class);
//        Task gotTask= taskMongoRepository.getItemById(UUID.fromString(id));
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");
        assertNotNull(response.getBody());
        assertThat(response.getBody().getName()).isEqualTo(name);
        assertThat(response.getBody().getDescription()).isEqualTo(description);
        assertNotNull(response.getBody().getId());

//        assertThat(gotTask.getId()).isEqualTo(UUID.fromString(id));
//        assertThat(gotTask.getName()).isEqualTo(name);
//        assertThat(gotTask.getDescription()).isEqualTo(description);
//        assertThat(gotTask.getUserId()).isEqualTo(userId);
    }


    @Test
    public void shouldUpdateTask(){
        //given
        String id = "e499b5df-e341-41c5-bf7a-06bc9c9bc4e9";
        String name = "PutTest";
        String description = "update a task";
        UUID userId = UUID.fromString("181d0c94-ed96-41f9-9f76-8ceaa0ce59c2");
        taskMongoRepository.addItem(new Task(UUID.fromString(id), name, description, userId));

        String updatedName = "updated Name";
        TaskCreateRequestDTO task = new TaskCreateRequestDTO( updatedName, description, userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TaskCreateRequestDTO> request = new HttpEntity<>(task, headers);
        //when
        ResponseEntity<TaskResponse> response = restTemplate.exchange("/tasks/" + id, HttpMethod.PUT, request, TaskResponse.class);

        Task modifiedTask= taskMongoRepository.getItemById(UUID.fromString(id));
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");
        assertNotNull(response.getBody());
        assertThat(response.getBody().getName()).isEqualTo(updatedName);
        assertThat(response.getBody().getDescription()).isEqualTo(description);
        assertNotNull(response.getBody().getId());

        assertThat(modifiedTask.getId()).isEqualTo(UUID.fromString(id));
        assertThat(modifiedTask.getName()).isEqualTo(updatedName);
        assertThat(modifiedTask.getDescription()).isEqualTo(description);
        assertThat(modifiedTask.getUserId()).isEqualTo(userId);
    }

    @Test
    public void shouldDeleteTaskTest() {
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
