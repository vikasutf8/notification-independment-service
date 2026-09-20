package com.project.notifcationApiService.exception;

import com.project.notifcationApiService.constant.ErrorCodes;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a requested resource cannot be found.
 * Returns HTTP 404 Not Found status.
 */
public class ResourceNotFoundException extends GlobalException {

    public ResourceNotFoundException(String message) {
        super(ErrorCodes.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND, message);
    }

    public ResourceNotFoundException(String message, Object details) {
        super(ErrorCodes.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND, message, details);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(
            ErrorCodes.RESOURCE_NOT_FOUND,
            HttpStatus.NOT_FOUND,
            String.format("Resource %s with %s '%s' not found.", resourceName, fieldName, fieldValue)
        );
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(ErrorCodes.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND, message, cause);
    }
}