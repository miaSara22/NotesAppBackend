package com.server.notesapp.service;

import com.server.notesapp.model.Note;
import com.server.notesapp.repository.IListRepo;
import com.server.notesapp.repository.INoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    public INoteRepo noteRepo;

    @Autowired
    public IListRepo listRepo;


    public boolean saveNote(Note note, int ownerId){
        try {
            Optional<com.server.notesapp.model.List> owner = listRepo.findById(ownerId);


        } catch (Exception e) {
            throw new RuntimeException("Error saving note to database", e);
        }
        return false;
    }

    public void deleteNote(int noteId){
        noteRepo.deleteById(noteId);
    }

    public List<Note> getNotes(int ownerId){
        List<Note> notes = new ArrayList<>();
        Streamable.of(noteRepo.findByOwnerId(ownerId))
                .forEach(notes::add);
        return notes;
    }
}