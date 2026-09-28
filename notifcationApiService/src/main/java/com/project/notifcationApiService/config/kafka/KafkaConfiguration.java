package com.project.notifcationApiService.config.kafka;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "app.pubsub.kafka", name = "enabled", havingValue = "true")
public class KafkaConfiguration {

    @Value("${app.pubsub.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${app.pubsub.kafka.retries}")
    private int retries;

    @Value("${app.pubsub.kafka.retry-backoff-ms}")
    private long retryBackoffMs;

    @Value("${app.pubsub.kafka.acks}")
    private String acks;

    @Bean
    public ProducerFactory<String, Object> kafkaProducerFactory() {

        Map<String, Object> props = new HashMap<>();

        // Broker addresses
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // Retry failed eligible sends
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, retryBackoffMs);

        // Durability guarantee
        props.put(ProducerConfig.ACKS_CONFIG, acks);

        // Key as string, value as JSON
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> kafkaProducerFactory) {
        return new KafkaTemplate<>(kafkaProducerFactory);
    }
}
