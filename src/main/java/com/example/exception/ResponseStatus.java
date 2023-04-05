package com.example.exception;

import org.springframework.http.HttpStatus;

public class ResponseStatus {
    // System Error
    public static final int SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();
    public static final int VALUE_ERROR = HttpStatus.UNPROCESSABLE_ENTITY.value();
    public static final int KEY_ERROR = HttpStatus.UNPROCESSABLE_ENTITY.value();
    public static final int SERVER_REQUEST_TIMEOUT = HttpStatus.INTERNAL_SERVER_ERROR.value();
    public static final int ACCESS_FORBIDDEN_ERROR = HttpStatus.FORBIDDEN.value();
    public static final int SERVICE_UNAVAILABLE = HttpStatus.SERVICE_UNAVAILABLE.value();
    public static final int ATTRIBUTE_ERROR = HttpStatus.SERVICE_UNAVAILABLE.value();
    // Framework Error
    public static final int DATA_NOT_CREATED = HttpStatus.INTERNAL_SERVER_ERROR.value();
    public static final int DOES_NOT_EXIST = HttpStatus.NOT_FOUND.value();
    public static final int REQUIRED_FIELD = HttpStatus.UNPROCESSABLE_ENTITY.value();
    public static final int REQUEST_FAILED = HttpStatus.BAD_REQUEST.value();
    public static final int JSON_PARSE_ERROR = HttpStatus.UNPROCESSABLE_ENTITY.value();
    public static final int SERIALIZER_ERROR = HttpStatus.UNPROCESSABLE_ENTITY.value();
    public static final int FIELD_ERROR = HttpStatus.BAD_REQUEST.value();
    public static final int VALIDATION_ERROR = HttpStatus.BAD_REQUEST.value();
    public static final int QUERY_EXCEPTION = HttpStatus.INTERNAL_SERVER_ERROR.value();
    public static final int MODEL_NOT_FOUND = HttpStatus.NOT_FOUND.value();
    public static final int PAGE_NOT_FOUND = HttpStatus.NOT_FOUND.value();
    // Custom Error
    // public static final int DATA_NOT_CREATED = HttpStatus.INTERNAL_SERVER_ERROR.value();
    public static final int UPDATE_FAILED = HttpStatus.NOT_MODIFIED.value();
    public static final int DELETE_FAILED = HttpStatus.INTERNAL_SERVER_ERROR.value();
    // public static final int FIELD_ERROR = HttpStatus.BAD_REQUEST.value();
    public static final int OUT_OF_STOCK = HttpStatus.BAD_REQUEST.value();
    public static final int INSUFFICIENT_STOCK = HttpStatus.UNPROCESSABLE_ENTITY.value();
    public static final int COLUMN_NOT_FOUND = HttpStatus.NOT_FOUND.value();
    public static final String SUCCESS = "Success Response";
}

