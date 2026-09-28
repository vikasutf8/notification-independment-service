package com.project.notifcationApiService.pubsub.impl;

import com.project.notifcationApiService.pubsub.interfaces.AwsSqsProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Primary AWS SQS provider stub (UAT environment).
 * Real SQS client wiring to be added when UAT work starts;
 * until then it always reports failure so fallbacks can take over.
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "app.pubsub.sqs", name = "enabled", havingValue = "true")
public class AwsSqsProviderImpl implements AwsSqsProvider {

    @Override
    public boolean sendNotification(String topic, Object message) {
        log.warn("AwsSqsProvider not configured in this environment, skipping push to '{}'", topic);
        return false;
    }
}
