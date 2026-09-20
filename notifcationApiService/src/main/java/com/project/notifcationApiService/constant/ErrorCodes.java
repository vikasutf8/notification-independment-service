package com.project.notifcationApiService.constant;

/**
 * Centralized error code definitions for the Notification API Service.
 * Each error code follows the format: CATEGORY_CODE
 * - RESOURCE_NOT_FOUND_XXX for missing resources
 * - VALIDATION_XXX for validation errors
 * - SERVICE_XXX for service-related errors
 * - DATABASE_XXX for database errors
 * - INTERNAL_XXX for internal errors
 */
public final class ErrorCodes {

    private ErrorCodes() {
        throw new AssertionError("Utils class should not be instantiated");
    }

    // Resource Not Found Errors
    public static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";
    public static final String RESOURCE_NOT_FOUND_USER = "RESOURCE_NOT_FOUND_USER";
    public static final String RESOURCE_NOT_FOUND_NOTIFICATION = "RESOURCE_NOT_FOUND_NOTIFICATION";
    public static final String RESOURCE_NOT_FOUND_CHANNEL = "RESOURCE_NOT_FOUND_CHANNEL";

    // Validation Errors
    public static final String VALIDATION_FAILED = "VALIDATION_FAILED";
    public static final String VALIDATION_INVALID_INPUT = "VALIDATION_INVALID_INPUT";
    public static final String VALIDATION_MISSING_FIELD = "VALIDATION_MISSING_FIELD";
    public static final String VALIDATION_INVALID_FORMAT = "VALIDATION_INVALID_FORMAT";

    // Service Unavailability Errors
    public static final String SERVICE_UNAVAILABLE = "SERVICE_UNAVAILABLE";
    public static final String SERVICE_UNAVAILABLE_KAFKA = "SERVICE_UNAVAILABLE_KAFKA";
    public static final String SERVICE_UNAVAILABLE_EXTERNAL = "SERVICE_UNAVAILABLE_EXTERNAL";

    // Database Errors
    public static final String DATABASE_ERROR = "DATABASE_ERROR";
    public static final String DATABASE_CONNECTION_FAILED = "DATABASE_CONNECTION_FAILED";
    public static final String DATABASE_QUERY_TIMEOUT = "DATABASE_QUERY_TIMEOUT";

    // Invalid Request Errors
    public static final String INVALID_REQUEST = "INVALID_REQUEST";
    public static final String INVALID_REQUEST_FORMAT = "INVALID_REQUEST_FORMAT";
    public static final String INVALID_REQUEST_BODY = "INVALID_REQUEST_BODY";

    // Authentication & Authorization Errors
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String FORBIDDEN = "FORBIDDEN";

    // Internal Server Errors
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    public static final String INTERNAL_ERROR_UNEXPECTED = "INTERNAL_ERROR_UNEXPECTED";

}