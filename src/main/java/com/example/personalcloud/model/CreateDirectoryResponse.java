package com.example.personalcloud.model;

public class CreateDirectoryResponse {
    private Long id;
    private String dirName;
    private Long parentId;

    public CreateDirectoryResponse(Long id, String dirName, Long parentId) {
        this.id = id;
        this.dirName = dirName;
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
