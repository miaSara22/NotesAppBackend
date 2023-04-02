package com.server.notesapp.repository;

import com.server.notesapp.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface INoteRepo extends JpaRepository<Note, Integer> {

    java.util.List<Note> findByOwnerId(int ownerId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE notes SET owner_id = :ownerId WHERE note_id = :noteId", nativeQuery = true)
    void setNoteOwnerId(@Param("ownerId") int ownerId, @Param("noteId") int noteId);


}
