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
@Table(name = "entry_types")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class EntryTypeEntity{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "entry_type_guid"   )
    private String entryTypeGuid;

    @OneToOne
    @JoinColumn(name = "translationMapGuid")
    private TranslationMapEntity translationMap;


}