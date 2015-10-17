package com.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Created by aautushk on 9/19/2015.
 */
public class InvitationRequestDto {
    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    @Pattern(regexp=".+@.+\\.[a-z]+", message = "{NotValidEmailValue}")
    private String recipientEmail;

    private String projectGuid;

    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    @Column(name = "authority")
    private String authority;

    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private List<GroupRequestDto> groups;

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public List<GroupRequestDto> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupRequestDto> groups) {
        this.groups = groups;
    }
}