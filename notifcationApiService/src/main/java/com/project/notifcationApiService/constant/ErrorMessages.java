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
    public static final String TENANT_ID_MISSING = "Forbidden: X-Tenant-Id header is required.";

    // Template Errors
    public static final String TEMPLATE_DUPLICATE_NAME = "Template with name '%s' already exists for tenant '%s'.";
    public static final String TEMPLATE_NOT_FOUND = "Template not found with id: %s";
    public static final String TEMPLATE_EMPTY_FIELDS = "Template request fields cannot be empty.";
    public static final String TEMPLATE_NAME_BLANK = "Template name cannot be blank.";
    public static final String TEMPLATE_VARIABLES_EMPTY = "Template variables cannot be empty.";
    public static final String TEMPLATE_MESSAGE_BLANK = "Message template cannot be blank.";
    public static final String TEMPLATE_NAME_SIZE = "Template name must be between 3 and 100 characters.";
    public static final String TEMPLATE_VARIABLES_MAX = "Template cannot declare more than 100 variables.";
    public static final String TEMPLATE_VARIABLE_KEY_INVALID = "Invalid template variable name '%s'. Use letters, digits and underscore (max 100 characters).";
    public static final String TEMPLATE_VARIABLE_VALUE_BLANK = "Value for template variable '%s' cannot be blank.";
    public static final String TEMPLATE_VARIABLE_VALUE_TOO_LONG = "Value for template variable '%s' exceeds 5120 characters.";
    public static final String TEMPLATE_MESSAGE_SIZE = "Message template must be between 10KB and 5MB.";
    public static final String TEMPLATE_UNDECLARED_VARIABLE = "Message template uses undeclared variable '%s'. Declare it in template variables first.";
    public static final String TEMPLATE_UNUSED_VARIABLE = "Template variable '%s' is declared but not used in the message template.";
    public static final String TEMPLATE_ID_REQUIRED  = "Template ID cannot be blank.";
    public static final String TEMPLATE_ID_INVALID = "Template ID must be a valid UUID.";
    public static final String TEMPLATE_INVALID_ID = "Template ID '%s' is not a valid UUID.";

    // Notification Errors
    public static final String NOTIFICATION_TYPE_REQUIRED = "Notification type is required.";
    public static final String NOTIFICATION_MISSING_VARIABLES = "Missing values for template variables: %s";
    public static final String NOTIFICATION_VARIABLE_NOT_SCALAR = "Value for variable '%s' must be a string, number or boolean.";

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
