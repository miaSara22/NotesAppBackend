package com.server.notesapp.service;

import com.server.notesapp.model.LoginRequest;
import com.server.notesapp.model.Role;
import com.server.notesapp.model.User;
import com.server.notesapp.repository.IListRepo;
import com.server.notesapp.repository.INoteRepo;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserService {

    public String wrongCredentialsMessage;
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private IListRepo listRepo;

    @Autowired
    private INoteRepo noteRepo;


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
        } else if (user.getEmail().length() > 200) {
            wrongCredentialsMessage = "Email field cannot be longer than 200 chars";
            return false;
        } else if (user.getPwd().length() > 50) {
            wrongCredentialsMessage = "Password field cannot be longer than 50 chars";
            return false;
        } else if (user.getFullName().length() > 50) {
            wrongCredentialsMessage = "Name field cannot be longer than 50 chars";
            return false;
        }


        String hashedPassword = passwordEncoder.encode(user.getPwd());
        user.setPwd(hashedPassword);
        user.setRole(Role.USER);

        try {
            userRepo.save(user);

        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to save user", e);
        }

        return true;
    }

    public boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    public boolean deleteUser(User user){
        try {
            userRepo.deleteById(user.getId());

            List<com.server.notesapp.model.List> lists = listRepo.findByOwnerId(user.getId());
            for (com.server.notesapp.model.List list : lists) {
                    listRepo.delete(list);
                    noteRepo.deleteNotes(list.getId());

                }

        } catch (Exception e){
            throw new RuntimeException("Failed to delete user", e);
        }
        return true;


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

    public boolean updateUserImage(Integer userId, String image) {
        try {
            userRepo.updateUserImage(userId, image);
        } catch (Exception e){
            throw new RuntimeException("Failed to save image", e);
        }
        return true;
    }

    public boolean deleteUserImage(Integer userId) {
        try {
            userRepo.deleteUserImage(userId);
        } catch (Exception e){
            throw new RuntimeException("Failed to delete image", e);
        }
        return true;
    }

    public String getUserImageIfExists(Integer userId) {
        try {
            String userImage = userRepo.getImageById(userId);
            if (userRepo.findById(userId).isPresent() && userImage != null) {
                return userImage;

            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to find user image", e);
        }
    }
}