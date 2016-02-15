package com.repository;

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


import com.dto.ProjectResponseDto;
import com.dto.ProjectUserResponseDto;
import com.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aautushk on 9/13/2015.
 */
@Repository
public interface IProjectRepository extends JpaRepository<ProjectEntity, Long> {
    Page<ProjectEntity> findAll(Pageable pageable);
    List<ProjectEntity> findByCreatedByUser(String createdBy);

    ProjectEntity findByProjectGuid(String guid);

    @Query("select a.projectGuid, c.content as description from ProjectEntity a join a.translationMap b join b.translations c where a.projectGuid=:projectGuid and c.field='description'")
    ArrayList<Object[]> getProject(@Param("projectGuid") String projectGuid);

    @Query("select b.username, b.firstName, b.lastName from ProjectEntity a join a.users b where a.projectGuid=:projectGuid")
    ArrayList<Object[]> getProjectUsers(@Param("projectGuid") String projectGuid);


//    @Query("select b.username, b.firstName, b.lastName from ProjectEntity a join a.users b where a.projectGuid=:projectGuid")
//    ArrayList<Object[]> getProjectGroups(@Param("projectGuid") String projectGuid);


//    @Query("select a from ProjectEntity a where a. b where a.projectGuid=:projectGuid")
//    List<ProjectEntity> getUserProjects(@Param("projectGuid") String projectGuid);


//    @Query("select b.username, b.firstName, b.lastName from ProjectEntity a join a.users b where a.projectGuid=:projectGuid")
//    List<ProjectUserResponseDto> getProjectUsers(@Param("projectGuid") String projectGuid);
}
