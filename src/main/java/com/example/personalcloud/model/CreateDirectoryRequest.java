package com.example.personalcloud.model;


public class CreateDirectoryRequest {
    private String dirName;
    private long parentId;

    public CreateDirectoryRequest(String dirName, long parentId) {
        this.dirName = dirName;
        this.parentId = parentId;
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