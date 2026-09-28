package com.project.notifcationApiService.pubsub.fallback;

/**
 * Fallback pubsub provider contract.
 * Implementations handle events that primary providers failed to push
 * (e.g. RabbitMQ, MQTT).
 */
public interface GenericFallback {

    /**
     * Publish a message to the given topic/queue as a fallback.
     *
     * @param topic the destination topic or queue name
     * @param message the payload to publish
     * @return true if the event was pushed successfully, false otherwise
     */
    boolean sendNotification(String topic, Object message);

    /**
     * Provider name used for logging.
     *
     * @return simple class name by default
     */
    default String getProviderName() {
        return getClass().getSimpleName();
    }
}
