package com.example.personalcloud.exception.storage;

import com.example.personalcloud.exception.storage.StorageException;

public class StorageFileNotFoundException extends StorageException {
    public StorageFileNotFoundException(String msg) {
        super(msg);
    }

    public StorageFileNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
