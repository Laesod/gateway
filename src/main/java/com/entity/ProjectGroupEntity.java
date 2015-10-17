package com.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by aautushk on 9/13/2015.
 */
@Entity
@Table(name = "project_group")
@EntityListeners(AuditingEntityListener.class)
public class ProjectGroupEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "guid")
    private String guid;

    @Column(name = "project_guid")
    private String projectGuid;

    @Column(name = "group_guid")
    private String groupGuid;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getGroupGuid() {
        return groupGuid;
    }

    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }
}
