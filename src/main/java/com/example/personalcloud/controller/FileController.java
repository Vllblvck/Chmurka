package com.example.personalcloud.controller;

import com.example.personalcloud.config.Routes;
import com.example.personalcloud.model.FileUploadResponse;
import com.example.personalcloud.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//TODO Add logger
//TODO Add async which should allow for more file uploads at the same time
// which is probably not needed for the single user use case but whatever xd
@RestController
public class FileController {

    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(Routes.UPLOAD_FILE)
    public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
        return storageService.store(file);
    }
}
