package com.server.notesapp.controller;

import com.server.notesapp.model.Note;
import com.server.notesapp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/save-note/{ownerId}")
    public void saveNote(@RequestBody Note note, @PathVariable int ownerId){
        noteService.saveNote(note, ownerId);
    }

    @PostMapping("/delete-note/{noteId}")
    public void deleteNote(@PathVariable int noteId){
        noteService.deleteNote(noteId);
    }

    @GetMapping("/get-notes/{ownerId}")
    public List<Note> getNotes(@PathVariable int ownerId){
        return noteService.getNotes(ownerId);
    }

    @PostMapping("/update-note/{noteId}")
    public void updateNote(@PathVariable int noteId){}
}