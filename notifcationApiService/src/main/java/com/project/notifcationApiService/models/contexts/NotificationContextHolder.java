package com.project.notifcationApiService.models.contexts;

import java.util.UUID;

/**
 * Context holder for managing NotificationContext using ThreadLocal.
 * Provides static utility methods to store, retrieve, and clear context data.
 *
 * ThreadLocal ensures thread isolation for each request without synchronization overhead.
 * IMPORTANT: clearContext() must be called to prevent ThreadLocal memory leaks.
 */
public class NotificationContextHolder {

    private static final ThreadLocal<NotificationContext> CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    private NotificationContextHolder() {
        throw new AssertionError("Cannot instantiate NotificationContextHolder utility class");
    }

    /**
     * Set the context with a NotificationContext object.
     *
     * @param context the NotificationContext object containing tenantId
     */
    public static void setContext(NotificationContext context) {
        CONTEXT_THREAD_LOCAL.set(context);
    }

    /**
     * Get the current context from ThreadLocal.
     *
     * @return the NotificationContext object or null if not set
     */
    public static NotificationContext getContext() {
        return CONTEXT_THREAD_LOCAL.get();
    }

    /**
     * Clear the current context.
     * IMPORTANT: Call this method at the end of request processing to prevent memory leaks.
     */
    public static void clearContext() {
        CONTEXT_THREAD_LOCAL.remove();
    }

    public static void ignoreTenantIdInjections() {
        UUID tenantId = CONTEXT_THREAD_LOCAL.get().tenantId();
        CONTEXT_THREAD_LOCAL.set(new NotificationContext(tenantId, true));

    }

    public static void ignoreTenantIdInjections(final boolean ignoreTenantId) {
        UUID tenantId = CONTEXT_THREAD_LOCAL.get().tenantId();
        CONTEXT_THREAD_LOCAL.set(new NotificationContext(tenantId, ignoreTenantId));

    }
}
