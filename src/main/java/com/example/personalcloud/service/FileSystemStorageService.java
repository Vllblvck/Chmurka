package com.example.personalcloud.service;

import com.example.personalcloud.config.StorageProperties;
import com.example.personalcloud.entity.DirectoryMetadata;
import com.example.personalcloud.entity.FileMetadata;
import com.example.personalcloud.exception.StorageDirectoryNotFoundException;
import com.example.personalcloud.exception.StorageEmptyFileException;
import com.example.personalcloud.exception.StorageException;
import com.example.personalcloud.exception.StorageFileNotFoundException;
import com.example.personalcloud.model.FileMetadataResponse;
import com.example.personalcloud.model.FileUploadResponse;
import com.example.personalcloud.repository.DirectoriesRepository;
import com.example.personalcloud.repository.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO refactor error handling to use ResponseStatusException
@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootUploadLocation;
    private final FilesRepository filesRepository;
    private final DirectoriesRepository directoriesRepository;

    @Autowired
    public FileSystemStorageService(
            StorageProperties storageProperties,
            FilesRepository filesRepository,
            DirectoriesRepository directoriesRepository) {

        this.rootUploadLocation = Paths.get(storageProperties.getUploadLocation());
        this.filesRepository = filesRepository;
        this.directoriesRepository = directoriesRepository;
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootUploadLocation);
        } catch (IOException ex) {
            throw new StorageException("Could not initialize storage directory", ex);
        }
    }

    //TODO provide filetype validation
    @Override
    public FileUploadResponse store(MultipartFile file, long parentId) {

        try {
            if (file.isEmpty()) {
                throw new StorageEmptyFileException("Failed to store empty file");
            }

            FileMetadata fileMetadata = new FileMetadata();
            fileMetadata.setFileName(file.getOriginalFilename());
            fileMetadata.setSize(file.getSize());

            if (parentId != 0) {
                Optional<DirectoryMetadata> parentDirectoryMetadata = directoriesRepository.findById(parentId);

                if (!parentDirectoryMetadata.isPresent()) {
                    throw new StorageDirectoryNotFoundException("No directory with id: " + parentId);
                }

                fileMetadata.setParent(parentDirectoryMetadata.get());
            }

            FileMetadata result = filesRepository.save(fileMetadata);

            String fileName = Long.toString(result.getId());
            Path destinationPath = this.rootUploadLocation
                    .resolve(Paths.get(fileName))
                    .normalize()
                    .toAbsolutePath();

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            }

            return new FileUploadResponse(
                    result.getId(),
                    result.getParentId(),
                    result.getFileName(),
                    result.getSize()
            );

        } catch (IOException ex) {
            throw new StorageException("Exception while storing a file", ex);
        }
    }

    @Override
    public List<FileMetadataResponse> getFilesMetadata() {
        List<FileMetadataResponse> fileMetadataResponseList = new ArrayList<>();

        for (FileMetadata fileMetadata : filesRepository.findAll()) {
            FileMetadataResponse fileMetadataResponse = new FileMetadataResponse(
                    fileMetadata.getId(),
                    fileMetadata.getParentId(),
                    fileMetadata.getFileName(),
                    fileMetadata.getSize()
            );

            fileMetadataResponseList.add(fileMetadataResponse);
        }

        return fileMetadataResponseList;
    }

    @Override
    public StreamingResponseBody download(long fileId) {

        return outputStream -> {
            Optional<FileMetadata> fileMetadata = filesRepository.findById(fileId);

            if (!fileMetadata.isPresent()) {
                throw new StorageFileNotFoundException("No file with id: " + fileId);
            }

            String fileName = Long.toString(fileMetadata.get().getId());
            Path filePath = this.rootUploadLocation.resolve(
                    Paths.get(fileName)).normalize().toAbsolutePath();

            Files.copy(filePath, outputStream);
        };
    }

    //TODO Implement
    @Override
    public void delete(long fileId) {
/*
        Optional<FileMetadata> fileMetadata = filesRepository.findById(fileId);

        if (!fileMetadata.isPresent()) {
            throw new StorageFileNotFoundException("No file with id: " + fileId);
        }

        String path = fileMetadata.get().getPath();
        boolean result = new File(path).delete();

        if (!result) {
            throw new StorageException("Failed to delete file with id: " + fileId);
        }

        filesRepository.deleteById(fileId);
*/
    }
}