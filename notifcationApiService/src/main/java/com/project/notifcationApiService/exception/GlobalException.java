package com.project.notifcationApiService.exception;

import com.project.notifcationApiService.utils.commonHelper.ErrorResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
 * Abstract base exception for the Notification API Service.
 * All custom exceptions extend this class for consistent error codes,
 * HTTP status mapping, and standardized error responses.
 *
 * Features:
 * - Encapsulates error code, HTTP status, message, and optional details
 * - Provides factory methods to create ErrorResponse objects
 * - Centralized exception handling with consistent structure
 */

public abstract class GlobalException extends RuntimeException {

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
    private final Object details;

    protected GlobalException(String errorCode, HttpStatus httpStatus, String message, Object details) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.message = message;
        this.details = details;
    }

    protected GlobalException(String errorCode, HttpStatus httpStatus, String message) {
        this(errorCode, httpStatus, message, null);
    }

    protected GlobalException(String errorCode, HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.message = message;
        this.details = null;
    }



    @Override
    public String getMessage() {
        return message;
    }


    public ErrorResponse toErrorResponse() {
        return ErrorResponse.of(httpStatus.value(), message);
    }

    public ErrorResponse toErrorResponse(String customTimestamp) {
        return ErrorResponse.of(httpStatus.value(), message, customTimestamp);
    }

    public HttpStatusCode getHttpStatus() {
        return httpStatus;
    }
}