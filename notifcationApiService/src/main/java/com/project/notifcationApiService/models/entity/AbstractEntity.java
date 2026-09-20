package com.project.notifcationApiService.models.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.UUID;

import static com.project.notifcationApiService.utils.commonHelper.UtilsMehtods.getCurrentTimeInMillis;

/**
 * Abstract base entity for all MongoDB documents.
 * Provides common fields like id (UUID), createdAt and updatedAt timestamps.
 * Automatically manages timestamps on create and update operations.
 */
@Data
@NoArgsConstructor
public abstract class AbstractEntity {



    protected Long createdAt;

    protected Long updatedAt;

    /**
     * Called before saving new entity to database.
     * Sets createdAt and updatedAt timestamps.
     */
    protected void entityCreateAt() {
        Long currentTime = getCurrentTimeInMillis();
        if (this.createdAt == null) {
            this.createdAt = currentTime;
        }
        this.updatedAt = currentTime;
    }

    /**
     * Called before updating entity in database.
     * Updates the updatedAt timestamp.
     */
    protected void entityUpdateAt() {
        this.updatedAt = getCurrentTimeInMillis();
    }
}
