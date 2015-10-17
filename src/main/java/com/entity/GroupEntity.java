package com.entity;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by aautushk on 9/15/2015.
 */
@Entity
@Table(name = "groups")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class GroupEntity extends BaseEntity {
    @Id
    @Column(name = "group_guid"   )
    private String groupGuid;

    @Column(name = "group_name"   )
    private String groupName;

    public String getGroupGuid() {
        return groupGuid;
    }

    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
