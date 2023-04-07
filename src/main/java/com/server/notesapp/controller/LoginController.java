package com.server.notesapp.controller;

import com.server.notesapp.config.CustomUserDetails;
import com.server.notesapp.model.LoginRequest;
import com.server.notesapp.model.LoginResponse;
import com.server.notesapp.service.CustomUserDetailsService;
import com.server.notesapp.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login-user")
public class LoginController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getPwd()));

        } catch (BadCredentialsException e) {
            throw new Exception("Wrong email or password", e);
        }
        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String jwtToken = jwtService.generateToken(userDetails);

        return ResponseEntity.ok().body(
                new LoginResponse(
                        jwtToken, userDetails.getUsername(), userDetails.getUserFullName())
        );
    }
}
