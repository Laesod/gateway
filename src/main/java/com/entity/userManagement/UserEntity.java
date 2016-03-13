package com.entity.userManagement;

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


import com.entity.BaseEntity;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by aautushk on 8/30/2015.
 */
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class UserEntity extends BaseEntity {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "email_verification_token")
    private String emailVerificationToken;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name = "avatar_s3_object_key")
    private String avatarS3ObjectKey;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_projects", joinColumns = { @JoinColumn(name = "username") }, inverseJoinColumns = { @JoinColumn(name = "project_guid") })
    private Set<ProjectEntity> projects;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "username") }, inverseJoinColumns = { @JoinColumn(name = "role_guid") })
    private Set<RoleEntity> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_groups", joinColumns = { @JoinColumn(name = "username") }, inverseJoinColumns = { @JoinColumn(name = "group_guid") })
    private Set<GroupEntity> groups;

    public String getUsername() {
        return username;
    }

    public void setUsername(String value){
        this.username = value;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String value){
        this.firstName = value;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String value){
        this.lastName = value;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String value){
        this.password =  value;
    }

    public boolean isEnabled() {return enabled;}
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getAvatarS3ObjectKey() {
        return avatarS3ObjectKey;
    }

    public void setAvatarS3ObjectKey(String avatarS3ObjectKey) {
        this.avatarS3ObjectKey = avatarS3ObjectKey;
    }

    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
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
