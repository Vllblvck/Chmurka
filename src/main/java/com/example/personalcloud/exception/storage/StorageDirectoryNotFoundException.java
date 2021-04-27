package com.example.personalcloud.exception.storage;

public class StorageDirectoryNotFoundException extends StorageException {

    public StorageDirectoryNotFoundException(String msg) {
        super(msg);
    }

    public StorageDirectoryNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
