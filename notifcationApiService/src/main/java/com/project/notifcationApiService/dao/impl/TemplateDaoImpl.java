package com.project.notifcationApiService.dao.impl;

import com.project.notifcationApiService.dao.interfaces.CacheService;
import com.project.notifcationApiService.dao.interfaces.TemplateDao;
import com.project.notifcationApiService.dao.repositories.TemplateRepository;
import com.project.notifcationApiService.models.entity.Template;
import com.project.notifcationApiService.utils.commonHelper.UtilsMehtods;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * DAO implementation for Template entity.
 * Cache-aside via CacheService (Redis hash per tenant):
 * reads check cache first and populate on miss, writes refresh cache entries.
 * Repository is kept private within DAO layer - not exposed to service layer.
 */
@Repository
@RequiredArgsConstructor
public class TemplateDaoImpl implements TemplateDao {
    private final CacheService cacheService;
    private final TemplateRepository templateRepository;

    /**
     * Save a template to database and refresh its cache entries.
     *
     * @param template the template entity to save
     * @return the saved template with generated id
     */
    @Override
    public Template save(Template template) {
        Template saved = templateRepository.save(template);
        cacheService.putById(saved.getTenantId(), saved.getId().toString(), saved);
        cacheService.putByName(saved.getTenantId(), normalize(saved.getName()), saved);
        return saved;
    }

    /**
     * Find template by name (case-insensitive) and tenant ID.
     * Cache-aside: cache key uses lower-cased name to match
     * case-insensitive semantics.
     *
     * @param name the template name
     * @param tenantId the tenant ID string
     * @return Optional containing template if found
     */
    @Override
    public Optional<Template> findByNameIgnoreCaseAndTenantId(String name, String tenantId) {
        String nameKey = normalize(name);

        Optional<Template> cached = cacheService.getByName(tenantId, nameKey, Template.class);
        if (cached.isPresent()) {
            return cached;
        }

        Optional<Template> fromDb = templateRepository.findByNameIgnoreCaseAndTenantId(name, tenantId);
        fromDb.ifPresent(template -> {
            cacheService.putByName(tenantId, nameKey, template);
            cacheService.putById(tenantId, template.getId().toString(), template);
        });
        return fromDb;
    }

    /**
     * Find template by id and tenant ID.
     * Cache-aside: populates both id and name entries on miss.
     * UUID conversion happens here only, at the DB boundary.
     *
     * @param id the template ID string (UUID format)
     * @param tenantId the tenant ID string
     * @return Optional containing template if found
     */
    @Override
    public Optional<Template> findByIdAndTenantId(String id, String tenantId) {
        Optional<Template> cached = cacheService.getById(tenantId, id, Template.class);
        if (cached.isPresent()) {
            return cached;
        }

        Optional<Template> fromDb = templateRepository.findByIdAndTenantId(UUID.fromString(id), tenantId);
        fromDb.ifPresent(template -> {
            cacheService.putById(tenantId, id, template);
            cacheService.putByName(tenantId, normalize(template.getName()), template);
        });
        return fromDb;
    }

    /**
     * Delete a template from database and evict its cache entries.
     *
     * @param id the template ID string (UUID format)
     * @param exceptionSupplier supplies the exception when nothing is found
     */
    @Override
    public void delete(String id, final Supplier<? extends Throwable> exceptionSupplier ) {
            //to delete from cache ...we need to fetch the template first to get its name and tenantId
        findByIdAndTenantId(id, UtilsMehtods.getCurrentTenantId()).ifPresentOrElse(template -> {
            cacheService.deleteById(template.getTenantId(), template.getId().toString());
            cacheService.deleteByName(template.getTenantId(), normalize(template.getName()));
        },()->{
            if(UtilsMehtods.isNotEmpty(exceptionSupplier)){
                exceptionSupplier.get();
            }
        });

        templateRepository.deleteById(UUID.fromString(id));
        // Note: We don't have the full template here, so we can't evict the cache entries.
        // This is a limitation of the current implementation.
    }

    @Override
    public Page<Template> filterTemplate(final Example<Template> example, final PageRequest pageRequest) {
        return templateRepository.findAll(example, pageRequest);
    }

    private String normalize(String name) {
        return name == null ? null : name.toLowerCase(Locale.ROOT);
    }
}
