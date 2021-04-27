package com.example.personalcloud.controller;

import com.example.personalcloud.config.Routes;
import com.example.personalcloud.model.FileMetadataResponse;
import com.example.personalcloud.model.FileUploadResponse;
import com.example.personalcloud.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(name = "parentId", required = false) Long parentId) {

        FileUploadResponse response = this.storageService.store(file, parentId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(Routes.FILES_METADATA)
    public ResponseEntity<List<FileMetadataResponse>> getFilesMetadata() {
        List<FileMetadataResponse> response = this.storageService.getFilesMetadata();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(Routes.DOWNLOAD_FILE)
    public ResponseEntity<StreamingResponseBody> downloadFile(@PathVariable long fileId) {
        StreamingResponseBody responseBody = this.storageService.download(fileId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping(Routes.DELETE_FILE)
    public ResponseEntity deleteFile(@PathVariable long fileId) {
        this.storageService.delete(fileId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
