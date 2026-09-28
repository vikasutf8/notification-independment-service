package com.project.notifcationApiService.config.beanConfig;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ApplicationProperties {

    @Value("${app.pubsub.kafka.ingest-topic}")
    private String ingestTopic;

    @Value("${app.pubsub.kafka.audit-topic}")
    private String auditTopic;
}
