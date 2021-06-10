package com.example.personalcloud.service;

import com.example.personalcloud.entity.DirectoryMetadata;
import com.example.personalcloud.entity.FileMetadata;
import com.example.personalcloud.model.CreateDirectoryRequest;
import com.example.personalcloud.model.CreateDirectoryResponse;
import com.example.personalcloud.model.EditDirectoryRequest;
import com.example.personalcloud.model.EditDirectoryResponse;
import com.example.personalcloud.repository.DirectoriesRepository;
import com.example.personalcloud.repository.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class LogicalDirectoryService implements DirectoryService {

    private final DirectoriesRepository directoriesRepository;
    private final FilesRepository filesRepository;

    @Autowired
    public LogicalDirectoryService(DirectoriesRepository directoriesRepository, FilesRepository filesRepository) {
        this.directoriesRepository = directoriesRepository;
        this.filesRepository = filesRepository;
    }

    @Override
    public CreateDirectoryResponse createDirectory(CreateDirectoryRequest request) {
        DirectoryMetadata directoryMetadata = new DirectoryMetadata();

        if (request.getParentId() != 0) {
            Optional<DirectoryMetadata> optional = this.directoriesRepository.findById(request.getParentId());

            if (!optional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No parent directory with id: " + request.getParentId());
            }

            directoryMetadata.setParent(optional.get());
        }

        String dirName = request.getDirName();
        if (dirName == null || dirName.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Directory name can't be null nor empty");
        }

        directoryMetadata.setDirName(request.getDirName());
        Optional<DirectoryMetadata> existingDirectory =
                this.directoriesRepository.findByDirNameAndParent(directoryMetadata.getDirName(), directoryMetadata.getParent());

        if (existingDirectory.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Directory already exists");
        }

        DirectoryMetadata result = this.directoriesRepository.save(directoryMetadata);

        return new CreateDirectoryResponse(
                result.getId(),
                result.getDirName(),
                result.getParentId()
        );
    }

    @Override
    public EditDirectoryResponse editDirectory(EditDirectoryRequest request) {
        Optional<DirectoryMetadata> optional = this.directoriesRepository.findById(request.getId());
        if (!optional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No directory with id: " + request.getId());
        }

        if (request.getDirName() == null || request.getDirName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Directory name can't be null nor empty");
        }

        DirectoryMetadata metadata = optional.get();
        metadata.setDirName(request.getDirName());

        DirectoryMetadata result = this.directoriesRepository.save(metadata);

        return new EditDirectoryResponse(
                result.getId(),
                result.getDirName(),
                result.getParentId()
        );
    }

    //FIXME Literally fixme (exception)
    @Override
    public void deleteDirectory(long directoryId) {
        Optional<DirectoryMetadata> optional = this.directoriesRepository.findById(directoryId);

        if (!optional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No directory with id: " + directoryId);
        }

        this.directoriesRepository.delete(optional.get());
    }
}
