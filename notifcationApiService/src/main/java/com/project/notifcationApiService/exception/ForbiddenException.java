package com.project.notifcationApiService.exception;

import com.project.notifcationApiService.constant.ErrorCodes;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when the caller lacks permission for the operation,
 * e.g. missing or malformed tenant identity.
 * Returns HTTP 403 Forbidden status.
 */
public class ForbiddenException extends GlobalException {

    public ForbiddenException(String message) {
        super(ErrorCodes.FORBIDDEN, HttpStatus.FORBIDDEN, message);
    }

    public ForbiddenException(String message, Object details) {
        super(ErrorCodes.FORBIDDEN, HttpStatus.FORBIDDEN, message, details);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(ErrorCodes.FORBIDDEN, HttpStatus.FORBIDDEN, message, cause);
    }
}
