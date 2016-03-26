package com.dto;

import java.util.Date;

/**
 * Created by root on 26/03/16.
 */
public class CommentResponseDto {
    private String commentGuid;
    private String parentEntityGuid;

    private String parentEntity;

    private  String message;

    private String createdBy;
    private Date createdAt;
    private String creatorAvatar;

    public String getCommentGuid() {
        return commentGuid;
    }

    public void setCommentGuid(String commentGuid) {
        this.commentGuid = commentGuid;
    }

    public String getParentEntityGuid() {
        return parentEntityGuid;
    }

    public void setParentEntityGuid(String parentEntityGuid) {
        this.parentEntityGuid = parentEntityGuid;
    }

    public String getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(String parentEntity) {
        this.parentEntity = parentEntity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatorAvatar() {
        return creatorAvatar;
    }

    public void setCreatorAvatar(String creatorAvatar) {
        this.creatorAvatar = creatorAvatar;
    }
}
