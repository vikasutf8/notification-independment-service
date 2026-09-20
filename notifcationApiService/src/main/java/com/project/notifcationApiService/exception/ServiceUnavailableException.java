package com.project.notifcationApiService.exception;

import com.project.notifcationApiService.constant.ErrorCodes;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a service operation cannot be completed.
 * Returns HTTP 503 Service Unavailable status.
 */
public class ServiceUnavailableException extends GlobalException {

    public ServiceUnavailableException(String message) {
        super(ErrorCodes.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE, message);
    }

    public ServiceUnavailableException(String message, Object details) {
        super(ErrorCodes.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE, message, details);
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(ErrorCodes.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE, message, cause);
    }
}