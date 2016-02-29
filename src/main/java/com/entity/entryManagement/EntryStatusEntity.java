package com.entity.entryManagement;

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

}
