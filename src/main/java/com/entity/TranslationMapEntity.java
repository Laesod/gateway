package com.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by root on 06/02/16.
 */

@Entity
@Table(name = "translation_maps")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class TranslationMapEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "translation_map_guid"   )
    private String translationMapGuid;

    @OneToMany(mappedBy="translationMap", cascade = CascadeType.ALL)
    private Set<TranslationEntity> translations;

    public String getTranslationMapGuid() {
        return translationMapGuid;
    }

    public void setTranslationMapGuid(String translationMapGuid) {
        this.translationMapGuid = translationMapGuid;
    }

    public Set<TranslationEntity> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<TranslationEntity> translations) {
        this.translations = translations;
    }
}
