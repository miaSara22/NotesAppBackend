package com.server.notesapp.repository;

import com.server.notesapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IUserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET image = :userImage WHERE id = :userId", nativeQuery = true)
    void updateUserImage(@Param("userId") Integer userId, @Param("userImage") String userImage);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET image = NULL WHERE id = :userId", nativeQuery = true)
    void deleteUserImage(@Param("userId") Integer userId);

    @Query(value = "SELECT image FROM users WHERE id = :userId", nativeQuery = true)
    String getImageById(@Param("userId") Integer userId);
}
