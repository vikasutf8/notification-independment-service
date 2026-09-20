package com.project.notifcationApiService.models.contexts;

/**
 * Simple immutable record to hold notification context data.
 * Contains only the tenant identifier needed across the application.
 */
public record NotificationContext(String tenantId) {
}
