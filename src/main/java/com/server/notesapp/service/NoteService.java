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

    public void saveNote(Note note, int ownerId){
        noteRepo.save(note);
        noteRepo.setNoteOwnerId(ownerId, note.getId());
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