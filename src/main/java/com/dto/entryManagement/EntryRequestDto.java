package com.dto.entryManagement;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by root on 01/03/16.
 */
public class EntryRequestDto {
    private String entryGuid;

    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private  String entryTypeGuid;

    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String projectGuid;

    private String description;

    private String[] groups;

    public String getEntryGuid() {
        return entryGuid;
    }

    public void setEntryGuid(String entryGuid) {
        this.entryGuid = entryGuid;
    }

    public String getEntryTypeGuid() {
        return entryTypeGuid;
    }

    public void setEntryTypeGuid(String entryTypeGuid) {
        this.entryTypeGuid = entryTypeGuid;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }
}
