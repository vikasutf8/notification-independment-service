package com.project.notifcationApiService.bootstrap;


import com.mongodb.client.MongoClient;
import com.project.notifcationApiService.config.beanConfig.ApplicationProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConnectionValidator {

    private final RedisConnectionFactory redisConnectionFactory;
    private final MongoClient mongoClient;
    private final ObjectProvider<KafkaTemplate<String, Object>> kafkaTemplateProvider;
    private final ApplicationProperties applicationProperties;
//    public ConnectionValidator(RedisConnectionFactory redisConnectionFactory) {}


    @Value("${app.pubsub.kafka.bootstrap-servers:}")
    private String kafkaBootstrapServers;


    @PostConstruct
    public  void  init() {
        testMongoConnection();
        testRedisConnection();
        testKafkaConnection();
    }

    public void testRedisConnection() {
        try {
            redisConnectionFactory.getConnection().ping();
            log.info("Redis connection is successful.");
        } catch (Exception e) {
            log.error("Redis connection failed: {}", e.getMessage());
            throw new RuntimeException("Redis connection failed", e);
        }
    }

    public void testKafkaConnection() {
        KafkaTemplate<String, Object> kafkaTemplate = kafkaTemplateProvider.getIfAvailable();
        if (kafkaTemplate == null) {
            log.info("Kafka is disabled, skipping Kafka connection test.");
            return;
        }
        try {
            String topic = applicationProperties.getIngestTopic();
            kafkaTemplate.execute(producer -> {
                producer.partitionsFor(topic);
                return null;
            });
            log.info("Kafka connection is successful (bootstrap-servers='{}', topic='{}').",
                    kafkaBootstrapServers, topic);
        } catch (Exception e) {
            log.error("Kafka connection failed: {}", e.getMessage());
            throw new RuntimeException("Kafka connection failed", e);
        }
    }

    public void testMongoConnection() {
        try {
            mongoClient
                    .getDatabase("admin")
                    .runCommand(new Document("ping", 1));

            log.info("MongoDB connection is successful.");

        } catch (Exception e) {
            log.error("MongoDB connection failed: {}", e.getMessage());

            throw new RuntimeException(
                    "MongoDB connection failed", e
            );
        }
    }
}
