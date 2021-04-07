package com.example.personalcloud.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();
    void store(MultipartFile[] files);
}
