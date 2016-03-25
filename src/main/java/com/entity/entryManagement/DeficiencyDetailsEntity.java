package com.entity.entryManagement;

import com.entity.BaseEntity;
import com.entity.userManagement.ProjectEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by root on 28/02/16.
 */
@Entity
@Table(name = "deficiency_details")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class DeficiencyDetailsEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "deficiency_details_guid"   )
    private String deficiencyDetailsGuid;

    @OneToOne
    @JoinColumn(name = "entryStatusGuid")
    private EntryStatusEntity entryStatus;

    @Column(name = "due_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    public String getDeficiencyDetailsGuid() {
        return deficiencyDetailsGuid;
    }

    public void setDeficiencyDetailsGuid(String deficiencyDetailsGuid) {
        this.deficiencyDetailsGuid = deficiencyDetailsGuid;
    }

    public EntryStatusEntity getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(EntryStatusEntity entryStatus) {
        this.entryStatus = entryStatus;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}