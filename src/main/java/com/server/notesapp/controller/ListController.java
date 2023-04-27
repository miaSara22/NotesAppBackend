package com.server.notesapp.controller;

import com.server.notesapp.model.List;
import com.server.notesapp.model.ResultResponse;
import com.server.notesapp.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ListController {

    @Autowired
    private ListService listService;

    @PostMapping("/saveList")
    public ResponseEntity<ResultResponse> saveList(@RequestBody List list){

        boolean success = listService.saveList(list);
        String message = success ? "List saved successfully" : listService.wrongCredentialsMessage;
        ResultResponse resultResponse = new ResultResponse(success, message);
        if (!success) {
            return new ResponseEntity<>(resultResponse, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(resultResponse, HttpStatus.OK);
    }

    @PostMapping("/deleteList")
    public ResponseEntity<ResultResponse> deleteList(@RequestBody List list){
        boolean success = listService.deleteList(list);
        String message = success ? "List deleted successfully" : "Failed to delete list";
        ResultResponse resultResponse = new ResultResponse(success, message);

        if (!success) {
            return new ResponseEntity<>(resultResponse, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(resultResponse, HttpStatus.OK);
    }

    @GetMapping("/getLists/{ownerId}")
    public java.util.List<List> getLists(@PathVariable int ownerId){
        return listService.getLists(ownerId);
    }

    @PostMapping("/updateList")
    public ResponseEntity<ResultResponse> updateList(@RequestBody List list){

        boolean success = listService.updateList(list);
        String message = success ? "List updated successfully" : "Failed to update list";
        ResultResponse resultResponse = new ResultResponse(success, message);

        if (!success) {
            return new ResponseEntity<>(resultResponse, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(resultResponse, HttpStatus.OK);
    }
}