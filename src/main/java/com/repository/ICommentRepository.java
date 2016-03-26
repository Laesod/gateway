package com.repository;

import com.entity.CommentEntity;
import com.entity.CommentMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by root on 26/03/16.
 */
@Repository
public interface ICommentRepository extends JpaRepository<CommentEntity, Long> {
//    Page<TranslationEntity> findAll(Pageable pageable);
//    //    TranslationEntity findByParentGuidAndFieldAndLanguage(String parentGuid, String field, String language);
//    List<TranslationEntity> findByCreatedByUser(String createdBy);
}
