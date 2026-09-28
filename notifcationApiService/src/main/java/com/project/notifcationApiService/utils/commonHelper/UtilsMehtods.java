package com.project.notifcationApiService.utils.commonHelper;

import com.project.notifcationApiService.models.contexts.NotificationContextHolder;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

public class UtilsMehtods {

    private UtilsMehtods() {
        throw new AssertionError("Cannot instantiate UtilsMehtods utility class");
    }

    public static boolean isNotEmpty(final Object obj) {
        return !ObjectUtils.isEmpty(obj);
    }

    public static boolean isEmpty(final Object obj) {
        return ObjectUtils.isEmpty(obj);
    }

    public static long getCurrentTimeInMillis() {
        return System.currentTimeMillis();
    }

    public static UUID getCurrentTenantId() {
        var context = NotificationContextHolder.getContext();
        return context.tenantId();
    }
}
