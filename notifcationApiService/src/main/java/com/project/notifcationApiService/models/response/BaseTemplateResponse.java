package com.project.notifcationApiService.models.response;

import lombok.Data;

import java.util.List;

@Data
public class BaseTemplateResponse <I,R extends Number>{
    private List<I> data;
    private boolean hasMoreElements;
    private R totalCount;
}
