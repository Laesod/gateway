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
import java.util.List;

/**
 * Created by root on 01/03/16.
 */
@Repository
public interface IEntryRepository extends JpaRepository<EntryEntity, Long> {
    EntryEntity findByEntryGuid(String EntryGuid);

    @Query("select a.entryGuid, a.description, b.entryTypeGuid, d.content from EntryEntity a join a.entryType b join b.translationMap c join c.translations d where a.entryGuid=:entryGuid and d.field='name' and d.language=:language")
    ArrayList<Object[]> getEntryByGuid(@Param("entryGuid") String entryGuid, @Param("language") String language);

    @Query("select a.entryGuid, a.description, b.entryTypeGuid, d.content from EntryEntity a join a.groups e join a.entryType b join b.translationMap c join c.translations d where a.entryGuid=:entryGuid and e.groupGuid IN (:groups) and d.field='name' and d.language=:language")
    ArrayList<Object[]>  getEntryByGuidForGroups(@Param("entryGuid") String entryGuid, @Param("groups") List<String> groups, @Param("language") String language);

    @Query("select a.entryGuid, a.description, b.entryTypeGuid, d.content from EntryEntity a join a.entryType b join b.translationMap c join c.translations d where a.project.projectGuid=:projectGuid and d.field='name' and d.language=:language")
    Page<Object[]> getEntriesForProject(@Param("projectGuid") String projectGuid, @Param("language") String language, Pageable pageable);

    @Query("select a.entryGuid, a.description, b.entryTypeGuid, d.content from EntryEntity a join a.groups e join a.entryType b join b.translationMap c join c.translations d where a.project.projectGuid=:projectGuid and e.groupGuid IN (:groups) and d.field='name' and d.language=:language")
    Page<Object[]> getEntriesForProjectAndGroups(@Param("projectGuid") String projectGuid, @Param("groups") List<String> groups, @Param("language") String language, Pageable pageable);


}
