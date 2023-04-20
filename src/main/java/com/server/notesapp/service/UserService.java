package com.server.notesapp.service;

import com.server.notesapp.model.LoginRequest;
import com.server.notesapp.model.Role;
import com.server.notesapp.model.User;
import com.server.notesapp.repository.IUserRepo;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Streamable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserService {

    public String wrongCredentialsMessage;
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean loginUser(LoginRequest loginRequest){

        if (userRepo.findByEmail(loginRequest.getEmail()).isPresent()) {

            User user = userRepo.findByEmail(loginRequest.getEmail()).get();

            if (passwordEncoder.matches(loginRequest.getPwd(), user.getPwd())) {
                return true;

            } else {
                wrongCredentialsMessage = "Wrong Password";
                return false;
            }
        } else {
            wrongCredentialsMessage = "User does not exist";
            return false;
        }
    }

    public boolean saveUser(User user) {

        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            wrongCredentialsMessage = "This email address is already registered";
            return false;
        } else if (user.getEmail().isEmpty() || !isValidEmail(user.getEmail())) {
            wrongCredentialsMessage = "Invalid Email Address";
            return false;
        } else if (!Objects.equals(user.getPwd(), user.getConfirmPwd()) || user.getPwd().isEmpty()) {
            wrongCredentialsMessage = "Passwords fields must match and can't be empty";
            return false;
        } else if (user.getFullName().isEmpty() || user.getFullName() == null) {
            wrongCredentialsMessage = "Name field cannot be empty";
            return false;
        }

        String hashedPassword = passwordEncoder.encode(user.getPwd());
        user.setPwd(hashedPassword);
        user.setRole(Role.USER);

        try {
            userRepo.save(user);

        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to save user to database", e);
        }

        return true;
    }

    public boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    public void deleteUser(int userId){
        userRepo.deleteById(userId);
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        LOGGER.log(Level.INFO, "Before findAll() method call");
        Streamable.of(userRepo.findAll())
                .forEach(users::add);
        LOGGER.log(Level.INFO, "After findAll() method call");
        LOGGER.log(Level.INFO, "Users: {0}", users);
        return users;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
