package com.example.personalcloud.exception;

public class StorageEmptyFileException extends StorageException {
    public StorageEmptyFileException(String msg) {
        super(msg);
    }

    public StorageEmptyFileException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
