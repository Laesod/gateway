package com.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by aautushk on 9/13/2015.
 */
@Entity
@Table(name = "file_metadatas")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class FileMetadataEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "file_metadata_guid"   )
    private String fileMetadataGuid;


}