package com.repository.entryManagement;

import com.entity.entryManagement.ContactDetailsEntity;
import com.entity.entryManagement.DeficiencyDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by root on 24/03/16.
 */
@Repository
public interface IContactDetailsRepository extends JpaRepository<ContactDetailsEntity, Long> {
    ContactDetailsEntity findByContactDetailsGuid(String contactDetailsGuid);

    @Query("select a.contactDetailsGuid, b.contactTypeGuid, a.photoS3ObjectKey, a.personFirstName, a.personLastName, a.personMobilePhone, a.personEmail, a.personAddress, a.organizationName, a.organizationWebSite, a.organizationContactPhone, a.organizationContactEmail, a.organizationAddress, d.content from ContactDetailsEntity a join a.contactType b join b.translationMap c join c.translations d where a.contactDetailsGuid=:contactDetailsGuid and d.field='name' and d.language=:language")
    List<Object[]> getContactDetails(@Param("contactDetailsGuid") String contactDetailsGuid, @Param("language") String language);
}