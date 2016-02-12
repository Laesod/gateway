package com.entity;

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
    @Column(name = "role_name"   )
    private String roleName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "role_name") }, inverseJoinColumns = { @JoinColumn(name = "username") })
    private Set<UserEntity> users;

//    @OneToOne
//    @JoinColumn(name = "translationMapGuid")
//    private TranslationMapEntity translationMap;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

//    public TranslationMapEntity getTranslationMap() {
//        return translationMap;
//    }
//
//    public void setTranslationMap(TranslationMapEntity translationMap) {
//        this.translationMap = translationMap;
//    }


    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}