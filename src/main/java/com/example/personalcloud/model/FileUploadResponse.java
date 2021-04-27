package com.example.personalcloud.model;

public class FileUploadResponse {
    private long id;
    private Long parentId;
    private String fileName;
    private long size;

    public FileUploadResponse(long id, Long parentId, String fileName, long size) {
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
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
