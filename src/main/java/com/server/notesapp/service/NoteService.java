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

    @Autowired
    public INoteRepo noteRepo;


    public boolean saveNote(Note note){
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
        try {
            noteRepo.updateNote(note.getTitle(), note.getDescription(), note.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error updating note", e);
        }
        return true;
    }
}