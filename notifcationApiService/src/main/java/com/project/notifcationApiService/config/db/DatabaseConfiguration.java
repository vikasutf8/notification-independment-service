package com.project.notifcationApiService.config.db;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class DatabaseConfiguration {

    @Value("${app.database.mongodb.uri}")
    private String mongoUri;

    @Value("${app.database.mongodb.database}")
    private String databaseName;

    @Value("${app.database.mongodb.connect-timeout-ms}")
    private int connectTimeoutMs;

    @Value("${app.database.mongodb.read-timeout-ms}")
    private int readTimeoutMs;

    @Value("${app.database.mongodb.retry-writes}")
    private boolean retryWrites;

    @Value("${app.database.mongodb.retry-reads}")
    private boolean retryReads;

    @Bean
    public MongoClient mongoClient() {

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoUri))

                // Connection and read timeout
                .applyToSocketSettings(builder -> builder
                        .connectTimeout(connectTimeoutMs, TimeUnit.MILLISECONDS)
                        .readTimeout(readTimeoutMs, TimeUnit.MILLISECONDS)
                )

                // Retry failed eligible operations
                .retryWrites(retryWrites)
                .retryReads(retryReads)

                .build();

        return MongoClients.create(settings);
    }

    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient) {
        return mongoClient.getDatabase(databaseName);
    }
}