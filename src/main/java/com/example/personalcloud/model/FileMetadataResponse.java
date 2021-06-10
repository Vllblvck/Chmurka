package com.example.personalcloud.model;

public class FileMetadataResponse {
    private long id;
    private long parentId;
    private String fileName;
    private long size;

    public FileMetadataResponse(long id, long parentId, String fileName, long size) {
        this.id = id;
        this.parentId = parentId;
        this.fileName = fileName;
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
