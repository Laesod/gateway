package com.repository.entryManagement;

import com.entity.entryManagement.ContactTypeEntity;
import com.entity.entryManagement.EntryStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by root on 24/03/16.
 */
@Repository
public interface IContactTypeRepository extends JpaRepository<ContactTypeEntity, Long> {
    ContactTypeEntity findByContactTypeGuid(String contactTypeGuid);

    @Query("select a.contactTypeGuid, a.ranking, c.content from ContactTypeEntity a join a.translationMap b join b.translations c where c.field='name' and c.language=:language order by a.ranking")
    ArrayList<Object[]> getContactTypes( @Param("language") String language);
}
