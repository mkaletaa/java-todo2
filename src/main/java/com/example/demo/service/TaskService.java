package com.example.demo.service;

import com.example.demo.controller.TaskCreateRequestDTO;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskMongoRepository;
import com.example.demo.repository.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final UserMongoRepository userMongoRepository;
    private final TaskMongoRepository taskMongoRepository;

    @Autowired
    public TaskService(UserMongoRepository userMongoRepository, TaskMongoRepository taskMongoRepository) {
        this.userMongoRepository = userMongoRepository;
        this.taskMongoRepository = taskMongoRepository;
    }

//    public void addTaskToUser(Task task) {
//
//        if (userMongoRepository.getItemById(task.getUserId()) != null) {
////            throw new IllegalStateException();
//
//            User user = userMongoRepository.getItemById(task.getUserId());
//            List<Task> userTasks = user.getTaskList();
//            userTasks.add(task);
//            User updatedUser = new User(user.getName(), user.getSurname(), user.getId(), userTasks);
//            userMongoRepository.updateItem(updatedUser);
//        }
//
//    }

    public Task addTaskToUser(TaskCreateRequestDTO taskBody) {
        Task task = new Task(UUID.randomUUID(), taskBody.getName(), taskBody.getDescription(), taskBody.getUserId());
        taskMongoRepository.addItem(task);
        User user = userMongoRepository.getItemById(task.getUserId());
        user.getTaskList().add(task);
        userMongoRepository.updateItem(user);
        //TODOo: pobrać name i description i tworzyć taska tutaj
        //TODO: unit test
        return task;
    }

    public void test() {
        System.out.println("Hello World");
    }

}
