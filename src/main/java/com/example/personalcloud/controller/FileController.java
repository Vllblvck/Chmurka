package com.example.personalcloud.controller;

import com.example.personalcloud.config.Routes;
import com.example.personalcloud.exception.StorageNotMultipartException;
import com.example.personalcloud.model.FileMetadataResponse;
import com.example.personalcloud.model.FileUploadResponse;
import com.example.personalcloud.service.StorageService;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<FileUploadResponse> uploadFile(HttpServletRequest request) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            throw new StorageNotMultipartException("Request is not a multipart/form-data");
        }

        return new ResponseEntity<>(storageService.store(request), HttpStatus.CREATED);
    }

    @GetMapping(Routes.FILES_METADATA)
    public ResponseEntity<List<FileMetadataResponse>> getFilesMetadata() {
        return new ResponseEntity<>(storageService.getFilesMetadata(), HttpStatus.OK);
    }

    @GetMapping(Routes.DOWNLOAD_FILE)
    public ResponseEntity<StreamingResponseBody> downloadFile(@PathVariable long fileId) {
        return new ResponseEntity<>(storageService.download(fileId), HttpStatus.OK);
    }

    @DeleteMapping(Routes.DELETE_FILE)
    public ResponseEntity deleteFile(@PathVariable long fileId) {
        storageService.delete(fileId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
