package com.dto.userManagement;

/*
 * #%L
 * Gateway
 * %%
 * Copyright (C) 2015 Powered by Sergey
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.util.Date;
import java.util.List;

/**
 * Created by aautushk on 9/19/2015.
 */
public class InvitationResponseDto {
    private String invitationGuid;
    private String email;
    private String projectGuid;
    private String projectDescription;

    private String createdBy;
    private Date createdAt;
    private String creatorAvatar;

    List<RoleResponseDto> roles;
    List<GroupResponseDto> groups;

    public String getInvitationGuid() {
        return invitationGuid;
    }

    public void setInvitationGuid(String invitationGuid) {
        this.invitationGuid = invitationGuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
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

    public String getCreatorAvatar() {
        return creatorAvatar;
    }

    public void setCreatorAvatar(String creatorAvatrar) {
        this.creatorAvatar = creatorAvatar;
    }

    public List<RoleResponseDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleResponseDto> roles) {
        this.roles = roles;
    }

    public List<GroupResponseDto> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupResponseDto> groups) {
        this.groups = groups;
    }
}
