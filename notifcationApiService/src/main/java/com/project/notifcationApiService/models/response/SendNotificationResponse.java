package com.project.notifcationApiService.models.response;

import com.project.notifcationApiService.models.request.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Response DTO for send-notification API responses.
 * Contains the template reference and the resolved message.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendNotificationResponse {

    private UUID templateId;

    private String templateName;

    private NotificationType notificationType;

    private String message;
}
