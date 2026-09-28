package com.project.notifcationApiService.dao.repositories;

import com.project.notifcationApiService.models.entity.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * MongoDB repository for Template entity.
 * Handles database operations for templates.
 */
@Repository
public interface TemplateRepository extends MongoRepository<Template, UUID> {

    /**
     * Find template by name (case-insensitive) and tenant ID.
     *
     * @param name the template name
     * @param tenantId the tenant UUID
     * @return Optional containing template if found
     */
    Optional<Template> findByNameIgnoreCaseAndTenantId(String name, UUID tenantId);

    /**
     * Find template by id and tenant ID (tenant-scoped lookup for update).
     *
     * @param id the template UUID from path variable
     * @param tenantId the tenant UUID
     * @return Optional containing template if found
     */
    Optional<Template> findByIdAndTenantId(UUID id, UUID tenantId);
}
