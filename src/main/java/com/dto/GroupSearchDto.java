package com.dto;

/**
 * Created by root on 14/02/16.
 */
public class GroupSearchDto {
    private String nameContains;
    private GroupResponseDto[] currentGroups;

    public String getNameContains() {
        return nameContains;
    }

    public void setNameContains(String nameContains) {
        this.nameContains = nameContains;
    }

    public GroupResponseDto[] getCurrentGroups() {
        return currentGroups;
    }

    public void setCurrentGroups(GroupResponseDto[] currentGroups) {
        this.currentGroups = currentGroups;
    }
}