package com.server.notesapp.service;

import com.server.notesapp.model.User;
import com.server.notesapp.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Streamable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean saveUser(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            return false;
        }
        String hashedPassword = passwordEncoder.encode(user.getPwd());
        user.setPwd(hashedPassword);

        try {
            User savedUser = userRepo.save(user);
            if (savedUser == null) {
                throw new RuntimeException("Failed to save user to the database");
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to save user to the database", e);
        }
        return true;
    }

    public void deleteUser(int userId){
        userRepo.deleteById(userId);
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        Streamable.of(userRepo.findAll())
                .forEach(users::add);
        return users;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
