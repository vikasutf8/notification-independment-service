package com.project.notifcationApiService.models.request;

import com.project.notifcationApiService.exception.ValidationException;
import com.project.notifcationApiService.utils.commonHelper.UtilsMehtods;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static java.util.Optional.ofNullable;

public abstract class BaseSearchDto<T> {


    @Min(value = 0, message = "Page must be >= 0")
    private int page = 0;

    @Min(value = 1, message = "Page size must be >= 1")
    @Max(value = 100, message = "Page size must be <= 100")
    private int size = 20;

    private SortRequest sortRequest;
// generic pagenation fucntion what ever key ...is only ..updateAt is hardcord should present in entiity
    public PageRequest buildPageRequest() {

        return ofNullable(sortRequest)
                .filter(request ->
                        UtilsMehtods.isNotEmpty(request.sortKey())
                                && request.sortType() != null
                )
                .filter(request -> {
                    try {
                        this.getClass()
                                .getDeclaredField(request.sortKey());

                        return true;

                    } catch (NoSuchFieldException e) {
                        throw new ValidationException(
                                "Invalid sort key: " + request.sortKey()
                        );
                    }
                })
                .map(request -> PageRequest.of(
                        page,
                        size,
                        Sort.by(
                                Sort.Direction.fromString(
                                        request.sortType().getValue()
                                ),
                                request.sortKey()
                        )
                ))
                .orElseGet(() ->
                        PageRequest.of(
                                page,
                                size,
                                Sort.by(Sort.Direction.DESC, "updatedAt")
                        )
                );
    }

    public Example<T> buildSearchExample()
            throws ReflectiveOperationException {

        try {
            Class<T> entityClass = getEntity();

            // Create a new entity instance
            T instance = entityClass.getDeclaredConstructor().newInstance();

            //also filter tenantId ==context fieldS
            injectTenantId(instance);

            // Read fields from the filter DTO
            for (Field field : this.getClass().getDeclaredFields()) {

                // Ignore static fields
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                field.setAccessible(true);

                Object value = field.get(this);

                // Skip null or empty values
                if (!UtilsMehtods.isNotEmpty(value)) {
                    continue;
                }

                try {
                    // Find the corresponding entity field
//                Field entityField =
//                        entityClass.getDeclaredField(field.getName());

                    Field entityField = findField(entityClass, field.getName());

                    entityField.setAccessible(true);

                    // Copy the filter value into the entity
                    entityField.set(instance, value);

                } catch (NoSuchFieldException e) {
                    // Filter DTO field doesn't exist in the entity.
                    // Ignore it.
                }
            }

            ExampleMatcher matcher = ExampleMatcher.matchingAll()
                    .withIgnoreNullValues()
                    .withIgnoreCase();

            return Example.of(instance, matcher);
            // Build the Example using the newly populated entity
        } catch (Exception e) {
            throw new ValidationException("Error while builling search result", e);
        }
    }

    private void injectTenantId(Object instance) throws NoSuchFieldException {
        // how to get --get field
            Field tenantIdField = findField(instance.getClass(), "tenantId");
            tenantIdField.setAccessible(true);
            try {
                var tenantId = UtilsMehtods.getCurrentTenantId();
                //getting from context
                tenantIdField.set(instance, tenantId);
            } catch (IllegalAccessException e) {
                // Ignore
                throw new ValidationException("Error while setting tenantId: " + e.getMessage());
            }
    }

    private Field findField(Class<?> clazz, String name)
            throws NoSuchFieldException {

        Class<?> current = clazz;

        while (current != null) {
            try {
                return current.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }

        throw new NoSuchFieldException(name);
    }

    // find which entity we perform searching on
    public abstract Class<T> getEntity();
}
