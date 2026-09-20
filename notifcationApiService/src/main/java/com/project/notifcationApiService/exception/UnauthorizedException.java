package com.project.notifcationApiService.exception;

import com.project.notifcationApiService.constant.ErrorCodes;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a request lacks valid authentication credentials.
 * Returns HTTP 401 Unauthorized status.
 */
public class UnauthorizedException extends GlobalException {

    public UnauthorizedException(String message) {
        super(ErrorCodes.UNAUTHORIZED, HttpStatus.UNAUTHORIZED, message);
    }

    public UnauthorizedException(String message, Object details) {
        super(ErrorCodes.UNAUTHORIZED, HttpStatus.UNAUTHORIZED, message, details);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(ErrorCodes.UNAUTHORIZED, HttpStatus.UNAUTHORIZED, message, cause);
    }
}
