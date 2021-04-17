package com.example.personalcloud.controller;

import com.example.personalcloud.config.Routes;
import com.example.personalcloud.exception.StorageException;
import com.example.personalcloud.exception.StorageNotMultipartException;
import com.example.personalcloud.model.FileMetadataResponse;
import com.example.personalcloud.service.StorageService;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    public ResponseEntity<Object> uploadFile(HttpServletRequest request) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            throw new StorageNotMultipartException("Request is not a multipart/form-data");
        }

        FileItemIterator fileItemIterator;
        try {
            fileItemIterator = new ServletFileUpload().getItemIterator(request);
        } catch (FileUploadException | IOException ex) {
            throw new StorageException("Exception while getting file iterator from request", ex);
        }

        return new ResponseEntity<>(storageService.store(fileItemIterator), HttpStatus.CREATED);
    }

    @GetMapping(Routes.FILES_METADATA)
    public ResponseEntity<List<FileMetadataResponse>> getFilesMetadata() {
        return new ResponseEntity<>(storageService.getFilesMetadata(), HttpStatus.OK);
    }

    public ResponseEntity<Resource> downloadFile(@PathVariable long fileId) {
        return new ResponseEntity<>(storageService.download(fileId), HttpStatus.OK);
    }
}
