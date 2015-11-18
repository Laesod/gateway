package com.entity;

/*
 * #%L
 * Gateway
 * %%
 * Copyright (C) 2015 Powered by Sergey
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


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