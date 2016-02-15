package com.dto;

/**
 * Created by root on 14/02/16.
 */
public class RoleResponseDto {
    private String roleGuid;
    private String roleName;

    public String getRoleGuid() {
        return roleGuid;
    }

    public void setRoleGuid(String roleGuid) {
        this.roleGuid = roleGuid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}