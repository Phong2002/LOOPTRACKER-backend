package com.looptracker.looptracker.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final String errorCode;   // Custom error code
    private final HttpStatus status;  // HTTP status

    public CustomException(String errorCode, String message, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }


}
