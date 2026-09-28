package com.project.notifcationApiService.models.request;

import lombok.Getter;

@Getter
public enum SortType {

    ASCENDING("asc"),
    DESCENDING("desc");

    private final String value;

    SortType(final String value) {
        this.value = value;
    }

}
