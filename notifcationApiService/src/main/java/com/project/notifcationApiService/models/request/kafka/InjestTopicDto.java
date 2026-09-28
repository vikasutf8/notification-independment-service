package com.project.notifcationApiService.models.request.kafka;

import com.project.notifcationApiService.models.request.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

import static com.project.notifcationApiService.constant.ErrorMessages.NOTIFICATION_TYPE_REQUIRED;
import static com.project.notifcationApiService.constant.ErrorMessages.TEMPLATE_ID_REQUIRED;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InjestTopicDto {


    private String requestId;
    private String tenantId;
    private Long recivedAt;

    @NotBlank(message = TEMPLATE_ID_REQUIRED)
    private String templateId;
    private Map<String, Object> dynamicVariables;
    @NotNull(message = NOTIFICATION_TYPE_REQUIRED)
    private NotificationType notificationType;
}
