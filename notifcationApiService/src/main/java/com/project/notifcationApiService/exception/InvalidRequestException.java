package com.project.notifcationApiService.exception;

import com.project.notifcationApiService.constant.ErrorCodes;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when the client sends an invalid request.
 * Returns HTTP 400 Bad Request status.
 */
public class InvalidRequestException extends GlobalException {

    public InvalidRequestException(String message) {
        super(ErrorCodes.INVALID_REQUEST, HttpStatus.BAD_REQUEST, message);
    }

    public InvalidRequestException(String message, Object details) {
        super(ErrorCodes.INVALID_REQUEST, HttpStatus.BAD_REQUEST, message, details);
    }

    public InvalidRequestException(String message, Throwable cause) {
        super(ErrorCodes.INVALID_REQUEST, HttpStatus.BAD_REQUEST, message, cause);
    }
}