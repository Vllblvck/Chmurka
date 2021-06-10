package com.example.personalcloud.model;

public class CreateDirectoryResponse {
    private long id;
    private String dirName;
    private long parentId;

    public CreateDirectoryResponse(long id, String dirName, Long parentId) {
        this.id = id;
        this.dirName = dirName;
        this.parentId = parentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
