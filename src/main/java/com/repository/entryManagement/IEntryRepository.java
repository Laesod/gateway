package com.repository.entryManagement;

import com.entity.entryManagement.EntryEntity;
import com.entity.entryManagement.EntryTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by root on 01/03/16.
 */
@Repository
public interface IEntryRepository extends JpaRepository<EntryEntity, Long> {
    EntryEntity findByEntryGuid(String EntryGuid);

    @Query("select a.entryGuid, a.description, b.entryTypeGuid, d.content  from EntryEntity a join a.entryType b join b.translationMap c join c.translations d where d.field='name' and d.language=:language")
    Page<Object[]> getEntries(@Param("language") String language, Pageable pageable);
}
