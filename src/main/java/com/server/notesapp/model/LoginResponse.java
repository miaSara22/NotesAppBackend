package com.server.notesapp.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;
    @SerializedName("token")
    private final String token;

    @SerializedName("id")
    private final int id;
    @SerializedName("email")
    private final String email;
    @SerializedName("fullName")
    private final String fullName;

    public LoginResponse(boolean success, String message, String token, int id, String email, String fullName) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.id = id;
        this.email = email;
        this.fullName = fullName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }
}

