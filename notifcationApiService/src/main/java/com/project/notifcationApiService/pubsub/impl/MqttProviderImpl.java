package com.project.notifcationApiService.pubsub.impl;

import com.project.notifcationApiService.pubsub.interfaces.MqttProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Fallback MQTT provider stub (UAT environment).
 * Real MQTT client wiring to be added when UAT work starts;
 * until then it always reports failure.
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "app.pubsub.mqtt", name = "enabled", havingValue = "true")
public class MqttProviderImpl implements MqttProvider {

    @Override
    public boolean sendNotification(String topic, Object message) {
        log.warn("MqttProvider not configured in this environment, skipping push to '{}'", topic);
        return false;
    }
}
