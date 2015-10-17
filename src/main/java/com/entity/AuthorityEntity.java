package com.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by aautushk on 9/15/2015.
 */
@Entity
@Table(name = "authorities")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class AuthorityEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "authority_guid"   )
    private String authorityGuid;

    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    private String authority;

    public String getAuthorityGuid() {
        return authorityGuid;
    }

    public void setAuthorityGuid(String authorityGuid) {
        this.authorityGuid = authorityGuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
