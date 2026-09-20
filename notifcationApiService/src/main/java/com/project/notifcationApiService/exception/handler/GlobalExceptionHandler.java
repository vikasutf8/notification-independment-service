package com.project.notifcationApiService.exception.handler;

import com.project.notifcationApiService.exception.GlobalException;
import com.project.notifcationApiService.utils.commonHelper.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler for the Notification API Service.
 * Handles all custom exceptions and provides standardized error responses.
 *
 * Design Benefits:
 * - Single generic handler for all GlobalException subclasses
 * - Eliminates duplicate handler methods for each exception type
 * - All exception types carry their own error code and HTTP status
 * - Centralized common error handling logic
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Generic handler for all GlobalException subclasses.
     * Centralizes error response handling for custom exceptions.
     * This single method handles: ResourceNotFoundException, InvalidRequestException,
     * ServiceUnavailableException, DatabaseException, ValidationException, etc.
     *
     * @param exception the global exception with errorCode and httpStatus
     * @param request current web request
     * @return ResponseEntity with standardized error response
     */
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<Object> handleGlobalException(
            GlobalException exception, WebRequest request) {
        ErrorResponse errorResponse = exception.toErrorResponse();
        return handleExceptionInternal(
                exception,
                errorResponse,
                new HttpHeaders(),
                exception.getHttpStatus(),
                request
        );
    }

    /**
     * Handles any unexpected exception not covered by specific handlers (500).
     * Catches all exceptions not handled by specific handlers.
     *
     * @param exception the generic exception
     * @param request current web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(
            Exception exception, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred."
        );
        return handleExceptionInternal(
                exception,
                errorResponse,
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }
}