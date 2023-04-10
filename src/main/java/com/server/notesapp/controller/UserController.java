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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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


    @PostMapping("/login-user")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {

        String userPwd = passwordEncoder.encode(loginRequest.getPwd());
        String userEmail = loginRequest.getEmail();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userEmail, userPwd));

        } catch (BadCredentialsException e) {
            throw new Exception("Wrong email or password", e);
        }
        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        final String jwtToken = jwtService.generateToken(userDetails);

        return ResponseEntity.ok().body(
                new LoginResponse(
                        jwtToken, userDetails.getUsername(), userDetails.getUserFullName())
        );
    }

    @PostMapping("/save-user")
    public ResponseEntity<RegisterResponse> saveUser(@RequestBody User user) {
        System.out.println(user);
        boolean success = userService.saveUser(user);
        String message = success ? "User registered successfully" : "Failed to register user";
        RegisterResponse response = new RegisterResponse(success, message);
        System.out.println(response);
        System.out.println(message);
        if (!success) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
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