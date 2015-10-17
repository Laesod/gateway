package com.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by aautushk on 9/13/2015.
 */
public class ProjectGroupRequestDto {
    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String projectGuid;

    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private List<String> groups;

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }
}
