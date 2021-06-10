package com.example.personalcloud.model;

public class EditDirectoryRequest {

    private long id;
    private String dirName;

    public EditDirectoryRequest(long id, String dirName) {
        this.id = id;
        this.dirName = dirName;
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
}
