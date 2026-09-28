package com.project.notifcationApiService.models.request;

import com.project.notifcationApiService.models.entity.Template;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Map;

/**
 * Request DTO for creating or updating notification templates.
 */
@Data
@RequiredArgsConstructor
@ToString
public class TemplateRequest {

    @NotBlank(message = "Template name cannot be blank")
    private String name;

    @NotEmpty(message = "Template variables cannot be empty")
    private Map<String, String> tempVariables;

    @NotBlank(message = "Message template cannot be blank")
    private String messageTemplate;


}
