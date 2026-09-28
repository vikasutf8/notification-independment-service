package com.project.notifcationApiService.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.notifcationApiService.dao.interfaces.CacheService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.project.notifcationApiService.constant.ApplicationConstants.TEMPLATES_REDIS_PREFIX;

@Service
@RequiredArgsConstructor
class CacheServiceImpl implements CacheService {

    private final RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper;

    // key [tenantId] --> value[Name,templates]
    //key[tenantId] -->value[id,templates]

    private String getTenantCacheKey(final  String tenantId) {

        return TEMPLATES_REDIS_PREFIX.concat(tenantId);

    }

    @Override
    public <T> void putById(final String tenantId, String Id, T value){
        put(tenantId,"BY_ID".concat(Id),value);
    }

    @Override
    public <T> void putByName(final String tenantId, String name, T value){
        put(tenantId,"BY_NAME".concat(name),value);
    }


    private  <T> void put(final String tenantId, String hashkey, T template) {
       try {
           String json= objectMapper.writeValueAsString(template);
           redisTemplate.opsForHash().put(getTenantCacheKey(tenantId),hashkey,json);
       }catch (JsonProcessingException e) {
           throw new ValidationException("Failed to convert template to JSON string: " + e.getMessage(), e);
       }
    }

    @Override
    public void  deleteById(final String tenantId, String id){
        delete(tenantId,"BY_ID".concat(id));
    }
    @Override
    public void  deleteByName(final String tenantId, String name){
        delete(tenantId,"BY_NAME".concat(name));
    }

    private void delete(final  String tenantId,String hashkey){
        redisTemplate.opsForHash().delete(getTenantCacheKey(tenantId),hashkey);
    }

    @Override
    public <T> Optional<T> getById(final String tenantId, String Id, Class<T> valueType) {
        return get(tenantId,"BY_ID".concat(Id),valueType);
    }

    @Override
    public <T> Optional<T> getByName(final String tenantId, String name, Class<T> valueType) {
        return get(tenantId,"BY_NAME".concat(name),valueType);
    }

    private <T> Optional<T> get(final String tenantId, String hashkey, Class<T> valueType) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        String json = hashOps.get(getTenantCacheKey(tenantId), hashkey);
        if (json == null) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(objectMapper.readValue(json, valueType));
        } catch (JsonProcessingException e) {
            throw new ValidationException("Failed to convert JSON string to object: " + e.getMessage(), e);
        }
    }
}
