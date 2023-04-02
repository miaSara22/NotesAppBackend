package com.server.notesapp.controller;

import com.server.notesapp.model.User;
import com.server.notesapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-all-users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/save-user")
    public User saveUser(@Valid @RequestBody User user){
        return userService.save(user);
    }

    @PostMapping("/delete-user/{userId}")
    public void deleteUser(@PathVariable int userId){
        userService.delete(userId);
    }
}