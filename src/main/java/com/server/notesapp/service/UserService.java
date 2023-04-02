package com.server.notesapp.service;

import com.server.notesapp.model.User;
import com.server.notesapp.repository.IUserRepo;
import com.server.notesapp.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user){
        String hashedPass = passwordEncoder.encode(user.getUserPwd());
        user.setUserPwd(hashedPass);
        return userRepo.save(user);
    }

    public void delete(int userId){
        userRepo.deleteById(userId);
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        Streamable.of(userRepo.findAll())
                .forEach(users::add);
        return users;
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
