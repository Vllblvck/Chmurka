package com.example.personalcloud.controller;

import com.example.personalcloud.config.Routes;
import com.example.personalcloud.entity.FileMetadata;
import com.example.personalcloud.model.FileMetadataResponse;
import com.example.personalcloud.model.FileUploadResponse;
import com.example.personalcloud.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//TODO Add logger
@RestController
public class FileController {

    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(Routes.UPLOAD_FILE)
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(storageService.store(file), HttpStatus.CREATED);
    }

    @GetMapping(Routes.FILES_METADATA)
    public ResponseEntity<List<FileMetadataResponse>> getFilesMetadata() {
        return new ResponseEntity<>(storageService.getFilesMetadata(), HttpStatus.OK);
    }
}
