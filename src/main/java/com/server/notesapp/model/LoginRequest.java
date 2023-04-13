package com.server.notesapp.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class LoginRequest {
    @SerializedName("email")
    private String email;
    @SerializedName("pwd")
    private String pwd;
}
