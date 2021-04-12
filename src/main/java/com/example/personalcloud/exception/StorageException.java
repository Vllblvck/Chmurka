package com.example.personalcloud.exception;

public class StorageException extends RuntimeException {

    public StorageException(String msg) {
        super(msg);
    }

    public StorageException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
