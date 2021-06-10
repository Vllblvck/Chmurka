package com.example.personalcloud.service;

import com.example.personalcloud.model.CreateDirectoryRequest;
import com.example.personalcloud.model.CreateDirectoryResponse;
import com.example.personalcloud.model.EditDirectoryRequest;
import com.example.personalcloud.model.EditDirectoryResponse;

public interface DirectoryService {

    CreateDirectoryResponse createDirectory(CreateDirectoryRequest request);

    void deleteDirectory(long directoryId);

    EditDirectoryResponse editDirectory(EditDirectoryRequest request);
}
