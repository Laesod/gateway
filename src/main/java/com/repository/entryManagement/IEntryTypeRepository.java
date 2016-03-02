package com.repository.entryManagement;

import com.entity.entryManagement.EntryTypeEntity;
import com.entity.userManagement.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by root on 28/02/16.
 */
@Repository
public interface IEntryTypeRepository extends JpaRepository<EntryTypeEntity, Long> {
    List<EntryTypeEntity> findAll();
    EntryTypeEntity findByEntryTypeGuid(String EntryTypeGuid);
}