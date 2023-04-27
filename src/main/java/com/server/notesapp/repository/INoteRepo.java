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

    @Query(value = "SELECT * FROM notes WHERE owner_id = :ownerId", nativeQuery = true)
    java.util.List<Note> findByOwnerId(@Param("ownerId") Integer ownerId);

    @Modifying
    @Transactional
    @Query(value = "Update notes SET title = :noteTitle, description = :noteDesc WHERE id = :noteId", nativeQuery = true)
    void updateNote(@Param("noteTitle") String title,@Param("noteDesc") String noteDesc, @Param("noteId") Integer noteId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM notes WHERE owner_id = :ownerId", nativeQuery = true)
    void deleteNotes(@Param("ownerId") Integer ownerId);

}
