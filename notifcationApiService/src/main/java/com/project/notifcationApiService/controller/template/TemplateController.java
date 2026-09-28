package com.project.notifcationApiService.controller.template;

import com.project.notifcationApiService.models.request.TemplateFilterRequest;
import com.project.notifcationApiService.models.request.TemplateRequest;
import com.project.notifcationApiService.models.response.FilterTemplateResponse;
import com.project.notifcationApiService.models.response.TemplateResponse;
import com.project.notifcationApiService.services.interfaces.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for template management.
 * Handles template creation, retrieval, and updates.
 */
@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    /**
     * Create a new notification template.
     * Extracts tenant ID from request context.
     * Validates template name uniqueness per tenant.
     *
     * @param templateRequest the template request DTO with name, variables, and message
     * @return ResponseEntity containing the created template response with id and name
     */
    @PostMapping
    public ResponseEntity<TemplateResponse> createTemplate(@Valid @RequestBody TemplateRequest templateRequest) {

        TemplateResponse response = templateService.createTemplate(templateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Update an existing notification template.
     * Uses same request payload as createTemplate.
     * Tenant ID comes from request context, template id from path variable.
     * Validates template exists for tenant, then checks new name uniqueness.
     *
     * @param id the template UUID from path variable
     * @param templateRequest the template request DTO with name, variables, and message
     * @return ResponseEntity containing the updated template response
     */
    @PutMapping("/{id}")
    public ResponseEntity<TemplateResponse> updateTemplate(
            @PathVariable UUID id,
            @Valid @RequestBody TemplateRequest templateRequest) {

        TemplateResponse response = templateService.updateTemplate(id, templateRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a notification template.
     * Tenant ID comes from request context, template id from path variable.
     * Only deletes if template belongs to the current tenant, else 404.
     *
     * @param id the template UUID from path variable
     * @return 204 No Content on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable UUID id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }


    // generic searching api on template Enitity  on name, tenantId
    @GetMapping
    public ResponseEntity<FilterTemplateResponse> filterTemplates(TemplateFilterRequest templateFilterRequest) throws ReflectiveOperationException {
        return ResponseEntity.ok(templateService.filterTemplates(templateFilterRequest));
    }


}
