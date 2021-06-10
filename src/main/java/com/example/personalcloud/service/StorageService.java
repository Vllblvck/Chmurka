package com.example.personalcloud.service;

import com.example.personalcloud.model.FileMetadataResponse;
import com.example.personalcloud.model.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

public interface StorageService {

    void init();

    FileUploadResponse store(MultipartFile file, long parentId);

    List<FileMetadataResponse> getFilesMetadata();

    StreamingResponseBody download(long fileId);

    void delete(long fileId);
}
