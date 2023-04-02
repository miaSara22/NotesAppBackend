package com.server.notesapp.controller;

import com.server.notesapp.model.LoginRequest;
import com.server.notesapp.model.LoginResponse;
import com.server.notesapp.model.User;
import com.server.notesapp.service.UserService;
import com.server.notesapp.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login-user")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){

        try{
            User user = userService.getUserByEmail(loginRequest.getEmail());

            if (user != null && BCrypt.checkpw(loginRequest.getPwd(), user.getUserPwd())) {
                String token = jwtUtils.generateToken(user.getEmail());
                return ResponseEntity.ok(new LoginResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
