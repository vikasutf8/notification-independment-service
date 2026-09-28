package com.project.notifcationApiService.constant;

/**
 * Application-wide constants for API configuration and headers.
 */
public final class ApplicationConstants {

    private ApplicationConstants() {
        throw new AssertionError("Cannot instantiate ApplicationConstants utility class");
    }

    // API Paths
    public static final String API_PREFIX = "/api";

    // HTTP Headers
    public static final String TENANT_ID_HEADER = "X-Tenant-Id";
    public  static final String REQUEST_ID_HEADER = "X-Request-Id";

    // Error Messages
    public static final String TENANT_ID_MISSING = "Forbidden: X-Tenant-Id header is required.";
    public static final String TENANT_ID_INVALID = "Forbidden: X-Tenant-Id must be a valid UUID.";
    public static final String INVALID_REQUEST_FORMAT = "Invalid request format.";

    public static final String TEMPLATES_REDIS_PREFIX = "templates";
}
