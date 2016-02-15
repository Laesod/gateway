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


import com.entity.GroupEntity;
import com.entity.RoleEntity;
import com.entity.TranslationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aautushk on 9/15/2015.
 */
@Repository
public interface IGroupRepository extends JpaRepository<GroupEntity, Long> {
    GroupEntity findByGroupGuid(String groupGuid);

    @Query("select a.groupGuid, a.groupName from GroupEntity a where a.project.projectGuid=:projectGuid")
    ArrayList<Object[]> getProjectGroups(@Param("projectGuid") String projectGuid);
}
