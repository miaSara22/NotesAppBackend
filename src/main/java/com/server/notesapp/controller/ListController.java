package com.server.notesapp.controller;

import com.server.notesapp.model.List;
import com.server.notesapp.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ListController {

    @Autowired
    private ListService listService;

    @PostMapping("/save-list/{ownerId}")
    public void saveList(@RequestBody List list, @PathVariable int ownerId){
        listService.saveList(list, ownerId);
    }

    @PostMapping("/delete-list/{listId}")
    public void deleteList(@PathVariable int listId){
        listService.deleteList(listId);
    }

    @GetMapping("/get-lists/{ownerId}")
    public java.util.List<List> getLists(@PathVariable int ownerId){
        return listService.getLists(ownerId);
    }

    @PostMapping("/update-list-name/{listId}")
    public void updateList(@RequestBody List list, @PathVariable int listId){ listService.updateList(list, listId);}
}