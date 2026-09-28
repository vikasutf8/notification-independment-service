package com.project.notifcationApiService.pubsub.publisher;

/**
 * Generic publisher contract.
 * Routes events to enabled primary providers first,
 * then to fallback providers on failure.
 */
public interface GenericPublisher {

    void sendDataToInjest(Object message);

    void sendDataToAudit(Object message);

    /**
     * Publish an event to the given topic.
     *
     * @param topic the destination topic or queue name
     * @param message the payload to publish
     * @return true if any provider pushed the event successfully, false otherwise
     */
    boolean sendNotification(String topic, Object message);
}
