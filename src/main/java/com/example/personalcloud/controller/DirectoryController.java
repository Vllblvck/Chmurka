package com.example.personalcloud.controller;

import com.example.personalcloud.config.Routes;
import com.example.personalcloud.model.CreateDirectoryRequest;
import com.example.personalcloud.model.CreateDirectoryResponse;
import com.example.personalcloud.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectoryController {

    private final DirectoryService directoryService;

    @Autowired
    public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @PostMapping(Routes.CREATE_DIRECTORY)
    public ResponseEntity<CreateDirectoryResponse> createDirectory(@RequestBody CreateDirectoryRequest request) {
        CreateDirectoryResponse response = this.directoryService.createDirectory(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
