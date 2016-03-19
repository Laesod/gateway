package com.repository.userManagement;

/*
 * #%L
 * Gateway
 * %%
 * Copyright (C) 2015 Powered by Sergey
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import com.entity.userManagement.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by aautushk on 8/30/2015.
 */
@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Page<UserEntity> findAll(Pageable pageable);
    UserEntity findByUsername(String username);
    UserEntity findByEmailVerificationToken(String emailVerificationToken);
    UserEntity findByResetPasswordToken(String emailVerificationToken);

    @Query("select b.projectGuid, d.content from UserEntity a join a.projects b join b.translationMap c join c.translations d where a.username=:username and b.markedAsDeleted=false and d.field='description' and d.language=:language")
    ArrayList<Object[]> getUserProjects(@Param("username") String username, @Param("language") String language);

    @Query("select b.projectGuid, d.content from UserEntity a join a.projects b join b.translationMap c join c.translations d where a.username=:username and b.projectGuid=:projectGuid and d.field='description' and d.language=:language")
    ArrayList<Object[]> getUserProject(@Param("username") String username, @Param("projectGuid") String projectGuid, @Param("language") String language);

//    @Query("select b.roleName from UserEntity a join a.roles b where a.username=:username and b.project.projectGuid=:projectGuid")
//    ArrayList<Object[]> getUserRolesForProject(@Param("username") String username, @Param("projectGuid") String projectGuid);
}