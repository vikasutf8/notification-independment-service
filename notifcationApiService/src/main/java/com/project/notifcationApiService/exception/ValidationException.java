package com.project.notifcationApiService.exception;

import com.project.notifcationApiService.constant.ErrorCodes;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when input validation fails.
 * Returns HTTP 422 Unprocessable Entity status.
 */
public class ValidationException extends GlobalException {

    public ValidationException(String message) {
        super(ErrorCodes.VALIDATION_FAILED, HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

    public ValidationException(String message, Object details) {
        super(ErrorCodes.VALIDATION_FAILED, HttpStatus.UNPROCESSABLE_ENTITY, message, details);
    }

    public ValidationException(String message, Throwable cause) {
        super(ErrorCodes.VALIDATION_FAILED, HttpStatus.UNPROCESSABLE_ENTITY, message, cause);
    }
}