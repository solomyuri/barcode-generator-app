package com.yurisolom.barcode_generator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage).findFirst().orElse("");
        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), errorMessage));
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppExceptions(AppException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getStatus().toString(), ex.getMessage()), ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAnyExceptions(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage()));
    }

}