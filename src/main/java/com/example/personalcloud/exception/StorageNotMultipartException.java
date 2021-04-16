package com.example.personalcloud.exception;

public class StorageNotMultipartException extends StorageException {
    public StorageNotMultipartException(String msg) {
        super(msg);
    }

    public StorageNotMultipartException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
