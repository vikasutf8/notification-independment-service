package com.project.notifcationApiService.utils.commonHelper;

import org.springframework.util.ObjectUtils;

public class UtilsMehtods {

    public static boolean isNotEmpty(final Object obj) {
        return !ObjectUtils.isEmpty(obj);
    }

}
