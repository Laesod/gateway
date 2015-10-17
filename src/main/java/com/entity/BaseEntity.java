package com.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by aautushk on 9/12/2015.
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {
    @Column(name = "created_by_user", nullable = false)
    @CreatedBy
    private String createdByUser;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "modified_by_user", nullable = false)
    @LastModifiedBy
    private String modifiedByUser;

    @Column(name = "modified_at", nullable = false)
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @Column(name = "version", nullable = false)
    @Version
    private Long version;

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedByUser() {
        return modifiedByUser;
    }

    public void setModifiedByUser(String modifiedByUser) {
        this.modifiedByUser = modifiedByUser;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
