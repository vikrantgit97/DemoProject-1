package com.example.exception;

public class ErrorCode {
    // System Error
    public static final String SERVER_ERROR = "RA_SYS_ERR_500";
    public static final String VALUE_ERROR = "RA_SYS_ERR_502";
    public static final String KEY_ERROR = "RA_SYS_ERR_503";
    public static final String SERVER_REQUEST_TIMEOUT = "RA_SYS_ERR_504";
    public static final String ACCESS_FORBIDDEN_ERROR = "RA_SYS_ERR_505";
    public static final String SERVICE_UNAVAILABLE = "RA_SYS_ERR_506";
    public static final String ATTRIBUTE_ERROR = "RA_SYS_ERR_507";
    // Framework
    public static final String DATA_NOT_CREATED = "RA_FW_ERR_480";
    public static final String DOES_NOT_EXIST = "RA_FW_ERR_481";
    public static final String REQUIRED_FIELD = "RA_FW_ERR_482";
    public static final String REQUEST_FAILED = "RA_FW_ERR_483";
    public static final String JSON_PARSE_ERROR = "RA_FW_ERR_484";
    public static final String SERIALIZER_ERROR = "RA_FW_ERR_485";
    public static final String FIELD_ERROR = "RA_FW_ERR_486";
    public static final String VALIDATION_ERROR = "RA_FW_ERR_487";
    public static final String QUERY_EXCEPTION = "RA_FW_ERR_488";
    public static final String MODEL_NOT_FOUND = "RA_FW_ERR_489";
    public static final String PAGE_NOT_FOUND = "RA_FW_ERR_490";
    // Custom Error
    // public static final String DATA_NOT_CREATED = "RA_CUST_ERR_601";
    public static final String UPDATE_FAILED = "RA_CUST_ERR_602";
    public static final String DELETE_FAILED = "RA_CUST_ERR_603";
    // public static final String FIELD_ERROR = "RA_CUST_ERR_604";
    public static final String OUT_OF_STOCK = "RAERR_605";
    public static final String INSUFFICIENT_STOCK = "RAERR_606";
    public static final String COLUMN_NOT_FOUND = "RAERR_607";

    public static boolean isSet(String code) {
        try {
            return (String) ErrorCode.class.getField(code).get(null) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
