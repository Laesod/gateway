package com.dto;

import java.util.List;

/**
 * Created by root on 06/02/16.
 */
public class ProjectUserResponseDto {
    private String username;
    private String firstName;
    private String lastName;
    private List<RoleResponseDto> roles;

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

    public List<RoleResponseDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleResponseDto> roles) {
        this.roles = roles;
    }
}