package com.example.personalcloud.service;

import com.example.personalcloud.config.StorageProperties;
import com.example.personalcloud.entity.FileMetadata;
import com.example.personalcloud.exception.StorageEmptyFileException;
import com.example.personalcloud.exception.StorageException;
import com.example.personalcloud.model.FileUploadResponse;
import com.example.personalcloud.repository.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootUploadLocation;
    private final FilesRepository filesRepository;

    @Autowired
    public FileSystemStorageService(StorageProperties storageProperties, FilesRepository filesRepository) {
        this.rootUploadLocation = Paths.get(storageProperties.getUploadLocation());
        this.filesRepository = filesRepository;
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootUploadLocation);
        } catch (IOException ex) {
            throw new StorageException("Could not initialize storage directory", ex);
        }
    }

    //TODO handle duplicate file (right now it updates it on drive and adds new record to db)
    @Override
    public FileUploadResponse store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageEmptyFileException("Failed to store empty file: " + file.getOriginalFilename());
            }

            Path destinationFile = this.rootUploadLocation.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootUploadLocation.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            FileMetadata fileMetadata = new FileMetadata();
            fileMetadata.setFileName(file.getOriginalFilename());
            fileMetadata.setSize(file.getSize());
            FileMetadata result = filesRepository.save(fileMetadata);

            return new FileUploadResponse(result.getId(), result.getFileName(), result.getSize());

        } catch (IOException ex) {
            throw new StorageException("IOException while storing a file: " + file.getOriginalFilename(), ex);
        }
    }
}
