package com.project.notifcationApiService.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

import static com.project.notifcationApiService.constant.ErrorMessages.NOTIFICATION_TYPE_REQUIRED;
import static com.project.notifcationApiService.constant.ErrorMessages.TEMPLATE_ID_INVALID;
import static com.project.notifcationApiService.constant.ErrorMessages.TEMPLATE_ID_REQUIRED;

/**
 * Request DTO for sending a notification based on a stored template.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationRequest {

    @NotBlank(message = TEMPLATE_ID_REQUIRED)
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = TEMPLATE_ID_INVALID)
    private String templateId;
    private Map<String, Object> dynamicVariables;
    @NotNull(message = NOTIFICATION_TYPE_REQUIRED)
    private NotificationType notificationType;
}
