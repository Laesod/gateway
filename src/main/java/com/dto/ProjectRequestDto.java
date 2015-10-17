package com.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by aautushk on 9/13/2015.
 */
public class ProjectRequestDto {
    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String description;

    private List<GroupRequestDto> groups;

    private List<InvitationRequestDto> invitations;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GroupRequestDto> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupRequestDto> groups) {
        this.groups = groups;
    }

    public List<InvitationRequestDto> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<InvitationRequestDto> invitations) {
        this.invitations = invitations;
    }
}
