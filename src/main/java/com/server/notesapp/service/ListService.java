package com.server.notesapp.service;

import com.server.notesapp.model.List;
import com.server.notesapp.repository.IListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ListService {

    @Autowired
    private IListRepo listRepo;


    @Autowired
    private UserService userService;

    public void saveList(List list){
        listRepo.save(list);

    }

    public void deleteList(int listId){
        listRepo.deleteById(listId);
    }

    public java.util.List<List> getLists(int ownerId){
        java.util.List<List> lists = new ArrayList<>();
        Streamable.of(listRepo.findByOwnerId(ownerId))
                .forEach(lists::add);
        return lists;
    }

    public void updateList(List list, int listId){ listRepo.updateList(list.getTitle(), listId); }

}
