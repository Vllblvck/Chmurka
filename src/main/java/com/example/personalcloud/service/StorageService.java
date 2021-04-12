package com.example.personalcloud.service;

import com.example.personalcloud.model.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();

    FileUploadResponse store(MultipartFile file);
}
