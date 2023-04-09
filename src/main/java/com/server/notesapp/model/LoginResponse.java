package com.server.notesapp.model;

public record LoginResponse(String token, String email, String fullName) {}
