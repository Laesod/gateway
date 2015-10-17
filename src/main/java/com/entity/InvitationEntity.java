package com.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by aautushk on 9/15/2015.
 */
@Entity
@Table(name = "invitations")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class InvitationEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "invitation_guid"   )
    private String invitationGuid;

    @Column(name = "recipient_email")
    private String recipientEmail;

    @Column(name = "project_guid")
    private String projectGuid;

    @Column(name = "authority")
    private String authority;

    @Column(name = "is_invitation_accepted")
    private boolean isInvitationAccepted;

    public String getInvitationGuid() {
        return invitationGuid;
    }

    public void setInvitationGuid(String invitationGuid) {
        this.invitationGuid = invitationGuid;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public boolean isInvitationAccepted() {
        return isInvitationAccepted;
    }

    public void setIsInvitationAccepted(boolean isInvitationAccepted) {
        this.isInvitationAccepted = isInvitationAccepted;
    }
}