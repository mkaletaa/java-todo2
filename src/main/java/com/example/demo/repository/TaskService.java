package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.userrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;



    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public void addTaskToUser(int userId, Task task) {
        Map<Integer, Task> userTasks = userRepository.getUserTasks();
        userTasks.put(task.getId(), task);
    }
}
