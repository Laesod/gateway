package com.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by aautushk on 9/15/2015.
 */
@Entity
@Table(name = "groups")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class GroupEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "group_guid"   )
    private String groupGuid;


    @Column(name = "group_name"   )
    private String groupName;

    @ManyToOne
    @JoinColumn(name="project_guid")
    private ProjectEntity project;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_groups", joinColumns = { @JoinColumn(name = "group_guid") }, inverseJoinColumns = { @JoinColumn(name = "username") })
    private Set<UserEntity> users;

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

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}
