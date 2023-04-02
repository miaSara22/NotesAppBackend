package com.server.notesapp.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int noteId;

    private int ownerId;

    @NotBlank(message = "Note name cannot be empty!")
    @Size(min = 2, max = 30, message = "Note name must be at least 3 and max 30 characters.")
    private String noteName;

    @Size(max = 50, message = "Note description cannot be over 50 characters.")
    private String noteDescription;
}
