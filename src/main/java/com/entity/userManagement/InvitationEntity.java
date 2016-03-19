package com.entity.userManagement;

import com.entity.BaseEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

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
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "invitation_guid")
    private String invitationGuid;

    @Column(name = "email")
    private String email;

    @Column(name = "is_accepted")
    private boolean isAccepted;

    @Column(name = "is_declined")
    private boolean isDeclined;

    @ManyToOne
    @JoinColumn(name = "project_guid")

    private ProjectEntity project;

    @ManyToMany
    @JoinTable(name = "invitations_roles", joinColumns = {@JoinColumn(name = "invitation_guid")}, inverseJoinColumns = {@JoinColumn(name = "role_guid")})
    private Set<RoleEntity> roles;

    @ManyToMany
    @JoinTable(name = "invitations_groups", joinColumns = {@JoinColumn(name = "invitation_guid")}, inverseJoinColumns = {@JoinColumn(name = "group_guid")})
    private Set<GroupEntity> groups;

    public String getInvitationGuid() {
        return invitationGuid;
    }

    public void setInvitationGuid(String invitationGuid) {
        this.invitationGuid = invitationGuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public boolean isDeclined() {
        return isDeclined;
    }

    public void setDeclined(boolean declined) {
        isDeclined = declined;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public Set<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupEntity> groups) {
        this.groups = groups;
    }
}