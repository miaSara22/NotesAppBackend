package com.server.notesapp.model;

public class LoginResponse {
    private final String token;
    private final String email;
    private final String fullName;

    public LoginResponse(String token, String email, String fullName) {
        this.token = token;
        this.email = email;
        this.fullName = fullName;
    }
}

