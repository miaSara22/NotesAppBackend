package com.server.notesapp.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    private final String token;
    @SerializedName("email")
    private final String email;

    @SerializedName("fullName")
    private final String fullName;

    public LoginResponse(String token, String email, String fullName) {
        this.token = token;
        this.email = email;
        this.fullName = fullName;
    }
}

