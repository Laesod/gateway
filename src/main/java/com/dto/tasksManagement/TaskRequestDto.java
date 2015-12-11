package com.dto.tasksManagement;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by aautushk on 12/3/2015.
 */
public class TaskRequestDto {

    @NotNull(message = "{FieldCanNotBeEmpty}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String project;

    @NotNull(message ="{FieldCanNotBeEmpty}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String type;

    @NotNull(message = "{FieldCanNotBeEmpty}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String status;

    @NotNull(message = "{FieldCanNotBeEmpty}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String description;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}