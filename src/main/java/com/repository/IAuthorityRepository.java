package com.repository;

import com.entity.AuthorityEntity;
import com.entity.TranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aautushk on 9/15/2015.
 */
@Repository
public interface IAuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
    List<AuthorityEntity> findByCreatedByUser(String createdBy);
    List<AuthorityEntity> findByUsername(String username);
}