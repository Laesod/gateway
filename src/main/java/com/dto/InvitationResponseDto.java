package com.dto;

import java.util.Date;

/**
 * Created by aautushk on 9/19/2015.
 */
public class InvitationResponseDto {
    private String invtitationGuid;
    private String projectDescription;

    private String createdBy;
    private Date createdAt;


    public String getInvtitationGuid() {
        return invtitationGuid;
    }

    public void setInvtitationGuid(String invtitationGuid) {
        this.invtitationGuid = invtitationGuid;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
