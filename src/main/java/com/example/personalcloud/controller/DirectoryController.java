package com.example.personalcloud.controller;

import com.example.personalcloud.config.Routes;
import com.example.personalcloud.model.CreateDirectoryRequest;
import com.example.personalcloud.model.CreateDirectoryResponse;
import com.example.personalcloud.model.EditDirectoryRequest;
import com.example.personalcloud.model.EditDirectoryResponse;
import com.example.personalcloud.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class DirectoryController {

    private final DirectoryService directoryService;

    @Autowired
    public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @PostMapping(Routes.CREATE_DIRECTORY)
    public ResponseEntity<CreateDirectoryResponse> createDirectory(@Valid @RequestBody CreateDirectoryRequest request) {
        CreateDirectoryResponse response = this.directoryService.createDirectory(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(Routes.EDIT_DIRECTORY)
    public ResponseEntity<EditDirectoryResponse> editDirectory(@Valid @RequestBody EditDirectoryRequest request) {
        EditDirectoryResponse response = this.directoryService.editDirectory(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //FIXME wrong response (204 even when file is not really deleted because it does not exist)
    @DeleteMapping(Routes.DELETE_DIRECTORY)
    public ResponseEntity deleteDirectory(@PathVariable long directoryId) {
        this.directoryService.deleteDirectory(directoryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //TODO Add get directories endpoint or combine it with get files endpoint
}
