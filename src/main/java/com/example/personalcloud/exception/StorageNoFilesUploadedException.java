package com.example.personalcloud.exception;

public class StorageNoFilesUploadedException extends StorageException {
    public StorageNoFilesUploadedException(String msg) {
        super(msg);
    }

    public StorageNoFilesUploadedException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
