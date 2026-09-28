package com.project.notifcationApiService.pubsub.impl;

import com.project.notifcationApiService.pubsub.interfaces.RabbitMqProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Fallback RabbitMQ provider stub (UAT environment).
 * Real AMQP client wiring to be added when UAT work starts;
 * until then it always reports failure.
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "app.pubsub.rabbitmq", name = "enabled", havingValue = "true")
public class RabbitMqProviderImpl implements RabbitMqProvider {

    @Override
    public boolean sendNotification(String topic, Object message) {
        log.warn("RabbitMqProvider not configured in this environment, skipping push to '{}'", topic);
        return false;
    }
}
