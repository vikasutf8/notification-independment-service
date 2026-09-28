package com.project.notifcationApiService.models.request;

import com.project.notifcationApiService.constant.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Map;

/**
 * Request DTO for creating or updating notification templates.
 */
@Data
@RequiredArgsConstructor
@ToString
public class TemplateRequest {

    @NotBlank(message = ErrorMessages.TEMPLATE_NAME_BLANK)
    @Size(min = 3, max = 100, message = ErrorMessages.TEMPLATE_NAME_SIZE)
    private String name;

    @NotEmpty(message = ErrorMessages.TEMPLATE_VARIABLES_EMPTY)
    @Size(max = 100, message = ErrorMessages.TEMPLATE_VARIABLES_MAX)
    private Map<String, String> tempVariables;

    @NotBlank(message = ErrorMessages.TEMPLATE_MESSAGE_BLANK)
    @Size(min = 10240, max = 5242880, message = ErrorMessages.TEMPLATE_MESSAGE_SIZE)
    private String messageTemplate;


}
