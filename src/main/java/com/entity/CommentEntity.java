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
    @Column(name = "comment_guid")
    private String commentGuid;

    @Column(name = "parent_entity")
    private String parentEntity;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name="comment_map_guid")
    private CommentMapEntity commentMap;

    public String getCommentGuid() {
        return commentGuid;
    }

    public void setCommentGuid(String commentGuid) {
        this.commentGuid = commentGuid;
    }

    public String getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(String parentEntity) {
        this.parentEntity = parentEntity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommentMapEntity getCommentMap() {
        return commentMap;
    }

    public void setCommentMap(CommentMapEntity commentMap) {
        this.commentMap = commentMap;
    }
}
