package com.example.personalcloud.controller;

import com.example.personalcloud.exception.StorageEmptyFileException;
import com.example.personalcloud.exception.StorageException;
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
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        return buildResponseEntity(error);
    }

    @ExceptionHandler(StorageEmptyFileException.class)
    public ResponseEntity<Object> handleStorageEmptyFile(StorageEmptyFileException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex);
        return buildResponseEntity(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex) {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        return buildResponseEntity(error);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError error) {
        return new ResponseEntity<>(error, error.getStatus());
    }
}
