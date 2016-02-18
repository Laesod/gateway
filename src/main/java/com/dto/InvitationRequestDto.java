package com.dto;

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


import org.hibernate.validator.constraints.NotEmpty;

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
    private String email;

    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String projectGuid;

    private String[] rolesToAdd;

    private String[] groupsToAdd;

    private List<GroupRequestDto> groupsToCreateAndAdd;

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

    public String[] getRolesToAdd() {
        return rolesToAdd;
    }

    public void setRolesToAdd(String[] rolesToAdd) {
        this.rolesToAdd = rolesToAdd;
    }

    public String[] getGroupsToAdd() {
        return groupsToAdd;
    }

    public void setGroupsToAdd(String[] groupsToAdd) {
        this.groupsToAdd = groupsToAdd;
    }

    public List<GroupRequestDto> getGroupsToCreateAndAdd() {
        return groupsToCreateAndAdd;
    }

    public void setGroupsToCreateAndAdd(List<GroupRequestDto> groupsToCreateAndAdd) {
        this.groupsToCreateAndAdd = groupsToCreateAndAdd;
    }
}