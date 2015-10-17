package com.repository;

import com.entity.GroupEntity;
import com.entity.TranslationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aautushk on 9/15/2015.
 */
@Repository
public interface IGroupRepository extends JpaRepository<GroupEntity, Long> {
    Page<GroupEntity> findAll(Pageable pageable);
    List<GroupEntity> findByCreatedByUser(String createdBy);
}
