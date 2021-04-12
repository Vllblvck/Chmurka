package com.example.personalcloud.controller;

import com.example.personalcloud.config.Routes;
import com.example.personalcloud.exception.StorageException;
import com.example.personalcloud.model.FileUploadResponse;
import com.example.personalcloud.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    //TODO custom null file request handling
    @PostMapping(Routes.UPLOAD_FILE)
    public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
        return storageService.store(file);
    }

    //TODO this needs tweaking
    @ExceptionHandler(StorageException.class)
    public StorageException handleStorageException(StorageException ex) {
        return ex;
    }
}
