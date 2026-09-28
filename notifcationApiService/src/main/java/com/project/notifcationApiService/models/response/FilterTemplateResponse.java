package com.project.notifcationApiService.models.response;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class FilterTemplateResponse extends BaseTemplateResponse<TemplateResponse, Long>{


    public FilterTemplateResponse(final List<TemplateResponse> list, final boolean hasMoreElement, final long totalCount) {
        super.setData(list);
        super.setHasMoreElements(hasMoreElement);
        super.setTotalCount(totalCount);
    }
}
