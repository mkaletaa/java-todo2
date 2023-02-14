package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskMongoRepository;
import com.example.demo.repository.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final UserMongoRepository userMongoRepository;

    @Autowired
    public UserController(UserMongoRepository userMongoRepository) {
        this.userMongoRepository = userMongoRepository;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @GetMapping("/users")
    public List<UserResponse> getUsers() {
        return toUserListReponse(userMongoRepository.getAllItems());
    }

    private static List<UserResponse> toUserListReponse(List<User> userList){

        List<UserResponse> userListResponse = userList.stream()
                .map(user->new UserResponse(user.getName(), user.getSurname()))
                .collect(Collectors.toList());

        return userListResponse;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @PostMapping("/users")
    public UserResponse addUser(@RequestBody UserCreateRequestDTO userBody) {
       User user = new User(userBody.getName(),
               userBody.getSurname(),
               UUID.randomUUID(),
               null);

       userMongoRepository.addItem(user);
        return toUserResponse(user);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    @PatchMapping("/users/{id}/tasks")
    public UserResponse updateUserTasks(@RequestBody UserCreateRequestDTO taskBody) {

        return null;
    }

    private static UserResponse toUserResponse(User user){
        return new UserResponse(user.getName(), user.getSurname());
    }
}
