package com.example.personalcloud.exception.directory;

public class DirectoryException extends RuntimeException {

    public DirectoryException(String msg) {
        super(msg);
    }

    public DirectoryException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}