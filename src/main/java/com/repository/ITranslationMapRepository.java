package com.repository;

import com.entity.TranslationEntity;
import com.entity.TranslationMapEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITranslationMapRepository extends JpaRepository<TranslationMapEntity, Long> {
//    Page<TranslationEntity> findAll(Pageable pageable);
//    //    TranslationEntity findByParentGuidAndFieldAndLanguage(String parentGuid, String field, String language);
//    List<TranslationEntity> findByCreatedByUser(String createdBy);
}
