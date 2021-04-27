package com.example.personalcloud.service;

import com.example.personalcloud.model.CreateDirectoryRequest;
import com.example.personalcloud.model.CreateDirectoryResponse;

public interface DirectoryService {

    CreateDirectoryResponse createDirectory(CreateDirectoryRequest request);
}
