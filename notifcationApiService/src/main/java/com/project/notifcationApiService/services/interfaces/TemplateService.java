package com.project.notifcationApiService.services.interfaces;

import com.project.notifcationApiService.models.entity.Template;
import com.project.notifcationApiService.models.request.TemplateRequest;
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
}
