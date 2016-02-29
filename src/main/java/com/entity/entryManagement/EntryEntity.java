package com.entity.entryManagement;

import com.entity.BaseEntity;
import com.entity.TranslationMapEntity;
import com.entity.userManagement.GroupEntity;
import com.entity.userManagement.ProjectEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by root on 28/02/16.
 */
@Entity
@Table(name = "entries")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class EntryEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "entry_guid"   )
    private String entryGuid;

    @OneToOne
    @JoinColumn(name = "entryTypeGuid")
    private EntryTypeEntity entryType;

    @OneToOne
    @JoinColumn(name = "entryStatusGuid")
    private EntryStatusEntity entryStatus;

    @OneToOne
    @JoinColumn(name = "projectGuid")
    private ProjectEntity project;

    @Column(name = "description")
    @Lob
    private String description;

    @OneToOne
    @JoinColumn(name = "deficiencyDetailsGuid")
    private ProjectEntity deficiency;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "entries_groups", joinColumns = { @JoinColumn(name = "entry_guid") }, inverseJoinColumns = { @JoinColumn(name = "group_guid") })
    private Set<GroupEntity> groups;
}