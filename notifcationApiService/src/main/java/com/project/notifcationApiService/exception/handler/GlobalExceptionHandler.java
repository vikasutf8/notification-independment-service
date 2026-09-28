package com.project.notifcationApiService.exception.handler;

import com.project.notifcationApiService.exception.GlobalException;
import com.project.notifcationApiService.utils.commonHelper.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

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
     * Handles missing request context (null tenant/request state).
     * Thrown by context accessors when the auth filter did not establish context.
     *
     * @param exception the null-state exception
     * @param request current web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler({NullPointerException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleNullStateException(
            RuntimeException exception, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Request context is missing: " + exception.getMessage()
        );
        return handleExceptionInternal(
                exception,
                errorResponse,
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    /**
     * Handles bean-validation failures on @Valid request bodies (422).
     *
     * @param exception the validation exception with field errors
     * @param request current web request
     * @return ResponseEntity with field-error details
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        String details = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        ErrorResponse errorResponse = ErrorResponse.of(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation failed: " + details
        );
        return handleExceptionInternal(
                exception,
                errorResponse,
                new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY,
                request
        );
    }

    /**
     * Handles malformed JSON bodies and unparseable enums (400).
     *
     * @param exception the unreadable-message exception
     * @param request current web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleNotReadableException(
            HttpMessageNotReadableException exception, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid request body format."
        );
        return handleExceptionInternal(
                exception,
                errorResponse,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    /**
     * Handles path-variable / request-param type mismatches (400).
     *
     * @param exception the type-mismatch exception
     * @param request current web request
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatchException(
            MethodArgumentTypeMismatchException exception, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid value for '" + exception.getName() + "'."
        );
        return handleExceptionInternal(
                exception,
                errorResponse,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
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