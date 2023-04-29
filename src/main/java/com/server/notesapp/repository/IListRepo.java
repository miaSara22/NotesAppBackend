package com.server.notesapp.repository;

import com.server.notesapp.model.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IListRepo extends JpaRepository<List, Integer> {

    @Query(value = "SELECT * FROM lists WHERE owner_id = :ownerId", nativeQuery = true)
    java.util.List<List> findByOwnerId(@Param("ownerId") int ownerId);

    Optional<List> findById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "Update lists SET title = :listName WHERE id = :listId", nativeQuery = true)
    void updateList(@Param("listName") String listName, @Param("listId") int listId);

}