package com.server.notesapp.service;

import com.server.notesapp.model.Note;
import com.server.notesapp.repository.INoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    public String wrongCredentialsMessage;

    @Autowired
    public INoteRepo noteRepo;


    public boolean saveNote(Note note){

        if (note.getTitle().isEmpty()) {
            wrongCredentialsMessage = "Note name cannot be empty";
            return false;
        } else if (note.getTitle().length() > 15) {
            wrongCredentialsMessage = "Note name cannot be longer than 15 chars";
            return false;
        } else if (note.getDescription().length() > 100) {
            wrongCredentialsMessage = "Note description cannot be longer than 100 chars";
            return false;
        }

        try {
            noteRepo.save(note);

        } catch (Exception e) {
            throw new RuntimeException("Error saving note to database", e);
        }
        return true;
    }

    public boolean deleteNote(Note note){
        try {
            noteRepo.deleteById(note.getId());
        } catch (Exception e){
            throw new RuntimeException("Failed to remove list from database", e);
        }
        return true;
    }

    public List<Note> getNotes(int ownerId){
        List<Note> notes = new ArrayList<>();
        Streamable.of(noteRepo.findByOwnerId(ownerId))
                .forEach(notes::add);
        return notes;
    }

    public boolean updateNote(Note note){

        if (note.getTitle().isEmpty()) {
            wrongCredentialsMessage = "Note name cannot be empty";
            return false;
        } else if (note.getTitle().length() > 15) {
            wrongCredentialsMessage = "Note name cannot be longer than 15 chars";
            return false;
        } else if (note.getDescription().length() > 100) {
            wrongCredentialsMessage = "Note description cannot be longer than 100 chars";
            return false;
        }
        try {
            noteRepo.updateNote(note.getTitle(), note.getDescription(), note.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error updating note", e);
        }
        return true;
    }
}