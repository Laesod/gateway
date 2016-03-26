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

    @OneToMany(mappedBy="commentMap", cascade = CascadeType.ALL)
    private Set<CommentEntity> comments;

    public String getCommentMapGuid() {
        return commentMapGuid;
    }

    public void setCommentMapGuid(String commentMapGuid) {
        this.commentMapGuid = commentMapGuid;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentEntity> comments) {
        this.comments = comments;
    }
}
