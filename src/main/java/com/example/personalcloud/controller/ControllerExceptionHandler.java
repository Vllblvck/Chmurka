package com.example.personalcloud.controller;

import com.example.personalcloud.exception.storage.StorageDirectoryNotFoundException;
import com.example.personalcloud.exception.storage.StorageEmptyFileException;
import com.example.personalcloud.exception.storage.StorageException;
import com.example.personalcloud.exception.storage.StorageFileNotFoundException;
import com.example.personalcloud.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//TODO check if this is really the best way to handle exceptions in my case (a lot of code duplication here)
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleStorageException(StorageException ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler({StorageEmptyFileException.class, StorageDirectoryNotFoundException.class})
    public ResponseEntity<Object> handleStorageBadRequest(StorageException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<Object> handleStorageFileNotFound(StorageFileNotFoundException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, Exception ex) {
        ApiError apiError = new ApiError(httpStatus, ex);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
