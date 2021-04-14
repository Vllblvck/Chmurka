package com.example.personalcloud.exception;

public class StorageDuplicateFileException extends StorageException {
    public StorageDuplicateFileException(String msg) {
        super(msg);
    }

    public StorageDuplicateFileException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
