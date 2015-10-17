package com.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by aautushk on 9/15/2015.
 */
public class GroupRequestDto {
    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String groupGuid;

    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupGuid() {
        return groupGuid;
    }

    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }
}
