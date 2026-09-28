package com.project.notifcationApiService.services.interfaces;

import com.project.notifcationApiService.models.request.SendNotificationRequest;
import com.project.notifcationApiService.models.response.SendNotificationResponse;

/**
 * Service interface for notification operations.
 */
public interface NotificationService {

    /**
     * Send a notification using a stored template.
     * Looks up the template scoped to the current tenant, validates and
     * resolves dynamic variables into the message template.
     *
     * @param request the send notification request DTO
     * @return the resolved notification response
     */
    SendNotificationResponse sendNotification(SendNotificationRequest request);
}
