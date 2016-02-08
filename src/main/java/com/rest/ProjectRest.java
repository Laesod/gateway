package com.rest;

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


import com.dto.ProjectRequestDto;
import com.dto.ProjectResponseDto;
import com.dto.ProjectUserResponseDto;
import com.dto.UserResponseDto;
import com.entity.*;
import com.repository.*;
import com.utils.SecurityContextReader;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by aautushk on 9/13/2015.
 */
@Component
@RestController
@RequestMapping("/gateway")
public class ProjectRest {
    private ModelMapper modelMapper = new ModelMapper(); //read more at http://modelmapper.org/user-manual/

    @Autowired
    public IProjectRepository projectRepository;

//    @Autowired
//    public IProjectUserRepository projectUserRepository;

    @Autowired
    public IUserRepository userRepository;

    @Autowired
    public IAuthorityRepository authorityRepository;

    @Autowired
    public ITranslationMapRepository translationMapRepository;

    @Autowired
    public ITranslationRepository translationRepository;

    @Autowired
    public IGroupRepository groupRepository;

    @Autowired
    public IProjectGroupRepository projectGroupRepository;

    @Autowired
    public IInvitationRepository invitationRepository;

    @Autowired
    public IInvitationGroupRepository invitationGroupRepository;

    public SecurityContextReader securityContextReader = new SecurityContextReader();

    @RequestMapping(value = "/createProject", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity createProject(@Valid @RequestBody ProjectRequestDto projectRequestDto) {
        ProjectEntity project = new ProjectEntity();

        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());

        Set<UserEntity> users = new HashSet<UserEntity>();
        users.add(user);

        project.setUsers(users);

        TranslationEntity translation = new TranslationEntity();
        translation.setParentEntity("Project");
        translation.setField("description");
        translation.setLanguage(LocaleContextHolder.getLocale().getDisplayLanguage());
        translation.setContent(projectRequestDto.getDescription());

        TranslationMapEntity translationMap = new TranslationMapEntity();

        Set<TranslationEntity> translations = new HashSet<TranslationEntity>();
        translations.add(translation);

        translationMap.setTranslations(translations);
        translation.setTranslationMap(translationMap);

        translationMapRepository.save(translationMap);
        translationRepository.save(translation);

        project.setTranslationMap(translationMap);

        projectRepository.save(project);

        RoleEntity role = new RoleEntity();
        role.setRoleName(project.getProjectGuid() + "_admin");

        Set<RoleEntity> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

//        for(GroupRequestDto groupRequestDto : projectRequestDto.getGroups()){
//            GroupEntity groupEntity = new GroupEntity();
//            groupEntity.setGroupName(groupRequestDto.getGroupName());
//            if(groupRequestDto.getGroupGuid() != ""){
//                groupEntity.setGroupGuid(groupRequestDto.getGroupGuid());
//            }
//
//            groupRepository.save(groupEntity);//group is created
//
//            ProjectGroupEntity projectGroupEntity = new ProjectGroupEntity();
//            projectGroupEntity.setProjectGuid(project.getProjectGuid());
//            projectGroupEntity.setGroupGuid(groupEntity.getGroupGuid());
//            projectGroupRepository.save(projectGroupEntity);//group is assigned to the project
//        }
//
//        for(InvitationRequestDto invitation : projectRequestDto.getInvitations()){
//            InvitationEntity invitationEntity = new InvitationEntity();
//            invitationEntity.setRecipientEmail(invitation.getRecipientEmail());
//            invitationEntity.setProjectGuid(project.getProjectGuid());
//            invitationEntity.setAuthority(invitation.getAuthority());
//            invitationEntity.setIsInvitationAccepted(false);
//
//            invitationRepository.save(invitationEntity);//invitation is created
//
//            for(GroupRequestDto groupRequestDto : invitation.getGroups()){
//                InvitationGroupEntity invitationGroupEntity = new InvitationGroupEntity();
//                invitationGroupEntity.setInvitationGuid(invitationEntity.getInvitationGuid());
//                invitationGroupEntity.setGroupGuid(groupRequestDto.getGroupGuid());
//
//                invitationGroupRepository.save(invitationGroupEntity);//group is assigned to the invitation
//            }
//        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getProject/{projectGuid}", method = RequestMethod.GET)
    public List<ProjectResponseDto> getProject(@PathVariable String projectGuid, Principal user) {
        //auh check user has admin role

        List<ProjectResponseDto> projectResponsesDto = projectRepository.getProject(projectGuid);


        return projectResponsesDto;
    }

    @RequestMapping(value = "/updateProject/{projectGuid}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity updateProject(@Valid @RequestBody ProjectRequestDto projectRequestDto, @PathVariable String projectGuid, Principal user) {
        TranslationEntity translation = null;
        //auh check user has admin role

        ProjectEntity project = projectRepository.findByProjectGuid(projectGuid);

        TranslationMapEntity translationMap = project.getTranslationMap();
        List<TranslationEntity> translations = translationRepository.getTranslation(translationMap.getTranslationMapGuid(), "description", LocaleContextHolder.getLocale().getDisplayLanguage());
        if(translations.size() > 0){
           // translation = modelMapper.map(user, UserResponseDto.class);

            translation = translations.get(0);
            translation.setContent(projectRequestDto.getDescription());
            translationRepository.save(translation);
        }else{
            translation = new TranslationEntity();
            translation.setParentEntity("Project");
            translation.setField("description");
            translation.setLanguage(LocaleContextHolder.getLocale().getDisplayLanguage());
            translation.setContent(projectRequestDto.getDescription());
            translation.setTranslationMap(translationMap);

            Set<TranslationEntity> translationEntities = translationMap.getTranslations();
            translationEntities.add(translation);
            translationMap.setTranslations(translationEntities);

            translationMapRepository.save(translationMap);
            translationRepository.save(translation);
        }
//
//        translationRepository.save(translation);

//        TranslationEntity translationEntity = translationRepository.findByParentGuidAndFieldAndLanguage(project.getProjectGuid(), "description", LocaleContextHolder.getLocale().getDisplayLanguage());
//        if(translationEntity != null){
//            translationEntity.setContent(projectRequestDto.getDescription());
//        }else{
//            translationEntity = new TranslationEntity();
////            translationEntity.setParentGuid(project.getProjectGuid());
//            translationEntity.setParentEntity("Project");
//            translationEntity.setField("description");
//            translationEntity.setLanguage(LocaleContextHolder.getLocale().getDisplayLanguage());
//            translationEntity.setContent(projectRequestDto.getDescription());
//
//            translationRepository.save(translationEntity);//project description translation is created
//        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getProjects", method = RequestMethod.GET)
    public List<ProjectResponseDto> getProjects(Principal user) {

        List<ProjectResponseDto> projectsResponseDto = new ArrayList<ProjectResponseDto>();
        List<ProjectEntity> projects = projectRepository.findByCreatedByUser(user.getName());

//        for(ProjectEntity project : projects){
//            ProjectResponseDto projectResponseDto = this.convertToResponseDto(project);
//            projectsResponseDto.add(projectResponseDto);
//        }

        return projectsResponseDto;
    }

    @RequestMapping(value = "/getProjectUsers/{projectGuid}", method = RequestMethod.GET)
    public List<ProjectUserResponseDto> getProjectUsers(@PathVariable String projectGuid, Principal user) {
        //auth check and exception call if needed

         List<ProjectUserResponseDto> projects = projectRepository.getProjectUsers(projectGuid);

        return projects;
    }
}
