package com.entity.entryManagement;

import com.entity.TranslationMapEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by root on 28/02/16.
 */
@Entity
@Table(name = "entry_statuses")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class EntryStatusEntity{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "entry_status_guid"   )
    private String entryStatusGuid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "translationMapGuid")
    private TranslationMapEntity translationMap;

    @Column(name = "parent_entry_type")
    private String parentEntryType;

    @Column(name = "icon_s3_object_key")
    private String iconS3ObjectKey;

    @Column(name = "background_color")
    private String backgroundColor;

    @Column(name = "ranking")
    private Integer ranking;

    public String getEntryStatusGuid() {
        return entryStatusGuid;
    }

    public void setEntryStatusGuid(String entryStatusGuid) {
        this.entryStatusGuid = entryStatusGuid;
    }

    public TranslationMapEntity getTranslationMap() {
        return translationMap;
    }

    public void setTranslationMap(TranslationMapEntity translationMap) {
        this.translationMap = translationMap;
    }

    public String getParentEntryType() {
        return parentEntryType;
    }

    public void setParentEntryType(String parentEntryType) {
        this.parentEntryType = parentEntryType;
    }

    public String getIconS3ObjectKey() {
        return iconS3ObjectKey;
    }

    public void setIconS3ObjectKey(String iconS3ObjectKey) {
        this.iconS3ObjectKey = iconS3ObjectKey;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
}
