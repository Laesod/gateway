package com.repository;


import com.entity.ProjectEntity;
import com.entity.TranslationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aautushk on 9/13/2015.
 */
@Repository
public interface ITranslationRepository extends JpaRepository<TranslationEntity, Long> {
    Page<TranslationEntity> findAll(Pageable pageable);
    TranslationEntity findByParentGuidAndFieldAndLanguage(String parentGuid, String field, String language);
    List<TranslationEntity> findByCreatedByUser(String createdBy);
}

