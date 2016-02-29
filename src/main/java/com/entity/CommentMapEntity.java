package com.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by root on 28/02/16.
 */

@Entity
@Table(name = "comment_maps")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class CommentMapEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "comment_map_guid"   )
    private String commentMapGuid;

}
