package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskMongoRepository;
import com.example.demo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class TaskController {

    private final TaskMongoRepository taskMongoRepository;
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskMongoRepository taskMongoRepository, TaskService taskService) {
        this.taskMongoRepository = taskMongoRepository;
        this.taskService = taskService;
    }


    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @GetMapping(value = "/tasks", params = {})
    public List<TaskResponse> getTasks() {

        return toTaskListResponse(taskMongoRepository.getAllItems());
    }

    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @GetMapping("/tasks/{id}")
    public TaskResponse getSingleTaskById(@PathVariable UUID id) {

        return toTaskResponse(taskMongoRepository.getItemById(id));
    }
    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @GetMapping(value="/tasks", params = "index")
    public TaskResponse getSingleTask(@RequestParam("index") int index) {

        return toTaskResponse(taskMongoRepository.getItemByIndex(index));
    }
    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @GetMapping(value = "/tasks", params = {"name"})
    public List<TaskResponse> getTasksByName(@RequestParam("name") String name, @RequestParam(value = "size", defaultValue = "-1") int size) {

        return toTaskResponseListName(taskMongoRepository.getAllItems(), name, size);
    }


    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @PostMapping("/tasks")
    public TaskResponse addTask(@Valid @RequestBody TaskCreateRequestDTO taskBody) {
//        Task task = new Task(UUID.randomUUID(), taskBody.getName(), taskBody.getDescription(), taskBody.getUserId());
//        taskService.addTaskToUser(taskBody.getUserId(), task);
//        taskMongoRepository.addItem(task);
        return toTaskResponse(taskService.addTaskToUser(taskBody));
    }


    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {

        taskMongoRepository.deleteItem(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @PutMapping("/tasks/{id}")
    public TaskResponse editTask(@PathVariable UUID id, @RequestBody TaskCreateRequestDTO taskBody) {

       Task task = new Task(id, taskBody.getName(), taskBody.getDescription(), taskBody.getUserId());
        taskMongoRepository.updateItem(task);

        return toTaskResponse(task);
    }

    private static TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(task.getId(), task.getName(), task.getDescription());
    }

    private static List<TaskResponse> toTaskListResponse(List<Task> taskList) {
        return taskList.stream()
                .map(task -> new TaskResponse(task.getId(), task.getName(), task.getDescription()))
                .collect(Collectors.toList());
    }

    private static List<TaskResponse> toTaskResponseListName(List<Task> taskList, String name, int size) {
        System.out.println(name);
        return taskList.stream()
                .filter(task -> task.getName().equals(name))
                .limit(size == -1 ? taskList.size() : size)
                .map(task -> new TaskResponse(task.getId(), task.getName(), task.getDescription()))
                .collect(Collectors.toList());
    }


}

