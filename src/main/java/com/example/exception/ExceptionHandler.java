package com.example.exception;

import com.example.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse("Resource Not Found", "RESTAPI_ERR_04");
        log.error(resourceNotFoundException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        ErrorResponse errorResponse = new ErrorResponse(illegalArgumentException.getMessage(), "RESTAPI_ERR_04");
        log.error(illegalArgumentException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<ErrorResponse> handleNumberFormatException(NumberFormatException numberFormatException) {
        ErrorResponse ErrorResponse = new ErrorResponse("Invalid format", "RESTAPI_ERR_03");
        log.error(numberFormatException.getMessage());
        return new ResponseEntity<>(ErrorResponse, HttpStatus.NOT_FOUND);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException runtimeException) {
        ErrorResponse ErrorResponse = new ErrorResponse(runtimeException.getMessage(), "RESTAPI_ERR_02");
        log.error(runtimeException.getMessage());
        return new ResponseEntity<>(ErrorResponse, HttpStatus.NOT_FOUND);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public static ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse ErrorResponse = new ErrorResponse(exception.getMessage(), "RESTAPI_ERR_01");
        log.error(exception.getMessage());
        return new ResponseEntity<>(ErrorResponse, HttpStatus.NOT_FOUND);
    }
}
