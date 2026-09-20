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

    // Error Messages
    public static final String TENANT_ID_MISSING = "Unauthorized: X-Tenant-Id header is required.";
    public static final String INVALID_REQUEST_FORMAT = "Invalid request format.";
}
