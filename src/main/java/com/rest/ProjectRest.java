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
import com.entity.AuthorityEntity;
import com.entity.ProjectEntity;
import com.entity.ProjectUserEntity;
import com.entity.TranslationEntity;
import com.repository.*;
import com.utils.SecurityContextReader;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    public IProjectUserRepository projectUserRepository;

    @Autowired
    public IAuthorityRepository authorityRepository;

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
        projectRepository.save(project);//project is created

        TranslationEntity translationEntity = new TranslationEntity();
        translationEntity.setParentGuid(project.getProjectGuid());
        translationEntity.setParentEntity("Project");
        translationEntity.setField("description");
        translationEntity.setLanguage(LocaleContextHolder.getLocale().getDisplayLanguage());
        translationEntity.setContent(projectRequestDto.getDescription());

        translationRepository.save(translationEntity);//project description translation is created

        ProjectUserEntity projectUserEntity = new ProjectUserEntity();
        projectUserEntity.setUsername(securityContextReader.getUsername());

        projectUserRepository.save(projectUserEntity);//project is assigned to its author

        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setUsername(securityContextReader.getUsername());
        authorityEntity.setAuthority(project.getProjectGuid() + "_admin");

        authorityRepository.save(authorityEntity);//admin role is assigned to the project author

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

    private ProjectResponseDto convertToResponseDto(ProjectEntity project) {
        ProjectResponseDto projectResponseDto = modelMapper.map(project, ProjectResponseDto.class);
        TranslationEntity translationEntity = translationRepository.findByParentGuidAndFieldAndLanguage(projectResponseDto.getProjectGuid(), "description", LocaleContextHolder.getLocale().getDisplayLanguage());

        if(translationEntity != null){
            projectResponseDto.setDescription(translationEntity.getContent());
        }else{
            projectResponseDto.setDescription("");
        }

        return projectResponseDto;
    }

    @RequestMapping(value = "/getProject/{projectGuid}", method = RequestMethod.GET)
    public ProjectResponseDto getProject(@PathVariable String projectGuid, Principal user) {
        ProjectEntity project = projectRepository.findByProjectGuidAndCreatedByUser(projectGuid, user.getName());

        ProjectResponseDto projectResponseDto = this.convertToResponseDto(project);

        return projectResponseDto;
    }

    @RequestMapping(value = "/updateProject/{projectGuid}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity updateProject(@Valid @RequestBody ProjectRequestDto projectRequestDto, @PathVariable String projectGuid, Principal user) {
        ProjectEntity project = projectRepository.findByProjectGuidAndCreatedByUser(projectGuid, user.getName());

        TranslationEntity translationEntity = translationRepository.findByParentGuidAndFieldAndLanguage(project.getProjectGuid(), "description", LocaleContextHolder.getLocale().getDisplayLanguage());
        if(translationEntity != null){
            translationEntity.setContent(projectRequestDto.getDescription());
        }else{
            translationEntity = new TranslationEntity();
            translationEntity.setParentGuid(project.getProjectGuid());
            translationEntity.setParentEntity("Project");
            translationEntity.setField("description");
            translationEntity.setLanguage(LocaleContextHolder.getLocale().getDisplayLanguage());
            translationEntity.setContent(projectRequestDto.getDescription());

            translationRepository.save(translationEntity);//project description translation is created
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getProjects", method = RequestMethod.GET)
    public List<ProjectResponseDto> getProjects(Principal user) {

        List<ProjectResponseDto> projectsResponseDto = new ArrayList<ProjectResponseDto>();
        List<ProjectEntity> projects = projectRepository.findByCreatedByUser(user.getName());

        for(ProjectEntity project : projects){
            ProjectResponseDto projectResponseDto = this.convertToResponseDto(project);
            projectsResponseDto.add(projectResponseDto);
        }

        return projectsResponseDto;
    }
}
