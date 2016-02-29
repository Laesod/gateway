package com.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by aautushk on 9/13/2015.
 */
@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "comment_guid"   )
    private String commentGuid;


}
