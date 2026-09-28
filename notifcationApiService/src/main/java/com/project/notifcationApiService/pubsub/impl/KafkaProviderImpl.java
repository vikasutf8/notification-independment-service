package com.project.notifcationApiService.pubsub.impl;

import com.project.notifcationApiService.pubsub.interfaces.KafkaProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Primary Kafka provider implementation (dev environment).
 * Activated via app.pubsub.kafka.enabled=true.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app.pubsub.kafka", name = "enabled", havingValue = "true")
public class KafkaProviderImpl implements KafkaProvider {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.pubsub.kafka.send-timeout-ms:5000}")
    private long sendTimeoutMs;

    @Override
    public boolean sendNotification(String topic, Object message) {
        try {
            kafkaTemplate.send(topic, message).get(sendTimeoutMs, TimeUnit.MILLISECONDS);
            log.info("Event pushed to kafka topic '{}' via {}", topic, getProviderName());
            return true;
        } catch (Exception ex) {
            log.error("Failed to push event to kafka topic '{}' via {}: {}",
                    topic, getProviderName(), ex.getMessage());
            return false;
        }
    }
}
