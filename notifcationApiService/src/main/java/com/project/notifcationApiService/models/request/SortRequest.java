package com.project.notifcationApiService.models.request;



public record SortRequest(
        String sortKey,
        SortType sortType
) {
}
