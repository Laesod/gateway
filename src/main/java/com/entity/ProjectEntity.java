package com.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

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

    public String getProjectGuid() {
        return projectGuid;
    }
    public void setProjectGuid(String value){
        this.projectGuid = value;
    }

}
