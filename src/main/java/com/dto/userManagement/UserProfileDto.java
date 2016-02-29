package com.dto.userManagement;

import java.util.List;

/**
 * Created by aautushk on 12/17/2015.
 */
public class UserProfileDto {
    private String username;
    private String firstName;
    private String lastName;
    private List<UserProjectResponseDto> userProjects;

    private String avatarS3ObjectKey;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<UserProjectResponseDto> getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(List<UserProjectResponseDto> userProjects) {
        this.userProjects = userProjects;
    }

    public String getAvatarS3ObjectKey() {
        return avatarS3ObjectKey;
    }

    public void setAvatarS3ObjectKey(String avatarS3ObjectKey) {
        this.avatarS3ObjectKey = avatarS3ObjectKey;
    }
}
