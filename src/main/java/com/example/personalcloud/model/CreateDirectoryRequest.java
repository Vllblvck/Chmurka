package com.example.personalcloud.model;

public class CreateDirectoryRequest {
    private String dirName;
    private Long parentId;

    public CreateDirectoryRequest(String dirName, Long parentId) {
        this.dirName = dirName;
        this.parentId = parentId;
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
