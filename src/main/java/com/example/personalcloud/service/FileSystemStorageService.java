package com.example.personalcloud.service;

import com.example.personalcloud.config.StorageProperties;
import com.example.personalcloud.exception.StorageException;
import com.example.personalcloud.model.FileUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

//TODO Better error handling
@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootUploadLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties storageProperties) {
        rootUploadLocation = Paths.get(storageProperties.getUploadLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootUploadLocation);
        } catch (IOException ex) {
            throw new StorageException("Could not initialize storage directory", ex);
        }
    }

    @Override
    public FileUploadResponse store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file: " + file.getOriginalFilename());
            }

            Path destinationFile = this.rootUploadLocation.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            //TODO save info to db in order to retrieve actual id
            long fileId = 0;

            return new FileUploadResponse(fileId, file.getOriginalFilename(), file.getSize());

        } catch (IOException ex) {
            throw new StorageException("IOException while storing a file: " + file.getOriginalFilename(), ex);
        }
    }
}
