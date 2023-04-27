package com.server.notesapp.service;

import com.server.notesapp.model.List;
import com.server.notesapp.repository.IListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ListService {

    public String wrongCredentialsMessage;

    @Autowired
    private IListRepo listRepo;


    @Autowired
    private UserService userService;

    public boolean saveList(List list){

        if (list.getTitle().isEmpty()) {
            wrongCredentialsMessage = "List name cannot be empty";
            return false;
        }
        try {
            listRepo.save(list);

        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to save list", e);
        }
        return true;
    }

    public boolean deleteList(List list){
        try {
            listRepo.deleteById(list.getId());
        } catch (Exception e){
            throw new RuntimeException("Failed to delete list", e);
        }
        return true;
    }

    public java.util.List<List> getLists(int ownerId){
        java.util.List<List> lists = new ArrayList<>();
        Streamable.of(listRepo.findByOwnerId(ownerId))
                .forEach(lists::add);
        return lists;
    }

    public boolean updateList(List list){
        try {
            listRepo.updateList(list.getTitle(), list.getId());
        }catch (Exception e){
            throw new RuntimeException("Failed to update list", e);
        }
        return true;
    }
}