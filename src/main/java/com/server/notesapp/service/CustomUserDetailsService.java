package com.server.notesapp.service;

import com.server.notesapp.config.CustomUserDetails;
import com.server.notesapp.model.User;
import com.server.notesapp.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepo userRepo;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepo.findByEmail(username);
        System.out.println(user);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not Found");
        }
        return new CustomUserDetails(user.get());
    }
}