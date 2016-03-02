package com.repository.entryManagement;

import com.entity.entryManagement.EntryStatusEntity;
import com.entity.entryManagement.EntryTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 01/03/16.
 */

@Repository
public interface IEntryStatusRepository extends JpaRepository<EntryStatusEntity, Long> {
    EntryStatusEntity findByEntryStatusGuid(String entryStatusGuid);

    @Query("select a.entryStatusGuid, c.content, a.iconS3ObjectKey, a.backgroundColor, a.ranking from EntryStatusEntity a join a.translationMap b join b.translations c where a.parentEntryType=:entryType and c.field='name' and c.language=:language order by a.ranking")
    ArrayList<Object[]> getEntryStatuses(@Param("entryType") String entryType, @Param("language") String language);
}