package com.server.notesapp.controller;

import com.server.notesapp.config.CustomUserDetails;
import com.server.notesapp.model.LoginRequest;
import com.server.notesapp.model.LoginResponse;
import com.server.notesapp.model.RegisterResponse;
import com.server.notesapp.model.User;
import com.server.notesapp.service.CustomUserDetailsService;
import com.server.notesapp.service.JwtService;
import com.server.notesapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/loginUser")
    @Secured("permitAll")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        boolean success = userService.loginUser(loginRequest);
        String message = success ? "User logged in successfully" : userService.wrongCredentialsMessage;

        if (!success) {
            LoginResponse response = new LoginResponse(false, message, null, 0, null, null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String jwtToken = jwtService.generateToken(userDetails);
        LoginResponse response = new LoginResponse(true, message, jwtToken, userDetails.getId(), userDetails.getUsername(), userDetails.getUserFullName());

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/saveUser")
    @Secured("permitAll")
    public ResponseEntity<RegisterResponse> saveUser(@RequestBody User user) {

        boolean success = userService.saveUser(user);
        String message = success ? "User registered successfully" : userService.wrongCredentialsMessage;
        RegisterResponse response = new RegisterResponse(success, message);

        if (!success) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/deleteUser/{userId}")
    public void deleteUser(@PathVariable int userId){
        userService.deleteUser(userId);
    }
}