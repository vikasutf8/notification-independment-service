package com.project.notifcationApiService.utils.commonHelper;

import java.time.Instant;

/**
 * Standardized error response structure returned to clients.
 * Contains only essential fields: statusCode, message, timestamp.
 */
public record ErrorResponse(
    int statusCode,
    String message,
    String timestamp
) {
    public static ErrorResponse of(int statusCode, String message) {
        return new ErrorResponse(statusCode, message, Instant.now().toString());
    }

    public static ErrorResponse of(int statusCode, String message, String timestamp) {
        return new ErrorResponse(statusCode, message, timestamp);
    }
}