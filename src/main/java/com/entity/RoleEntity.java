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
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class RoleEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "role_guid"   )
    private String roleGuid;

    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "role_guid") }, inverseJoinColumns = { @JoinColumn(name = "username") })
    private Set<UserEntity> users;

    public String getRoleName() {
        return roleName;
    }

    @ManyToOne
    @JoinColumn(name="project_guid")

    private ProjectEntity project;

    public String getRoleGuid() {
        return roleGuid;
    }

    public void setRoleGuid(String roleGuid) {
        this.roleGuid = roleGuid;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }
}