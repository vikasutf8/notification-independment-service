package com.project.notifcationApiService.models.response;

import com.project.notifcationApiService.models.entity.Template;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

/**
 * Response DTO for template API responses.
 * Contains only essential template information: id and name.
 * Supports multiple constructor overloads for flexible usage.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateResponse {

    private String id;

    private String name;

    //make something optional
    private Map<String, String> templateVariables;

    private String messageTemplate;

    /**
     * Constructor that extracts id and name from Template entity.
     *
     * @param template the Template entity
     */
    public TemplateResponse(Template template) {
        setId(template.getId().toString());
        setName(template.getName());
    }

    //includes

}
