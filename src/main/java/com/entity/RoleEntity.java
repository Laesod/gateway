package com.entity;

import com.entity.BaseEntity;
import com.entity.UserEntity;
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
    @Column(name = "role_name"   )
    private String roleName;

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
}