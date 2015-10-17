package com.repository;

import com.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aautushk on 9/13/2015.
 */
@Repository
public interface IProjectRepository extends JpaRepository<ProjectEntity, Long> {
    Page<ProjectEntity> findAll(Pageable pageable);
    List<ProjectEntity> findByCreatedByUser(String createdBy);
}
