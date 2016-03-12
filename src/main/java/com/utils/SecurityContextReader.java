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


import com.dto.userManagement.GroupResponseDto;
import com.dto.userManagement.RoleResponseDto;
import com.entity.userManagement.GroupEntity;
import com.entity.userManagement.UserEntity;
import com.repository.userManagement.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ShellProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by aautushk on 9/20/2015.
 */
@Component
public abstract class SecurityContextReader {
    public static UserEntity userEntity;
    //public static String username;

    public static UserEntity getUserEntity(IUserRepository userRepository) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getName().equals("anonymousUser")){
            userEntity = null;
            return null;
        }else{
            if(userEntity != null){
                return userEntity;
            }else{
                userEntity = userRepository.findByUsername(authentication.getName());
                return userEntity;
            }
        }
    }

    public static void clear(){
        userEntity = null;
    }
}
