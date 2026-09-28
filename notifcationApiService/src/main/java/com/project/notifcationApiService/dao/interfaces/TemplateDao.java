package com.project.notifcationApiService.dao.interfaces;

import com.project.notifcationApiService.models.entity.Template;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.function.Supplier;

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
     * @param tenantId the tenant ID string
     * @return Optional containing template if found
     */
    Optional<Template> findByNameIgnoreCaseAndTenantId(String name, String tenantId);

    /**
     * Find template by id and tenant ID.
     *
     * @param id the template ID string (UUID format)
     * @param tenantId the tenant ID string
     * @return Optional containing template if found
     */
    Optional<Template> findByIdAndTenantId(String id, String tenantId);

    /**
     * Delete a template from database.
     *
     * @param id the template ID string (UUID format)
     * @param exceptionSupplier supplies the exception when nothing is found
     */
    void delete(String id, final Supplier<? extends Throwable> exceptionSupplier);

    Page<Template> filterTemplate(Example<Template> example, PageRequest pageRequest);
}
