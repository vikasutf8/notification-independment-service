package com.project.notifcationApiService.services.impl;

import com.project.notifcationApiService.constant.ErrorMessages;
import com.project.notifcationApiService.dao.interfaces.CacheService;
import com.project.notifcationApiService.dao.interfaces.TemplateDao;
import com.project.notifcationApiService.exception.InvalidRequestException;
import com.project.notifcationApiService.exception.ResourceNotFoundException;
import com.project.notifcationApiService.exception.ValidationException;
import com.project.notifcationApiService.models.contexts.NotificationContext;
import com.project.notifcationApiService.models.contexts.NotificationContextHolder;
import com.project.notifcationApiService.models.entity.Template;
import com.project.notifcationApiService.models.request.TemplateFilterRequest;
import com.project.notifcationApiService.models.request.TemplateRequest;
import com.project.notifcationApiService.models.response.FilterTemplateResponse;
import com.project.notifcationApiService.models.response.TemplateResponse;
import com.project.notifcationApiService.services.interfaces.TemplateService;
import com.project.notifcationApiService.utils.commonHelper.UtilsMehtods;
import com.project.notifcationApiService.utils.validator.TemplateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation for template operations.
 * Handles business logic for template creation and management.
 */
@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateDao templateDao;
    private final TemplateValidator templateValidator;


    /**
     * Create a new template for the current tenant.
     * Validates that a template with the same name doesn't already exist (case-insensitive).
     *
     * @param templateRequest the template request DTO containing name, variables, and message
     * @return the created template
     * @throws ValidationException if template with same name already exists for tenant
     */
    @Override
    public TemplateResponse createTemplate(TemplateRequest templateRequest) {
        // Extract tenant ID from context
        var tenantId = UtilsMehtods.getCurrentTenantId();

        // Validate request (presence, sizes, variable keys, placeholder consistency)
        templateValidator.validate(templateRequest);

        // Check for duplicate template name for this tenant (case-insensitive)
        var existingTemplate = templateDao.findByNameIgnoreCaseAndTenantId(
                templateRequest.getName(),
                tenantId
        );

        if (existingTemplate.isPresent()) {
            throw new ValidationException(
                    String.format(ErrorMessages.TEMPLATE_DUPLICATE_NAME,
                            templateRequest.getName(), tenantId),
                    tenantId
            );
        }

        // Build template using builder pattern with default flow status
        Template template = Template.builder()
                .name(templateRequest.getName())
                .templateVariables(templateRequest.getTempVariables())
                .messageTemplate(templateRequest.getMessageTemplate())
                .tenantId(tenantId)
                .id(UUID.randomUUID())
                .build();
        

        // Save to database through DAO
         templateDao.save(template);

         return new TemplateResponse(template);
    }

    /**
     * Update an existing template for the current tenant.
     * 1. Lookup by id + tenantId via DAO — 404 if not found.
     * 2. If name is being changed, check no other template for this tenant
     *    already uses that name (case-insensitive, names are unique per tenant).
     * 3. Apply updates and save.
     *
     * @param id the template ID string (UUID format) from path variable
     * @param templateRequest the template request DTO (same payload as create)
     * @return the updated template
     * @throws ResourceNotFoundException if template with id not found for tenant
     * @throws ValidationException if another template already uses the new name
     */
    @Override
    public TemplateResponse updateTemplate(String id, TemplateRequest templateRequest) {
        // Extract tenant ID from context
        var tenantId = UtilsMehtods.getCurrentTenantId();

        // Template IDs are UUIDs at the DB boundary — reject malformed values early
        validateTemplateId(id);

        // Validate request (presence, sizes, variable keys, placeholder consistency)
        templateValidator.validate(templateRequest);

        // 1. Check template exists for this tenant via id + tenantId
        Template existingTemplate = templateDao.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.TEMPLATE_NOT_FOUND, id)));

        // 2. If name is being changed, check uniqueness per tenant
        if (!existingTemplate.getName().equalsIgnoreCase(templateRequest.getName())) {
            var duplicate = templateDao.findByNameIgnoreCaseAndTenantId(
                    templateRequest.getName(),
                    tenantId
            );

            if (duplicate.isPresent() && !duplicate.get().getId().toString().equals(id)) {
                throw new ValidationException(
                        String.format(ErrorMessages.TEMPLATE_DUPLICATE_NAME,
                                templateRequest.getName(), tenantId),
                        tenantId
                );
            }
        }

        // 3. Apply updates and save
        existingTemplate.setName(templateRequest.getName());
        existingTemplate.setTemplateVariables(templateRequest.getTempVariables());
        existingTemplate.setMessageTemplate(templateRequest.getMessageTemplate());

        templateDao.save(existingTemplate);

        return new TemplateResponse(existingTemplate);
    }

    /**
     * Delete a template for the current tenant.
     * Lookup by id + tenantId via DAO — 404 if not found — then delete.
     *
     * @param id the template ID string (UUID format) from path variable
     * @throws ResourceNotFoundException if template with id not found for tenant
     */
    @Override
    public void deleteTemplate(String id) {
//        var tenantId = UtilsMehtods.getCurrentTenantId();
//
//        Template existingTemplate = templateDao.findByIdAndTenantId(id, tenantId)
//                .orElseThrow(() -> new ResourceNotFoundException(
//                        String.format(ErrorMessages.TEMPLATE_NOT_FOUND, id)));

        validateTemplateId(id);

        templateDao.delete(id,() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.TEMPLATE_NOT_FOUND, id)));
    }

    /**
     * Template IDs are UUIDs at the DB boundary — reject malformed values
     * before any DAO call so callers get 400 instead of a conversion error.
     */
    private void validateTemplateId(String id) {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new InvalidRequestException(
                    String.format(ErrorMessages.TEMPLATE_INVALID_ID, id), ex);
        }
    }

    @Override
    public FilterTemplateResponse filterTemplates(TemplateFilterRequest templateFilterRequest) throws ReflectiveOperationException {
    //ignore tenant based searching
        NotificationContextHolder.ignoreTenantIdInjections();
Page<Template> templates =  templateDao.filterTemplate(templateFilterRequest.buildSearchExample(),
                templateFilterRequest.buildPageRequest());
        List<TemplateResponse> data =templates.stream().map(TemplateResponse::new).collect(Collectors.toList());
        return new FilterTemplateResponse(data,templates.hasNext(), templates.getTotalElements());
    }
}
