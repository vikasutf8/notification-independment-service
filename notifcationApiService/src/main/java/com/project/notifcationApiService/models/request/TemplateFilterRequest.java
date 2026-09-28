package com.project.notifcationApiService.models.request;


import com.project.notifcationApiService.models.entity.Template;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public  class TemplateFilterRequest extends BaseSearchDto<Template> {

    // user only enter the name of the template to filter the templates else show all data
    @NotBlank(message = "Template name cannot be blank")
    private String name;

    @Override
    public Class<Template> getEntity() {
        return Template.class;
    }
}
