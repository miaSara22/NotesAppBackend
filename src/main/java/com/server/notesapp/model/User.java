package com.server.notesapp.model;

import com.server.notesapp.annotation.FieldsMatch;
import com.server.notesapp.annotation.SecuredPassword;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "users")
@FieldsMatch.List({
        @FieldsMatch(fieldOne = "userPwd", fieldTwo = "confirmUserPwd", message = "Password fields doesn't match!")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int userId;

    @NotBlank(message = "Email field cannot be empty!")
    @Email(message = "This email address is not valid!")
    private String email;

    @NotBlank(message = "Name field cannot be empty!")
    @Size(min = 2, max = 30, message = "Name must be at least 3 and max 30 characters.")
    private String fullName;

    @NotBlank(message = "Password cannot be empty!")
    @SecuredPassword
    private String userPwd;

    @Transient
    private String confirmUserPwd;
}