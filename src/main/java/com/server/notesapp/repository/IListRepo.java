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

    java.util.List<List> findByOwnerId(int ownerId);

    Optional<List> findById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE lists SET owner_id = :ownerId WHERE list_id = :listId", nativeQuery = true)
    void setListOwnerId(@Param("ownerId") int ownerId, @Param("listId") int listId);

    @Modifying
    @Transactional
    @Query(value = "Update lists SET list_name = :listName WHERE list_id = :listId", nativeQuery = true)
    void updateList(@Param("listName") String listName, @Param("listId") int listId);
}
