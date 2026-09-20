package com.project.notifcationApiService.models.contexts;

import java.util.UUID;

/**
 * Simple immutable record to hold notification context data.
 * Contains only the tenant identifier (UUID) needed across the application.
 */
public record NotificationContext(UUID tenantId) {
}
