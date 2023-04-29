package com.server.notesapp.controller;

import com.server.notesapp.model.Note;
import com.server.notesapp.model.ResultResponse;
import com.server.notesapp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/saveNote")
    public ResponseEntity<ResultResponse> saveNote(@RequestBody Note note){

        boolean success = noteService.saveNote(note);
        String message = success ? "Note saved successfully" : noteService.wrongCredentialsMessage;
        ResultResponse resultResponse = new ResultResponse(success, message);

        if (!success) {
            return new ResponseEntity<>(resultResponse, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(resultResponse, HttpStatus.OK);
    }

    @PostMapping("/deleteNote")
    public ResponseEntity<ResultResponse> deleteNote(@RequestBody Note note){

        boolean success = noteService.deleteNote(note);
        String message = success ? "Note deleted successfully" : "Failed to delete note";
        ResultResponse resultResponse = new ResultResponse(success, message);

        if (!success) {
            return new ResponseEntity<>(resultResponse, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(resultResponse, HttpStatus.OK);
    }

    @GetMapping("/getNotes/{ownerId}")
    public List<Note> getNotes(@PathVariable int ownerId){
        return noteService.getNotes(ownerId);
    }

    @PostMapping("/updateNote")
    public ResponseEntity<ResultResponse> updateNote(@RequestBody Note note){
        boolean success = noteService.updateNote(note);
        String message = success ? "Note updated successfully" : noteService.wrongCredentialsMessage;
        ResultResponse resultResponse = new ResultResponse(success, message);

        if (!success) {
            return new ResponseEntity<>(resultResponse, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(resultResponse, HttpStatus.OK);
    }
}