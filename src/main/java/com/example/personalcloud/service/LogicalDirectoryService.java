package com.example.personalcloud.service;

import com.example.personalcloud.entity.DirectoryMetadata;
import com.example.personalcloud.exception.directory.DirectoryAlreadyExistsException;
import com.example.personalcloud.exception.directory.DirectoryParentDoesNotExistException;
import com.example.personalcloud.model.CreateDirectoryRequest;
import com.example.personalcloud.model.CreateDirectoryResponse;
import com.example.personalcloud.repository.DirectoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LogicalDirectoryService implements DirectoryService {

    private final DirectoriesRepository directoriesRepository;

    @Autowired
    public LogicalDirectoryService(DirectoriesRepository directoriesRepository) {
        this.directoriesRepository = directoriesRepository;
    }

    //TODO parent id should be optional
    @Override
    public CreateDirectoryResponse createDirectory(CreateDirectoryRequest request) {
        Optional<DirectoryMetadata> parent = this.directoriesRepository.findById(request.getParentId());

        if (!parent.isPresent()) {
            throw new DirectoryParentDoesNotExistException("No parent folder with id: " + request.getParentId());
        }

        Optional<DirectoryMetadata> existingDirectory =
                this.directoriesRepository.findByDirNameAndParent(request.getDirName(), parent.get());

        if (existingDirectory.isPresent()) {
            throw new DirectoryAlreadyExistsException("Directory with name: " + request.getDirName()
                    + " and parent id: " + request.getParentId() + " already exists");
        }

        DirectoryMetadata directoryMetadata = new DirectoryMetadata();
        directoryMetadata.setDirName(request.getDirName());
        directoryMetadata.setParent(parent.get());

        DirectoryMetadata result = this.directoriesRepository.save(directoryMetadata);

        return new CreateDirectoryResponse(
                result.getId(),
                result.getDirName(),
                result.getParent().getId()
        );
    }
}
