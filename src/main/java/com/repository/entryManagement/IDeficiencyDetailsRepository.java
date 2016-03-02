package com.repository.entryManagement;

import com.entity.entryManagement.DeficiencyDetailsEntity;
import com.entity.entryManagement.EntryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by root on 01/03/16.
 */
@Repository
public interface IDeficiencyDetailsRepository extends JpaRepository<DeficiencyDetailsEntity, Long> {
    DeficiencyDetailsEntity findByDeficiencyDetailsGuid(String deficiencyDetailsGuid);

    @Query("select a.deficiencyDetailsGuid, b.entryStatusGuid, d.content, b.backgroundColor from DeficiencyDetailsEntity a join a.entryStatus b join b.translationMap c join c.translations d where a.deficiencyDetailsGuid=:deficiencyDetailsGuid and d.field='name' and d.language=:language")
    List<Object[]> getDeficiencyDetails(@Param("deficiencyDetailsGuid") String deficiencyDetailsGuid, @Param("language") String language);
}
