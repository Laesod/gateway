package com.dto;

import java.util.ArrayList;

/**
 * Created by root on 14/02/16.
 */
public class UpdateUserGroupsRequestDto {
    private String[] groups;
    private GroupRequestDto[] groupsToCreateAndAdd;

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
