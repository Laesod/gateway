package com.repository.userManagement;

import com.entity.userManagement.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by root on 13/02/16.
 */
@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRoleGuid(String roleGuid);

    @Query("select a.roleGuid, a.roleName from RoleEntity a where a.project.projectGuid=:projectGuid and a.roleName like :nameContains")
    ArrayList<Object[]> getProjectRoles(@Param("projectGuid") String projectGuid, @Param("nameContains") String nameContains);
}