package com.project.notifcationApiService.config.beanConfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ApplicationProperties {

    @Value("${app.pubsub.kafka.topics.ingest-topic}")
    private String ingestTopic;

    @Value("${app.pubsub.kafka.topics.audit-topic}")
    private String auditTopic;

    @Value("${app.pubsub.kafka.send-timeout-ms}")
    private long sendTimeoutMs;


}
