package com.entity.entryManagement;

import com.entity.BaseEntity;
import com.entity.CommentMapEntity;
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
    @JoinColumn(name = "projectGuid")
    private ProjectEntity project;

    @Column(name = "description")
    @Lob
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deficiencyDetailsGuid")
    private DeficiencyDetailsEntity deficiencyDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contactDetailsGuid")
    private ContactDetailsEntity contactDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "commentMapGuid")
    private CommentMapEntity commentMap;

    @ManyToMany
    @JoinTable(name = "entries_groups", joinColumns = { @JoinColumn(name = "entry_guid") }, inverseJoinColumns = { @JoinColumn(name = "group_guid") })
    private Set<GroupEntity> groups;

    public String getEntryGuid() {
        return entryGuid;
    }

    public void setEntryGuid(String entryGuid) {
        this.entryGuid = entryGuid;
    }

    public EntryTypeEntity getEntryType() {
        return entryType;
    }

    public void setEntryType(EntryTypeEntity entryType) {
        this.entryType = entryType;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DeficiencyDetailsEntity getDeficiencyDetails() {
        return deficiencyDetails;
    }

    public void setDeficiencyDetails(DeficiencyDetailsEntity deficiencyDetails) {
        this.deficiencyDetails = deficiencyDetails;
    }

    public ContactDetailsEntity getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetailsEntity contactDetails) {
        this.contactDetails = contactDetails;
    }

    public CommentMapEntity getCommentMap() {
        return commentMap;
    }

    public void setCommentMap(CommentMapEntity commentMap) {
        this.commentMap = commentMap;
    }

    public Set<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupEntity> groups) {
        this.groups = groups;
    }
}