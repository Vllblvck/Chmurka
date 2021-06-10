package com.example.personalcloud.service;

import com.example.personalcloud.entity.DirectoryMetadata;
import com.example.personalcloud.model.CreateDirectoryRequest;
import com.example.personalcloud.model.CreateDirectoryResponse;
import com.example.personalcloud.model.EditDirectoryRequest;
import com.example.personalcloud.model.EditDirectoryResponse;
import com.example.personalcloud.repository.DirectoriesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogicalDirectoryServiceTest {

    @Mock
    private DirectoriesRepository directoriesRepository;
    @InjectMocks
    private LogicalDirectoryService directoryService;

    @Test
    public void createDirectory_ValidRequest_ResponseEqualToRequest() {
        CreateDirectoryRequest request = new CreateDirectoryRequest("ValidDirName", 0);
        DirectoryMetadata directoryMetadata = new DirectoryMetadata();
        directoryMetadata.setDirName("ValidDirName");

        when(directoriesRepository.save(any(DirectoryMetadata.class))).thenReturn(directoryMetadata);
        CreateDirectoryResponse response = directoryService.createDirectory(request);

        assertEquals(request.getDirName(), response.getDirName());
        assertEquals(request.getParentId(), response.getParentId());
    }

    @Test
    public void createDirectory_BlankDirName_ResponseStatusException() {
        CreateDirectoryRequest request = new CreateDirectoryRequest("", 0L);

        Throwable exception = assertThrows(ResponseStatusException.class, () -> {
            directoryService.createDirectory(request);
        });
        assertEquals("400 BAD_REQUEST \"Directory name can't be null nor empty\"", exception.getMessage());
    }

    @Test
    public void createDirectory_NullDirName_ResponseStatusException() {
        CreateDirectoryRequest request = new CreateDirectoryRequest(null, 0L);

        Throwable exception = assertThrows(ResponseStatusException.class, () -> {
            directoryService.createDirectory(request);
        });
        assertEquals("400 BAD_REQUEST \"Directory name can't be null nor empty\"", exception.getMessage());
    }

    @Test
    public void createDirectory_InvalidParentId_ResponseStatusException() {
        long parentId = 20L;
        CreateDirectoryRequest request = new CreateDirectoryRequest("ValidDirName", parentId);

        Throwable exception = assertThrows(ResponseStatusException.class, () -> {
            directoryService.createDirectory(request);
        });
        assertEquals("400 BAD_REQUEST \"No parent directory with id: " + parentId + "\"", exception.getMessage());
    }

    @Test
    public void editDirectory_ValidRequest_ChangedDirName() {
        long directoryId = 1;
        String newDirName = "NewName";
        String oldDirName = "OldName";

        EditDirectoryRequest request = new EditDirectoryRequest(directoryId, newDirName);

        DirectoryMetadata directoryMetadataBeforeSave = new DirectoryMetadata();
        directoryMetadataBeforeSave.setId(directoryId);
        directoryMetadataBeforeSave.setDirName(oldDirName);
        Optional<DirectoryMetadata> optional = Optional.of(directoryMetadataBeforeSave);

        DirectoryMetadata directoryMetadataAfterSave = new DirectoryMetadata();
        directoryMetadataAfterSave.setId(directoryId);
        directoryMetadataAfterSave.setDirName(newDirName);

        when(directoriesRepository.findById(directoryId)).thenReturn(optional);
        when(directoriesRepository.save(any(DirectoryMetadata.class))).thenReturn(directoryMetadataAfterSave);
        EditDirectoryResponse response = directoryService.editDirectory(request);

        assertEquals(directoryId, response.getId());
        assertEquals(newDirName, response.getDirName());
        assertEquals(0, response.getParentId());
    }

    @Test
    public void editDirectory_InvalidId_ResponseStatusException() {
        long id = 0;
        EditDirectoryRequest request = new EditDirectoryRequest(id, "NewDirName");
        Optional<DirectoryMetadata> optional = Optional.empty();

        when(directoriesRepository.findById(id)).thenReturn(optional);

        Throwable exception = assertThrows(ResponseStatusException.class, () -> {
            directoryService.editDirectory(request);
        });
        assertEquals("400 BAD_REQUEST \"No directory with id: " + id + "\"", exception.getMessage());
    }

    @Test
    public void editDirectory_BlankDirName_ResponseStatusException() {
        String dirName = "";
        long id = 1L;
        EditDirectoryRequest request = new EditDirectoryRequest(id, dirName);
        DirectoryMetadata directoryMetadata = new DirectoryMetadata();
        directoryMetadata.setId(id);
        directoryMetadata.setDirName("OldDirName");
        Optional<DirectoryMetadata> optional = Optional.of(directoryMetadata);

        when(directoriesRepository.findById(id)).thenReturn(optional);

        Throwable exception = assertThrows(ResponseStatusException.class, () -> {
            directoryService.editDirectory(request);
        });
        assertEquals("400 BAD_REQUEST \"Directory name can't be null nor empty\"", exception.getMessage());
    }

    @Test
    public void editDirectory_NullDirName_ResponseStatusException() {
        String dirName = null;
        long id = 1L;
        EditDirectoryRequest request = new EditDirectoryRequest(id, dirName);
        DirectoryMetadata directoryMetadata = new DirectoryMetadata();
        directoryMetadata.setId(id);
        directoryMetadata.setDirName("OldDirName");
        Optional<DirectoryMetadata> optional = Optional.of(directoryMetadata);

        when(directoriesRepository.findById(id)).thenReturn(optional);

        Throwable exception = assertThrows(ResponseStatusException.class, () -> {
            directoryService.editDirectory(request);
        });
        assertEquals("400 BAD_REQUEST \"Directory name can't be null nor empty\"", exception.getMessage());
    }
}