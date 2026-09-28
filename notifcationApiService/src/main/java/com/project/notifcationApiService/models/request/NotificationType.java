package com.project.notifcationApiService.models.request;

import lombok.Getter;

@Getter
public enum NotificationType {
    SMS("sms"),
    EMAIL("email"),
    PUSH("push"),
    WEBHOOK("webhook");


    private final String value;

    NotificationType(final String value) {
        this.value = value;
    }

}
