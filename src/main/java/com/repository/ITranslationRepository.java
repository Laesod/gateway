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



import com.entity.TranslationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aautushk on 9/13/2015.
 */
@Repository
public interface ITranslationRepository extends JpaRepository<TranslationEntity, Long> {
    Page<TranslationEntity> findAll(Pageable pageable);
//    TranslationEntity findByParentGuidAndFieldAndLanguage(String parentGuid, String field, String language);
   //    List<TranslationEntity> findByCreatedByUser(String createdBy);

    @Query("select a from TranslationEntity a where a.translationMap.translationMapGuid=:translationMapGuid and a.field=:field and a.language=:language")
    List<TranslationEntity> getTranslation(@Param("translationMapGuid") String translationMapGuid, @Param("field") String field, @Param("language") String language);

   // List<TranslationEntity> findByTranslationMapGuid(String translationMapGuid);
}

