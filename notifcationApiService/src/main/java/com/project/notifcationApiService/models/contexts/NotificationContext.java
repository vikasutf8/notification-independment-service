package com.project.notifcationApiService.models.contexts;

/**
 * Simple immutable record to hold notification context data.
 * Tenant and request identifiers are carried as Strings;
 * UUID is only used at generation time and at the DB boundary.
 */
public record NotificationContext(String tenantId, String requestId, boolean ignoreTenantIdInjections) {
}
