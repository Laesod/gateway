package com.utils;

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


import com.entity.ProjectEntity;
import com.entity.RoleEntity;
import com.entity.UserEntity;
import com.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * Created by aautushk on 9/12/2015.
 */
public class PermissionsValidator {
    private SecurityContextReader securityContextReader = new SecurityContextReader();

    @Autowired
    public IUserRepository userRepository;

    public Boolean rolesForProjectCheck(UserEntity user, String projectGuid, String[] roles) {
        Boolean isProjectAssignementValid = false;
        Boolean isRoleAssignementValid = false;

        for (ProjectEntity project : user.getProjects()) {
            if(project.getProjectGuid().equals(projectGuid)){
                isProjectAssignementValid = true;
            }
        }

        for (RoleEntity role : user.getRoles()) {
            if(role.getProject().getProjectGuid().equals(projectGuid) && Arrays.asList(roles).contains(role.getRoleName())){
                isRoleAssignementValid = true;
            }
        }

        return (isProjectAssignementValid && isRoleAssignementValid);
    }

    public Boolean projectCheck(UserEntity user, String projectGuid) {
        Boolean isProjectAssignementValid = false;

        for (ProjectEntity project : user.getProjects()) {
            if(project.getProjectGuid().equals(projectGuid)){
                isProjectAssignementValid = true;
            }
        }

        return isProjectAssignementValid;
    }
}