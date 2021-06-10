package com.example.personalcloud.exception;

public class StorageDirectoryNotFoundException extends StorageException {

    public StorageDirectoryNotFoundException(String msg) {
        super(msg);
    }

    public StorageDirectoryNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
