package com.repository.entryManagement;

import com.entity.entryManagement.EntryTypeEntity;
import com.entity.userManagement.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 28/02/16.
 */
@Repository
public interface IEntryTypeRepository extends JpaRepository<EntryTypeEntity, Long> {
    List<EntryTypeEntity> findAll();
    EntryTypeEntity findByEntryTypeGuid(String EntryTypeGuid);

    @Query("select a.entryTypeGuid, c.content from EntryTypeEntity a join a.translationMap b join b.translations c where c.field='name' and c.language=:language")
    ArrayList<Object[]> getEntryTypes(@Param("language") String language);
}