package com.looptracker.looptracker.exception;

import lombok.Data;

@Data
public class ErrorDetails {
    private String errorCode;    // Custom error code
    private String message;
    private String details;

    public ErrorDetails(String errorCode, String message, String details) {
        super();
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }

}
