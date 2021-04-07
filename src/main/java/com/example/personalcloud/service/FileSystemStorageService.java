package com.example.personalcloud.service;

import org.springframework.web.multipart.MultipartFile;

public class FileSystemStorageService implements StorageService {

    public void init() {
        //TODO check if directory exists if not create it
        //TODO provide a way to configure this directory
    }

    public void store(MultipartFile[] files) {
        //TODO implement storing files
    }
}
