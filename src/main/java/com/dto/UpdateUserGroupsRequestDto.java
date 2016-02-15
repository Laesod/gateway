package com.dto;

import java.util.ArrayList;

/**
 * Created by root on 14/02/16.
 */
public class UpdateUserGroupsRequestDto {
    private String[] groupsToRemove;
    private String[] groupsToAdd;

    private GroupRequestDto[] groupsToCreateAndAdd;

    public String[] getGroupsToRemove() {
        return groupsToRemove;
    }

    public void setGroupsToRemove(String[] groupsToRemove) {
        this.groupsToRemove = groupsToRemove;
    }

    public String[] getGroupsToAdd() {
        return groupsToAdd;
    }

    public void setGroupsToAdd(String[] groupsToAdd) {
        this.groupsToAdd = groupsToAdd;
    }

    public GroupRequestDto[] getGroupsToCreateAndAdd() {
        return groupsToCreateAndAdd;
    }

    public void setGroupsToCreateAndAdd(GroupRequestDto[] groupsToCreateAndAdd) {
        this.groupsToCreateAndAdd = groupsToCreateAndAdd;
    }
}
