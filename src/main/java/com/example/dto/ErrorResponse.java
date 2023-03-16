package com.example.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// Define a response object for error requests
public class ErrorResponse {
    private boolean success;
    private String message;
    private int error_code;

    public ErrorResponse(String message, int error_code) {
        this.success = false;
        this.message = message;
        this.error_code = error_code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getError_code() {
        return error_code;
    }

    // Create a ResponseEntity object with an error status code and the ErrorResponse object
    public static ResponseEntity<ErrorResponse> error(String message, int error_code) {
        return new ResponseEntity<>(new ErrorResponse(message, error_code), HttpStatus.UNAUTHORIZED);
    }
}


// Use the ErrorResponse object to generate an error response
//return ErrorResponse.error("Invalid email or password", 1308);
