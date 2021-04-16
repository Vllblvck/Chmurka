package com.example.personalcloud.service;

import com.example.personalcloud.model.FileMetadataResponse;
import com.example.personalcloud.model.FileUploadResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.springframework.core.io.Resource;

import java.util.List;

public interface StorageService {

    void init();

    FileUploadResponse store(FileItemIterator fileItemIterator);

    List<FileMetadataResponse> getFilesMetadata();

    Resource download(long fileId);
}
