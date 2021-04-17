package com.example.personalcloud.controller;

import com.example.personalcloud.exception.StorageException;
import com.example.personalcloud.exception.StorageNoFilesUploadedException;
import com.example.personalcloud.exception.StorageNotMultipartException;
import com.example.personalcloud.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//TODO check if this is really the best way to handle exceptions in my case (a lot of code duplication here)
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleStorageException(StorageException ex) {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        return buildResponseEntity(error);
    }

    @ExceptionHandler({
            StorageNotMultipartException.class,
            StorageNoFilesUploadedException.class
    })
    public ResponseEntity<Object> handleStorageBadRequestExceptions(StorageException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex);
        return buildResponseEntity(error);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleSizeLimitExceeded(MaxUploadSizeExceededException ex) {
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
