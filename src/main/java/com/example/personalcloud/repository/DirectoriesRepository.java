package com.example.personalcloud.repository;


import com.example.personalcloud.entity.DirectoryMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectoriesRepository extends CrudRepository<DirectoryMetadata, Long> {
    Optional<DirectoryMetadata> findByDirNameAndParent(String dirName, DirectoryMetadata parent);
}