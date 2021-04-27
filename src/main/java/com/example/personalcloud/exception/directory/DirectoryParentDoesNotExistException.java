package com.example.personalcloud.exception.directory;

public class DirectoryParentDoesNotExistException extends DirectoryException {

    public DirectoryParentDoesNotExistException(String msg) {
        super(msg);
    }

    public DirectoryParentDoesNotExistException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
