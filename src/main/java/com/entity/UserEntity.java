package com.entity;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

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
}
