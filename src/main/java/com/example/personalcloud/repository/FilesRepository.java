package com.example.personalcloud.repository;

import com.example.personalcloud.entity.FileMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilesRepository extends CrudRepository<FileMetadata, Long> {
    Optional<FileMetadata> findFileMetadataByFileName(String fileName);
}