//package com.entity;
//
//import org.hibernate.annotations.GenericGenerator;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import javax.persistence.*;
//import java.util.Set;
//
///**
// * Created by aautushk on 9/13/2015.
// */
//@Entity
//@Table(name = "projects_users")
//@EntityListeners(AuditingEntityListener.class)
//public class ProjectUserEntity extends BaseEntity {
//    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name="system-uuid", strategy = "uuid")
//    @Column(name = "guid")
//    private String guid;
//
//    @Column(name = "project_guid")
//    private String projectGuid;
//
////    @OneToOne(mappedBy = "")
////    private ProjectEntity projectEntity;
//
////    @OneToOne(mappedBy = "username")
////    private  UserEntity userEntity;
//
//    public ProjectUserEntity() {
//    }
//
//    public String getGuid() {
//        return guid;
//    }
//
//    public void setGuid(String guid) {
//        this.guid = guid;
//    }
//
//    @Column(name = "username")
//    private String username;
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getProjectGuid() {
//        return projectGuid;
//    }
//
//    public void setProjectGuid(String projectGuid) {
//        this.projectGuid = projectGuid;
//    }
//}
