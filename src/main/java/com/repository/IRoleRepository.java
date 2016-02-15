package com.repository;

import com.entity.GroupEntity;
import com.entity.RoleEntity;
import com.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 13/02/16.
 */
@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRoleGuid(String roleGuid);

    @Query("select a.roleGuid, a.roleName from RoleEntity a where a.project.projectGuid=:projectGuid")
    ArrayList<Object[]> getProjectRoles(@Param("projectGuid") String projectGuid);
}