package com.project.notifcationApiService.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

import static com.project.notifcationApiService.constant.ErrorMessages.NOTIFICATION_TYPE_REQUIRED;
import static com.project.notifcationApiService.constant.ErrorMessages.TEMPLATE_ID_REQUIRED;

/**
 * Request DTO for sending a notification based on a stored template.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationRequest {

    @NotNull(message = TEMPLATE_ID_REQUIRED)
    private UUID templateId;
    private Map<String, Object> dynamicVariables;
    @NotNull(message = NOTIFICATION_TYPE_REQUIRED)
    private NotificationType notificationType;
}
