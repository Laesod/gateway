package com.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by aautushk on 9/13/2015.
 */
@Entity
@Table(name = "translations")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class TranslationEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "translation_guid"   )
    private String translationGuid;

    @Column(name = "parent_guid")
    private String parentGuid;

    @Column(name = "parent_entity")
    private String parentEntity;

    @Column(name = "field")
    private String field;

    @Column(name = "language")
    private String language;

    @Column(name = "content")
    private String content;

    public String getTranslationGuid() {
        return translationGuid;
    }

    public void setTranslationGuid(String translationGuid) {
        this.translationGuid = translationGuid;
    }

    public String getParentGuid() {
        return parentGuid;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
    }

    public String getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(String parentEntity) {
        this.parentEntity = parentEntity;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
