package com.dto;

import java.util.ArrayList;

/**
 * Created by root on 14/02/16.
 */
public class UpdateUserRolesRequestDto {
    private String[] rolesToRemove;
    private String[] rolesToAdd;

    public String[] getRolesToRemove() {
        return rolesToRemove;
    }

    public void setRolesToRemove(String[] rolesToRemove) {
        this.rolesToRemove = rolesToRemove;
    }

    public String[] getRolesToAdd() {
        return rolesToAdd;
    }

    public void setRolesToAdd(String[] rolesToAdd) {
        this.rolesToAdd = rolesToAdd;
    }
}
