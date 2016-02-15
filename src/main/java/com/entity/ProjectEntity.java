package com.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by aautushk on 9/7/2015.
 */
@Entity
@Table(name = "projects")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class ProjectEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "project_guid"   )
    private String projectGuid;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_projects", joinColumns = { @JoinColumn(name = "project_guid") }, inverseJoinColumns = { @JoinColumn(name = "username") })
    private Set<UserEntity> users;

    @OneToOne
    @JoinColumn(name = "translationMapGuid")
    private TranslationMapEntity translationMap;

    @OneToMany(mappedBy="project")
    private Set<GroupEntity> groups;

    @OneToMany(mappedBy="project")
    private Set<RoleEntity> roles;

    @OneToMany(mappedBy="project")
    private Set<InvitationEntity> invitations;

    public String getProjectGuid() {
        return projectGuid;
    }
    public void setProjectGuid(String value){
        this.projectGuid = value;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public TranslationMapEntity getTranslationMap() {
        return translationMap;
    }

    public void setTranslationMap(TranslationMapEntity translationMap) {
        this.translationMap = translationMap;
    }

    public Set<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupEntity> groups) {
        this.groups = groups;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public Set<InvitationEntity> getInvitations() {
        return invitations;
    }

    public void setInvitations(Set<InvitationEntity> invitations) {
        this.invitations = invitations;
    }
}
