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
import javax.validation.constraints.Size;

/**
 * Created by aautushk on 8/30/2015.
 */
public class UserRequestDto {

    @NotNull(message = "{FieldCanNotBeEmpty}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    @Pattern(regexp=".+@.+\\.[a-z]+", message = "{NotValidEmailValue}")
    private String username;

    @NotNull(message ="{FieldCanNotBeEmpty}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String firstName;

    @NotNull(message = "{FieldCanNotBeEmpty}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String lastName;

    @NotNull(message = "{FieldCanNotBeEmpty}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    @Size(min = 6, message =  "{WrongPasswordLength}")
    private String password;

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String value){
        this.username = value;
    }

    public String getFirstName(){
        return this.firstName;
    }
    public void setFirstName(String value){
        this.firstName = value;
    }

    public String getLastName(){
        return this.lastName;
    }
    public void setLastName(String value){
        this.lastName = value;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String value){
        this.password = value;
    }
}
