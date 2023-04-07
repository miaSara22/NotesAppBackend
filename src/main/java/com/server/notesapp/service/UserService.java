package com.server.notesapp.service;

import com.server.notesapp.model.Role;
import com.server.notesapp.model.User;
import com.server.notesapp.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean saveUser(User user){
        try {
            Optional<User> userEmail = userRepo.findByEmail(user.getEmail());

            if(userEmail.isEmpty() && Objects.equals(user.getUserPwd(), user.getConfirmUserPwd())) {
                String hashedPass = passwordEncoder.encode(user.getUserPwd());
                user.setUserPwd(hashedPass);
                user.setRole(Role.USER);
                userRepo.save(user);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
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
