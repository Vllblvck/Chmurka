package com.example.personalcloud.exception.directory;

public class DirectoryAlreadyExistsException extends DirectoryException {

    public DirectoryAlreadyExistsException(String msg) {
        super(msg);
    }

    public DirectoryAlreadyExistsException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
