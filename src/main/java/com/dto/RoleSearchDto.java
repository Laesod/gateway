package com.dto;

/**
 * Created by root on 14/02/16.
 */
public class RoleSearchDto {
    private String nameContains;
    private RoleResponseDto[] currentRoles;

    public String getNameContains() {
        return nameContains;
    }

    public void setNameContains(String nameContains) {
        this.nameContains = nameContains;
    }

    public RoleResponseDto[] getCurrentRoles() {
        return currentRoles;
    }

    public void setCurrentRoles(RoleResponseDto[] currentRoles) {
        this.currentRoles = currentRoles;
    }
}