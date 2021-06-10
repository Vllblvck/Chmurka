package com.example.personalcloud.exception;

public class StorageFileNotFoundException extends StorageException {
    public StorageFileNotFoundException(String msg) {
        super(msg);
    }

    public StorageFileNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
