package com.example.personalcloud.model;

public class EditDirectoryResponse {
    private long id;
    private String dirName;
    private long parentId;

    public EditDirectoryResponse(long id, String dirName, long parentId) {
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