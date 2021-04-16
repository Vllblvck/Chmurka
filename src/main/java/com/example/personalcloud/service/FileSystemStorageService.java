package com.example.personalcloud.service;

import com.example.personalcloud.config.StorageProperties;
import com.example.personalcloud.entity.FileMetadata;
import com.example.personalcloud.exception.StorageDuplicateFileException;
import com.example.personalcloud.exception.StorageException;
import com.example.personalcloud.model.FileMetadataResponse;
import com.example.personalcloud.model.FileUploadResponse;
import com.example.personalcloud.repository.FilesRepository;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    //TODO finish implementation
    // Don't allow multiple files uploads (easier error handling and client can send request one after another so whatever)
    @Override
    public FileUploadResponse store(FileItemIterator fileItemIterator) {

        try {
            while (fileItemIterator.hasNext()) {
                FileItemStream fileItem = fileItemIterator.next();

                if (fileItem.isFormField()) {
                    continue;
                }

                Path destinationFile = this.rootUploadLocation.resolve(Paths.get(fileItem.getName()))
                        .normalize().toAbsolutePath();

                if (!destinationFile.getParent().equals(this.rootUploadLocation.toAbsolutePath())) {
                    throw new StorageException("Cannot store file outside current directory");
                }

                try (InputStream inputStream = fileItem.openStream()) {
                    Files.copy(inputStream, destinationFile);
                }

               /* FileMetadata fileMetadata = new FileMetadata();
                fileMetadata.setFileName(file.getOriginalFilename());
                fileMetadata.setSize(file.getSize());
                FileMetadata result = filesRepository.save(fileMetadata);*/
            }

            return new FileUploadResponse(0, "askjfhaskjhf", 0);

        } catch (FileAlreadyExistsException ex) {
            throw new StorageDuplicateFileException("File already exists");
        } catch (FileUploadException | IOException ex) {
            ex.printStackTrace();
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

    //TODO Find best way to download large files
    //TODO Allow multiple files download? (maybe let frontend handle that?)
    @Override
    public Resource download(long fileId) {
        return null;
    }
}
