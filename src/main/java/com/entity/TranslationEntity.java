package com.entity;

/*
 * #%L
 * Gateway
 * %%
 * Copyright (C) 2015 Powered by Sergey
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


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
public class TranslationEntity{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "translation_guid"   )
    private String translationGuid;

//    @Column(name = "parent_guid")
//    private String parentGuid;

    @Column(name = "parent_entity")
    private String parentEntity;

    @Column(name = "field")
    private String field;

    @Column(name = "language")
    private String language;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name="translation_map_guid")
    private TranslationMapEntity translationMap;

    public String getTranslationGuid() {
        return translationGuid;
    }

    public void setTranslationGuid(String translationGuid) {
        this.translationGuid = translationGuid;
    }

//    public String getParentGuid() {
//        return parentGuid;
//    }
//
//    public void setParentGuid(String parentGuid) {
//        this.parentGuid = parentGuid;
//    }

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

//    public String getTranslation_map_guid() {
//        return translation_map_guid;
//    }
//
//    public void setTranslation_map_guid(String translation_map_guid) {
//        this.translation_map_guid = translation_map_guid;
//    }

    public TranslationMapEntity getTranslationMap() {
        return translationMap;
    }

    public void setTranslationMap(TranslationMapEntity translationMap) {
        this.translationMap = translationMap;
    }
}
