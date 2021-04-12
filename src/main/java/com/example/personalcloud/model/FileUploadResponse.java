package com.example.personalcloud.model;

public class FileUploadResponse {
    private long id;
    private String fileName;
    private long size;

    public FileUploadResponse(long id, String fileName, long size) {
        this.id = id;
        this.fileName = fileName;
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
