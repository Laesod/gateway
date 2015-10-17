package com.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by aautushk on 9/13/2015.
 */
@Entity
@Table(name = "project_user")
@EntityListeners(AuditingEntityListener.class)
public class ProjectUserEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "guid")
    private String guid;

    @Column(name = "project_guid")
    private String projectGuid;

    @Column(name = "username")
    private String username;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }
}
