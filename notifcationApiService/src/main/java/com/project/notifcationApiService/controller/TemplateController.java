package com.project.notifcationApiService.controller;

import com.project.notifcationApiService.models.request.TemplateRequest;
import com.project.notifcationApiService.models.response.TemplateResponse;
import com.project.notifcationApiService.services.interfaces.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
