package com.dto;

import java.security.Principal;

/**
 * Created by aautushk on 12/17/2015.
 */
public class UserProfileDto {
    private Principal user;
    private String avatarS3ObjectKey;

    public Principal getUser() {
        return user;
    }

    public void setUser(Principal user) {
        this.user = user;
    }

    public String getAvatarS3ObjectKey() {
        return avatarS3ObjectKey;
    }

    public void setAvatarS3ObjectKey(String avatarS3ObjectKey) {
        this.avatarS3ObjectKey = avatarS3ObjectKey;
    }
}
