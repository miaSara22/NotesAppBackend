package com.server.notesapp.controller;

import com.server.notesapp.model.RegisterResponse;
import com.server.notesapp.model.User;
import com.server.notesapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save-user")
    public ResponseEntity<RegisterResponse> saveUser(@RequestBody User user) {
        System.out.println(user);
        boolean success = userService.saveUser(user);
        String message = success ? "User registered successfully" : "Failed to register user";
        RegisterResponse response = new RegisterResponse(success, message);
        System.out.println(response);
        System.out.println(message);
        if (!success) {
            throw new RuntimeException("Failed to save user to the database.");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RegisterResponse> handleRuntimeException(RuntimeException e) {
        RegisterResponse response = new RegisterResponse(false, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get-all-users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/delete-user/{userId}")
    public void deleteUser(@PathVariable int userId){
        userService.deleteUser(userId);
    }
}