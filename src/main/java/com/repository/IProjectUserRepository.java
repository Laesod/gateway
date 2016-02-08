//package com.repository;
//
//import com.dto.ProjectUserResponseDto;
//import com.entity.ProjectUserEntity;
//import com.entity.TranslationEntity;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * Created by aautushk on 9/13/2015.
// */
//@Repository
//public interface IProjectUserRepository extends JpaRepository<ProjectUserEntity, Long> {
//    @Query("select a.username, b.first_name, b.last_name from ProjectUserEntity a join a.userEntity b on a.username=b.username where a.projectGuid=:projectGuid")
//    List<ProjectUserResponseDto> getProjectUsers(@Param("projectGuid") String projectGuid);
//}