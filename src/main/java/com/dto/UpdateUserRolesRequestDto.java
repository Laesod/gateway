package com.dto;

import java.util.ArrayList;

/**
 * Created by root on 14/02/16.
 */
public class UpdateUserRolesRequestDto {
    private String[] roles;

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
