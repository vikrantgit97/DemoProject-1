package com.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private boolean success;
    private String message;
    private String error_code;

    public ErrorResponse(String message, String error_code) {
        this.success = false;
        this.message = message;
        this.error_code = error_code;
    }

    public static ResponseEntity<ErrorResponse> error(String message, String error_code) {
        return new ResponseEntity<>(new ErrorResponse(message, error_code), HttpStatus.UNAUTHORIZED);
    }
}

