package com.project.notifcationApiService.models.request.kafka;

import com.project.notifcationApiService.models.request.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

import static com.project.notifcationApiService.constant.ErrorMessages.NOTIFICATION_TYPE_REQUIRED;
import static com.project.notifcationApiService.constant.ErrorMessages.TEMPLATE_ID_REQUIRED;



@Data
@RequiredArgsConstructor
@Builder
public class InjestTopicDto {


    private String requestId;
    private String tenantId;
    private Long recivedAt;

    @NotNull(message = TEMPLATE_ID_REQUIRED)
    private UUID templateId;
    private Map<String, Object> dynamicVariables;
    @NotNull(message = NOTIFICATION_TYPE_REQUIRED)
    private NotificationType notificationType;
}
