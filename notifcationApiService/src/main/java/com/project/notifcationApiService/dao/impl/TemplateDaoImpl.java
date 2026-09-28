package com.project.notifcationApiService.dao.impl;

import com.project.notifcationApiService.dao.interfaces.TemplateDao;
import com.project.notifcationApiService.dao.repositories.TemplateRepository;
import com.project.notifcationApiService.models.entity.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * DAO implementation for Template entity.
 * Delegates database operations to TemplateRepository.
 * Repository is kept private within DAO layer - not exposed to service layer.
 */
@Repository
@RequiredArgsConstructor
public class TemplateDaoImpl implements TemplateDao {

    private final TemplateRepository templateRepository;

    /**
     * Save a template to database.
     *
     * @param template the template entity to save
     * @return the saved template with generated id
     */
    @Override
    public Template save(Template template) {
        return templateRepository.save(template);
    }

    /**
     * Find template by name (case-insensitive) and tenant ID.
     *
     * @param name the template name
     * @param tenantId the tenant UUID
     * @return Optional containing template if found
     */
    @Override
    public Optional<Template> findByNameIgnoreCaseAndTenantId(String name, UUID tenantId) {
        return templateRepository.findByNameIgnoreCaseAndTenantId(name, tenantId);
    }

    @Override
    public Optional<Template> findByIdAndTenantId(UUID id, UUID tenantId) {
        return templateRepository.findByIdAndTenantId(id, tenantId);
    }

    @Override
    public void delete(Template template) {
        templateRepository.delete(template);
    }

    @Override
    public Page<Template> filterTemplate(final Example<Template> example, final PageRequest pageRequest) {
        return templateRepository.findAll(example, pageRequest);
    }
}
