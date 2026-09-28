package com.project.notifcationApiService.services.interfaces;

import com.project.notifcationApiService.models.request.TemplateFilterRequest;
import com.project.notifcationApiService.models.request.TemplateRequest;
import com.project.notifcationApiService.models.response.FilterTemplateResponse;
import com.project.notifcationApiService.models.response.TemplateResponse;

/**
 * Service interface for template operations.
 */
public interface TemplateService {

    /**
     * Create a new template.
     *
     * @param templateRequest the template request DTO
     * @return the created template
     */
    TemplateResponse createTemplate(TemplateRequest templateRequest);

    /**
     * Update an existing template (same payload as create).
     *
     * @param id the template ID string (UUID format) from path variable
     * @param templateRequest the template request DTO
     * @return the updated template
     */
    TemplateResponse updateTemplate(String id, TemplateRequest templateRequest);

    /**
     * Delete a template by id, scoped to current tenant.
     *
     * @param id the template ID string (UUID format) from path variable
     */
    void deleteTemplate(String id);

    FilterTemplateResponse filterTemplates(TemplateFilterRequest templateFilterRequest) throws ReflectiveOperationException;
}
