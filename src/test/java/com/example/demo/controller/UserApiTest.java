package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskMongoRepository;
import com.example.demo.repository.UserMongoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value="test")
public class UserApiTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserMongoRepository userMongoRepository;

    @AfterEach
    public void deleteAll(){
        userMongoRepository.deleteAll();
    }

    @Test
    public void shouldGetUsers() {
        //given

        String[][] userArray = {{"Jan", "Kowalski","85be177f-5e63-44b5-bddc-894d15a4d26e"},
                {"Kasia", "Nowak", "bbdef43f-59a1-47fb-9804-f869e2614cbd"},
                {"Ola", "Kowalczyk","74ad803c-d78f-4630-ae0c-7205cf5cb9c5"}};

        for (String[] s : userArray) {
            User user = new User(s[0], s[1], UUID.fromString(s[2]), null);
            userMongoRepository.addItem(user);
        }


        //when
        ResponseEntity<User[]> response = restTemplate.getForEntity("/users", User[].class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");
        assertNotNull(response.getBody());
        assertThat(response.getBody()).hasSize(3);
        assertThat(response.getBody()[0].getName()).isEqualTo("Jan");
        assertThat(response.getBody()[0].getSurname()).isEqualTo("Kowalski");
        assertThat(response.getBody()[1].getName()).isEqualTo("Kasia");
        assertThat(response.getBody()[2].getSurname()).isEqualTo("Kowalczyk");
    }

    @Test
    public void shouldGetUserById() {
        //given
        String name = "John";
        String surname = "Doe";
        String userId = "181d0c94-ed96-41f9-9f76-8ceaa0ce59c2";
        List<Task> taskList = new ArrayList<>();
        User expectedUser = new User(name, surname, UUID.fromString(userId),taskList);
        userMongoRepository.addItem(expectedUser);

        //when
        ResponseEntity<UserResponse> response = restTemplate.getForEntity("/users/181d0c94-ed96-41f9-9f76-8ceaa0ce59c2" , UserResponse.class);
//        User actualUser = userMongoRepository.getItemById(UUID.fromString(userId));

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");
        assertNotNull(response.getBody());
        assertThat(response.getBody().getName()).isEqualTo(name);
        assertThat(response.getBody().getSurname()).isEqualTo(surname);
//        assertEquals(expectedUser.getId(), actualUser.getId());
    }
    @Test
    public void shouldUpdate(){
        //given
        UUID userId = UUID.fromString("bbdef43f-59a1-47fb-9804-f869e2614cbd");
        List<Task> userTasks = new ArrayList<>();
        User user = new User("John", "Doe", userId, userTasks);
        userMongoRepository.addItem(user);
        Task task = new Task(UUID.randomUUID(), "name", "description", userId);

        //when
        userMongoRepository.updateItem(user);
        ResponseEntity<User[]> response = restTemplate.getForEntity("/users", User[].class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");
        assertNotNull(response.getBody());
        assertThat(response.getBody()[0].getName()).isEqualTo("John");
        assertThat(response.getBody()[0].getSurname()).isEqualTo("Doe");
    }

    @Test
    public void shouldPost() {
        //given
        String name = "John";
        String surname = "Doe";
//        UUID userId = UUID.fromString("181d0c94-ed96-41f9-9f76-8ceaa0ce59c2");

        UserCreateRequestDTO user = new UserCreateRequestDTO( name, surname);
        //when
        ResponseEntity<UserResponse> response = restTemplate.postForEntity("/users", user, UserResponse.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");
        assertNotNull(response.getBody());
        assertThat(response.getBody().getName()).isEqualTo(name);
        assertThat(response.getBody().getSurname()).isEqualTo(surname);
    }

    @Test
    public void shouldDeleteUserTest() {
        //given
        String userId = "181d0c94-ed96-41f9-9f76-8ceaa0ce59c2";
        String name = "John";
        String surname = "Doe";
        List<Task> taskList = null;
        User user = new User(name, surname, UUID.fromString(userId), taskList);
        userMongoRepository.addItem(user);
//        restTemplate.postForEntity("/tasks", task, String.class);

        //when
        ResponseEntity<Void> response = restTemplate.exchange("/users/" + userId, HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()), Void.class);
//
//        //then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
