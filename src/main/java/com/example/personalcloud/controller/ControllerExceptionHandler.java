package com.example.personalcloud.controller;

import com.example.personalcloud.exception.StorageDirectoryNotFoundException;
import com.example.personalcloud.exception.StorageEmptyFileException;
import com.example.personalcloud.exception.StorageException;
import com.example.personalcloud.exception.StorageFileNotFoundException;
import com.example.personalcloud.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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

    private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, Exception ex) {
        ApiError apiError = new ApiError(httpStatus, ex);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}