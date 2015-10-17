package com.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by aautushk on 9/15/2015.
 */
@Entity
@Table(name = "invitation_group")
@EntityListeners(AuditingEntityListener.class)
public class InvitationGroupEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "guid")
    private String guid;

    @Column(name = "invitation_guid")
    private String invitationGuid;

    @Column(name = "group_guid")
    private String groupGuid;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getInvitationGuid() {
        return invitationGuid;
    }

    public void setInvitationGuid(String invitationGuid) {
        this.invitationGuid = invitationGuid;
    }

    public String getGroupGuid() {
        return groupGuid;
    }

    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }
}
