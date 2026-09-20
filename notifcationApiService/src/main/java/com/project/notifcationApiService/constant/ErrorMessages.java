package com.project.notifcationApiService.constant;

/**
 * Centralized error messages for the Notification API Service.
 * All user-facing error messages are defined here for consistency.
 */
public final class ErrorMessages {

    private ErrorMessages() {
        throw new AssertionError("Cannot instantiate ErrorMessages utility class");
    }

    // Authentication & Authorization Errors
    public static final String TENANT_ID_MISSING = "Unauthorized: X-Tenant-Id header is required.";

    // Template Errors
    public static final String TEMPLATE_DUPLICATE_NAME = "Template with name '%s' already exists for tenant '%s'.";
    public static final String TEMPLATE_NOT_FOUND = "Template not found with id: %s";
    public static final String TEMPLATE_EMPTY_FIELDS = "Template request fields cannot be empty.";
    public static final String TEMPLATE_NAME_BLANK = "Template name cannot be blank.";
    public static final String TEMPLATE_VARIABLES_EMPTY = "Template variables cannot be empty.";
    public static final String TEMPLATE_MESSAGE_BLANK = "Message template cannot be blank.";

    // Validation Errors
    public static final String VALIDATION_FAILED = "Validation failed.";
    public static final String INVALID_REQUEST_BODY = "Invalid request body format.";
    public static final String INVALID_REQUEST_FORMAT = "Invalid request format.";
    public static final String MISSING_REQUIRED_FIELD = "Missing required field: %s";

    // Database Errors
    public static final String DATABASE_ERROR = "Database operation failed.";
    public static final String DATABASE_CONNECTION_ERROR = "Failed to connect to database.";
    public static final String DATABASE_QUERY_ERROR = "Database query execution failed.";

    // Service Errors
    public static final String SERVICE_UNAVAILABLE = "Service is currently unavailable. Please try again later.";
    public static final String EXTERNAL_SERVICE_ERROR = "External service call failed.";
    public static final String OPERATION_TIMEOUT = "Operation timed out.";

    // General Errors
    public static final String INTERNAL_SERVER_ERROR = "An unexpected error occurred. Please contact support.";
    public static final String UNKNOWN_ERROR = "An unknown error occurred.";

    // Resource Errors
    public static final String RESOURCE_NOT_FOUND = "Requested resource not found.";
}
