package com.project.notifcationApiService.utils.commonHelper;

import com.project.notifcationApiService.models.contexts.NotificationContextHolder;
import org.slf4j.MDC;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

import static com.project.notifcationApiService.constant.ApplicationConstants.REQUEST_ID_HEADER;

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

    public static UUID randomGenerateUUID() {
        return UUID.randomUUID();
    }

    public static String getRequestIDContext(){
//        response.setHeader(REQUEST_ID_HEADER, String.valueOf(requestId));//TODO
        return MDC.get(REQUEST_ID_HEADER);
    }
}
