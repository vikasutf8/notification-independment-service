package com.project.notifcationApiService.dao.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface CacheService {
    <T> void putById(String tenantId, String Id, T value);

    <T> void putByName(String tenantId, String name, T value);

    void  deleteById(String tenantId, String id);

    void  deleteByName(String tenantId, String name);

    <T> Optional<T> getById(String tenantId, String Id, Class<T> valueType);

    <T> Optional<T> getByName(String tenantId, String name, Class<T> valueType);
}
