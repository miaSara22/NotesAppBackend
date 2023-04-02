package com.server.notesapp.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "lists")
public class List {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int listId;

    private int ownerId;

    @NotBlank(message = "List name cannot be empty!")
    @Size(min = 2, max = 30, message = "List name must be at least 3 and max 30 characters.")
    private String listName;
}