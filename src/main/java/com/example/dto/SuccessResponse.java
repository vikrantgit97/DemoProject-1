package com.example.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// Define a response object for successful requests
public class SuccessResponse {
    private boolean success;
    private String message;
    private Object data;

    public SuccessResponse(String message, Object data) {
        this.success = true;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    // Create a ResponseEntity object with a success status code (200 OK) and the SuccessResponse object
    public static ResponseEntity<SuccessResponse> ok(String message, Object data) {
        return new ResponseEntity<>(new SuccessResponse(message, data), HttpStatus.OK);
    }
}

// Use the SuccessResponse object to generate a successful response
//return SuccessResponse.ok("otp sent successfully",Map.of("otp","9286"));


