package com.project.notifcationApiService.pubsub.primary;

/**
 * Primary pubsub provider contract.
 * Implementations push events to the active messaging backbone
 * (e.g. Kafka, AWS SQS).
 */
public interface GenericProvider {

    /**
     * Publish a message to the given topic/queue.
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
