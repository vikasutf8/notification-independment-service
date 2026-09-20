package com.project.notifcationApiService.exception;

import com.project.notifcationApiService.constant.ErrorCodes;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a database operation fails.
 * Returns HTTP 500 Internal Server Error status.
 */
public class DatabaseException extends GlobalException {

    public DatabaseException(String message) {
        super(ErrorCodes.DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public DatabaseException(String message, Object details) {
        super(ErrorCodes.DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, message, details);
    }

    public DatabaseException(String message, Throwable cause) {
        super(ErrorCodes.DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
    }
}