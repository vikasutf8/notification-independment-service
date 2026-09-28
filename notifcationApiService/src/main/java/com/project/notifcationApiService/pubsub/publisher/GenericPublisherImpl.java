package com.project.notifcationApiService.pubsub.publisher;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.notifcationApiService.config.beanConfig.ApplicationProperties;
import com.project.notifcationApiService.exception.ServiceUnavailableException;
import com.project.notifcationApiService.pubsub.fallback.GenericFallback;
import com.project.notifcationApiService.pubsub.primary.GenericProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Generic publisher implementation with first-success-wins dispatch.
 * Tries each enabled primary provider in order until one returns true;
 * only if all primaries fail (or none are enabled) does it try
 * each fallback provider in order.
 */
@Slf4j
@Component
public class GenericPublisherImpl implements GenericPublisher {

    private final List<GenericProvider> providers;
    private final List<GenericFallback> fallbacks;
    private final ObjectMapper mapper;
    private final ApplicationProperties applicationProperties;

    public GenericPublisherImpl(ObjectProvider<List<GenericProvider>> providers,
                                ObjectProvider<List<GenericFallback>> fallbacks,
                                ObjectMapper mapper,
                                ApplicationProperties applicationProperties) {
        this.providers = providers.getIfAvailable(Collections::emptyList);
        this.fallbacks = fallbacks.getIfAvailable(Collections::emptyList);
        this.mapper = mapper;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void sendDataToInjest(final Object message) {
        // Implementation for sending data to injest

        sendNotification(applicationProperties.getIngestTopic(), convertDataIntoString(message));
    }

    @Override
    public void sendDataToAudit(final Object message) {
        // Implementation for sending data to audit

        sendNotification(applicationProperties.getAuditTopic(), convertDataIntoString(message));

    }

    public String convertDataIntoString(Object message) {
        try {
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
                throw new ServiceUnavailableException("Failed to convert message to JSON string: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean sendNotification(String topic, Object message) {
        for (GenericProvider provider : providers) {
            try {
                if (provider.sendNotification(topic, message)) {
                    log.info("Event pushed to '{}' via primary {}", topic, provider.getProviderName());
                    return true;
                }
                log.warn("Primary {} failed to push event to '{}', trying next", provider.getProviderName(), topic);
            } catch (Exception ex) {
                log.error("Primary {} threw while pushing event to '{}': {}",
                        provider.getProviderName(), topic, ex.getMessage());
            }
        }

        for (GenericFallback fallback : fallbacks) {
            try {
                if (fallback.sendNotification(topic, message)) {
                    log.info("Event pushed to '{}' via fallback {}", topic, fallback.getProviderName());
                    return true;
                }
                log.warn("Fallback {} failed to push event to '{}', trying next", fallback.getProviderName(), topic);
            } catch (Exception ex) {
                log.error("Fallback {} threw while pushing event to '{}': {}",
                        fallback.getProviderName(), topic, ex.getMessage());
            }
        }

        log.error("All providers failed to push event to '{}'", topic);
        return false;
    }

    /*
    AtomicBoolean is useful when multiple threads need to access or update the same boolean atomically. In your method, the providers are called sequentially in the same thread, so atomic operations provide no benefit.

One important distinction:
if you later make provider calls asynchronous or parallel, you will need to reconsider the coordination logic. Simply adding an AtomicBoolean would not, by itself, make the entire publishing flow thread-safe.
Multiple threads calling your current method: Keep the original code.

Multiple threads sharing a success flag: Use an atomic variable only if shared state is genuinely required.

Parallel provider execution: Use a proper concurrency mechanism to coordinate primary completion and fallback execution.
     */
    public boolean sendNotificationAtomic(String topic, Object message) {

        AtomicBoolean atomicResult = new AtomicBoolean(false);

        // 1. Try primary providers
        for (GenericProvider provider : providers) {
            try {
                if (provider.sendNotification(topic, message)) {
                    log.info("Event pushed to '{}' via primary {}",
                            topic, provider.getProviderName());

                    atomicResult.set(true);
                    break;
                }

                log.warn("Primary {} failed to push event to '{}'",
                        provider.getProviderName(), topic);

            } catch (Exception ex) {
                log.error("Primary {} threw while pushing event to '{}': {}",
                        provider.getProviderName(), topic, ex.getMessage());
            }
        }

        // 2. If all primary providers failed, try fallbacks
        if (!atomicResult.get()) {

            for (GenericFallback fallback : fallbacks) {
                try {
                    if (fallback.sendNotification(topic, message)) {
                        log.info("Event pushed to '{}' via fallback {}",
                                topic, fallback.getProviderName());

                        atomicResult.set(true);
                        break;
                    }

                    log.warn("Fallback {} failed to push event to '{}'",
                            fallback.getProviderName(), topic);

                } catch (Exception ex) {
                    log.error("Fallback {} threw while pushing event to '{}': {}",
                            fallback.getProviderName(), topic, ex.getMessage());
                }
            }
        }

        if (!atomicResult.get()) {
            log.error("All providers failed to push event to '{}'", topic);
        }

        return atomicResult.get();
    }
}
