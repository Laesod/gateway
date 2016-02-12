package com.dto;

import java.util.List;

/**
 * Created by aautushk on 2/12/2016.
 */
public class UserProjectResponseDto {
    private String projectGuid;
    private String projectDescription;
    private List<String> roles;

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
