package com.project.notifcationApiService.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

/**
 * Request DTO for creating or updating notification templates.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TemplateRequest {

    @NotBlank(message = "Template name cannot be blank")
    private String name;

    @NotEmpty(message = "Template variables cannot be empty")
    private Map<String, String> tempVariables;

    @NotBlank(message = "Message template cannot be blank")
    private String messageTemplate;
}
