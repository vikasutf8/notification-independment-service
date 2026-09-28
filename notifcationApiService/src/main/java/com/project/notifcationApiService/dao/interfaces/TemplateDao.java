package com.project.notifcationApiService.dao.interfaces;

import com.project.notifcationApiService.models.entity.Template;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

/**
 * DAO interface for Template data access operations.
 * Abstracts database operations from service layer.
 */
public interface TemplateDao {

    /**
     * Save a template to database.
     *
     * @param template the template entity to save
     * @return the saved template with generated id
     */
    Template save(Template template);

    /**
     * Find template by name (case-insensitive) and tenant ID.
     *
     * @param name the template name
     * @param tenantId the tenant UUID
     * @return Optional containing template if found
     */
    Optional<Template> findByNameIgnoreCaseAndTenantId(String name, UUID tenantId);

    Page<Template> filterTemplate(Example<Template> example, PageRequest pageRequest);
}
