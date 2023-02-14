package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskMongoRepository;
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

    @Autowired
    public TaskController(TaskMongoRepository taskMongoRepository) {
        this.taskMongoRepository = taskMongoRepository;
    }


    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @GetMapping(value = "/tasks", params = {})
    public List<TaskResponse> getTasks() {

        return toTaskListResponse(taskMongoRepository.getAllItems());
    }

    private static List<TaskResponse> toTaskListResponse(List<Task> taskList) {
        return taskList.stream()
                .map(task -> new TaskResponse(task.getId(), task.getName(), task.getDescription()))
                .collect(Collectors.toList());
    }


    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @GetMapping(value = "/tasks", params = "name")
    public List<TaskResponse> getTasksByName(@RequestParam("name") String name) {

        return toTaskResponseList2(taskMongoRepository.getAllItems(), name);
    }

    private static List<TaskResponse> toTaskResponseList2(List<Task> taskList, String name) {
        System.out.println(name);
        return taskList.stream()
                .filter(task -> task.getName().equals(name))
                .map(task -> new TaskResponse(task.getId(), task.getName(), task.getDescription()))
                .collect(Collectors.toList());
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

//    @CrossOrigin(origins = "http://127.0.0.1:5173/")
//    @PostMapping("/tasks")
//    public TaskResponse addTask(@RequestBody TaskCreateRequestDTO taskBody) {
//        UUID userId = UUID.fromString("181d0c94-ed96-41f9-9f76-8ceaa0ce59c2");
//        Task task = new Task(UUID.randomUUID(), taskBody.getName(), taskBody.getDescription(), userId);
//        taskMongoRepository.add(task);
//        return toTaskResponse(task);
//    }

    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @PostMapping("/tasks")
    public TaskResponse addTask(@RequestBody TaskCreateRequestDTO taskBody) {
        Task task = new Task(UUID.randomUUID(), taskBody.getName(), taskBody.getDescription(), taskBody.getUserId());
        taskMongoRepository.addItem(task);
        return toTaskResponse(task);
    }


    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {

        taskMongoRepository.deleteItem(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private static TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(task.getId(), task.getName(), task.getDescription());
    }


}

