package com.example.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;


    public ResourceNotFoundException(String resourceName) {
        super(String.format("%s not found with %s : '%s'", resourceName)); // Post not found with id : 1
        this.resourceName = resourceName;

    }

    public String getResourceName() {
        return resourceName;
    }


}

/*public class MyCustomException extends RuntimeException {
    private int errorCode;

    public MyCustomException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void myServiceMethod() {
    // ...
    if (someCondition) {
        throw new MyCustomException("My custom error message", 1001);
    }
    // ...
}

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MyCustomException.class)
    public ResponseEntity<ErrorResponse> handleMyCustomException(MyCustomException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

}*/