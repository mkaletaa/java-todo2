package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskMongoRepository;
import com.example.demo.repository.UserMongoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value="test")
class TaskServiceTest {

    @Autowired
    TaskService taskService;

    @Autowired
    UserMongoRepository userMongoRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @AfterEach
    public void deleteAll(){
//        userMongoRepository.deleteAll();
    }

    @Test
    public void shouldAddTaskToUserWhenUserExists(){
        //given
        UUID userId = UUID.fromString("bbdef43f-59a1-47fb-9804-f869e2614cbd");
        List<Task> userTasks = new ArrayList<>();
        User user = new User("John", "Doe", userId, userTasks);
        userMongoRepository.addItem(user);


        Task task = new Task(UUID.randomUUID(), "name", "description", userId);
        //when
        taskService.addTaskToUser(task);
//        taskService.addTaskToUser(task);
        ResponseEntity<User[]> response = restTemplate.getForEntity("/users", User[].class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Http status should be OK");
        assertNotNull(response.getBody());
        System.out.println(response.getBody()[0].getName());
        System.out.println(response.getBody()[0].getId());
//TODO dodatkowe asercje
        //        assertFalse(response.getBody()[0].getTaskList().isEmpty());
//        assertThat(response.getBody()[0].getTaskList()).hasSize(1);


    }


}