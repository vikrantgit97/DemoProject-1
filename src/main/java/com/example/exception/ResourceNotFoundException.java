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
