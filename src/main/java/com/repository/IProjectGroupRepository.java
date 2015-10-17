package com.repository;

import com.entity.ProjectGroupEntity;
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
public interface IProjectGroupRepository extends JpaRepository<ProjectGroupEntity, Long> {
    Page<ProjectGroupEntity> findAll(Pageable pageable);
    List<ProjectGroupEntity> findByCreatedByUser(String createdBy);
}
