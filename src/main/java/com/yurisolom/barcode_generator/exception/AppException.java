package com.yurisolom.barcode_generator.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class AppException extends RuntimeException {
    private String message;
    private HttpStatus status;
}
