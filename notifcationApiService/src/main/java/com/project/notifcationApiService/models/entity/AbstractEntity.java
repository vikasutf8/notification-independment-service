package com.project.notifcationApiService.models.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

/**
 * Abstract base entity for all MongoDB documents.
 * Provides common fields like id (UUID), createdAt and updatedAt timestamps.
 * Automatically manages timestamps on create and update operations.
 */
@Data
@NoArgsConstructor
public abstract class AbstractEntity {

    @Id
    protected UUID id = UUID.randomUUID();

    protected Long createdAt;

    protected Long updatedAt;

    /**
     * Called before saving new entity to database.
     * Sets createdAt and updatedAt timestamps.
     */
    protected void prePersist() {
        Long currentTime = System.currentTimeMillis();
        if (this.createdAt == null) {
            this.createdAt = currentTime;
        }
        this.updatedAt = currentTime;
    }

    /**
     * Called before updating entity in database.
     * Updates the updatedAt timestamp.
     */
    protected void preUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }
}
