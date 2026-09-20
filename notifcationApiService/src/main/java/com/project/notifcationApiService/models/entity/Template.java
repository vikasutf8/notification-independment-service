package com.project.notifcationApiService.models.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.UUID;

/**
 * Template entity for storing notification templates in MongoDB.
 * Each template is stored per tenant and contains template variables and message format.
 */
@Document(collection = "templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Template extends AbstractEntity {

    @NotBlank(message = "Template name cannot be blank")
    private String name;

    @NotEmpty(message = "Template variables cannot be empty")
    private Map<String, String> templateVariables;

    @NotBlank(message = "Message template cannot be blank")
    private String messageTemplate;

    @NotNull(message = "Tenant ID cannot be null")
    private UUID tenantId;
}
