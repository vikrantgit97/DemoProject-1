package com.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class SuccessResponse {
    private boolean success;
    private String message;
    private Object data;

    public SuccessResponse(String message, Object data) {
        this.success = true;
        this.message = message;
        this.data = data;
    }

    public static ResponseEntity<SuccessResponse> ok(String message, Object data) {
        return new ResponseEntity<>(new SuccessResponse(message, data), HttpStatus.OK);
    }
}




