package com.dto.userManagement;

/**
 * Created by root on 26/02/16.
 */
public class UpdateUserRolesAndGroupsRequestDto {
    private String[] roles;
    private String[] groups;
    private GroupRequestDto[] groupsToCreateAndAdd;

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public GroupRequestDto[] getGroupsToCreateAndAdd() {
        return groupsToCreateAndAdd;
    }

    public void setGroupsToCreateAndAdd(GroupRequestDto[] groupsToCreateAndAdd) {
        this.groupsToCreateAndAdd = groupsToCreateAndAdd;
    }
}
