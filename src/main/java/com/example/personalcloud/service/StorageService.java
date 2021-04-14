package com.example.personalcloud.service;

import com.example.personalcloud.model.FileMetadataResponse;
import com.example.personalcloud.model.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    void init();

    FileUploadResponse store(MultipartFile file);

    List<FileMetadataResponse> getFilesMetadata();
}
