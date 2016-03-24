package com.entity.entryManagement;

import com.entity.TranslationMapEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by root on 24/03/16.
 */
@Entity
@Table(name = "contact_types")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class ContactTypeEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "contact_type_guid")
    private String contactTypeGuid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "translationMapGuid")
    private TranslationMapEntity translationMap;

    @Column(name = "ranking")
    private Integer ranking;

    public String getContactTypeGuid() {
        return contactTypeGuid;
    }

    public void setContactTypeGuid(String contactTypeGuid) {
        this.contactTypeGuid = contactTypeGuid;
    }

    public TranslationMapEntity getTranslationMap() {
        return translationMap;
    }

    public void setTranslationMap(TranslationMapEntity translationMap) {
        this.translationMap = translationMap;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
}
