package com.example.personalcloud.service;

import com.example.personalcloud.config.StorageProperties;
import com.example.personalcloud.entity.FileMetadata;
import com.example.personalcloud.exception.StorageException;
import com.example.personalcloud.exception.StorageFileNotFoundException;
import com.example.personalcloud.exception.StorageNoFilesUploadedException;
import com.example.personalcloud.model.FileMetadataResponse;
import com.example.personalcloud.model.FileUploadResponse;
import com.example.personalcloud.repository.FilesRepository;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public FileUploadResponse store(FileItemIterator fileItemIterator) {
        Path destinationFile = null;

        try {
            while (fileItemIterator.hasNext()) {
                FileItemStream fileItem = fileItemIterator.next();

                if (fileItem.isFormField()) {
                    continue;
                }

                destinationFile = this.rootUploadLocation.resolve(
                        Paths.get(fileItem.getName())).normalize().toAbsolutePath();

                if (!destinationFile.getParent().equals(this.rootUploadLocation.toAbsolutePath())) {
                    throw new StorageException("Cannot store file outside current directory");
                }

                try (InputStream inputStream = fileItem.openStream()) {
                    Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                }

                Optional<FileMetadata> savedMetadata = filesRepository.findFileMetadataByFileName(fileItem.getName());
                if (savedMetadata.isPresent()) {
                    FileMetadata fileMetadata = savedMetadata.get();
                    return new FileUploadResponse(
                            fileMetadata.getId(),
                            fileMetadata.getFileName(),
                            fileMetadata.getSize()
                    );
                }

                File uploadedFile = destinationFile.toFile();
                FileMetadata fileMetadata = new FileMetadata();
                fileMetadata.setFileName(uploadedFile.getName());
                fileMetadata.setSize(uploadedFile.length());
                fileMetadata.setPath(destinationFile.toString());
                FileMetadata result = filesRepository.save(fileMetadata);

                return new FileUploadResponse(
                        result.getId(),
                        result.getFileName(),
                        result.getSize()
                );
            }

            throw new StorageNoFilesUploadedException("No files in request");

        } catch (FileUploadException | IOException ex) {

            if (destinationFile != null) {
                File partlyUploadedFile = destinationFile.toFile();
                partlyUploadedFile.delete();
            }

            throw new StorageException("Exception while storing file", ex);
        }
    }

    @Override
    public List<FileMetadataResponse> getFilesMetadata() {
        List<FileMetadataResponse> fileMetadataResponseList = new ArrayList<>();

        for (FileMetadata fileMetadata : filesRepository.findAll()) {
            FileMetadataResponse fileMetadataResponse = new FileMetadataResponse(fileMetadata.getId(),
                    fileMetadata.getFileName(), fileMetadata.getSize());
            fileMetadataResponseList.add(fileMetadataResponse);
        }

        return fileMetadataResponseList;
    }

    //TODO Allow multiple files download? (with zip archive)
    @Override
    public StreamingResponseBody download(long fileId) {

        return outputStream -> {
            Optional<FileMetadata> fileMetadata = filesRepository.findById(fileId);

            if (!fileMetadata.isPresent()) {
                throw new StorageFileNotFoundException("No file with id: " + fileId);
            }

            String pathString = fileMetadata.get().getPath();
            Path path = Paths.get(pathString);

            Files.copy(path, outputStream);
        };
    }

    @Override
    public void delete(long fileId) {
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
    }
}