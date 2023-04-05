package com.example.exception;

import com.example.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class Handler extends ResponseEntityExceptionHandler {

    /**
     * A list of the exception types that are not reported.
     */
    protected List<Class<? extends Throwable>> dontReport = Arrays.asList();

    /**
     * A list of the inputs that are never flashed to the session on validation exceptions.
     */
    protected List<String> dontFlash = Arrays.asList(
            "current_password",
            "password",
            "password_confirmation"
    );

    /**
     * Handle exceptions and return a ResponseEntity with error details.
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleException(Throwable ex) {
        // Log the exception if it is not in the don't report list
        if (!dontReport.contains(ex.getClass())) {
            logger.error(ex.getMessage(), ex);
        }
        // Return a ResponseEntity with error details
        ErrorResponse errorResponse = new ErrorResponse("Something went wrong", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
