package com.rest;

import com.dto.GroupRequestDto;
import com.dto.InvitationRequestDto;
import com.dto.ProjectRequestDto;
import com.entity.*;
import com.repository.*;
import com.utils.SecurityContextReader;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

        for(GroupRequestDto groupRequestDto : projectRequestDto.getGroups()){
            GroupEntity groupEntity = new GroupEntity();
            groupEntity.setGroupName(groupRequestDto.getGroupName());
            if(groupRequestDto.getGroupGuid() != ""){
                groupEntity.setGroupGuid(groupRequestDto.getGroupGuid());
            }

            groupRepository.save(groupEntity);//group is created

            ProjectGroupEntity projectGroupEntity = new ProjectGroupEntity();
            projectGroupEntity.setProjectGuid(project.getProjectGuid());
            projectGroupEntity.setGroupGuid(groupEntity.getGroupGuid());
            projectGroupRepository.save(projectGroupEntity);//group is assigned to the project
        }

        for(InvitationRequestDto invitation : projectRequestDto.getInvitations()){
            InvitationEntity invitationEntity = new InvitationEntity();
            invitationEntity.setRecipientEmail(invitation.getRecipientEmail());
            invitationEntity.setProjectGuid(project.getProjectGuid());
            invitationEntity.setAuthority(invitation.getAuthority());
            invitationEntity.setIsInvitationAccepted(false);

            invitationRepository.save(invitationEntity);//invitation is created

            for(GroupRequestDto groupRequestDto : invitation.getGroups()){
                InvitationGroupEntity invitationGroupEntity = new InvitationGroupEntity();
                invitationGroupEntity.setInvitationGuid(invitationEntity.getInvitationGuid());
                invitationGroupEntity.setGroupGuid(groupRequestDto.getGroupGuid());

                invitationGroupRepository.save(invitationGroupEntity);//group is assigned to the invitation
            }
        }

        return new ResponseEntity(HttpStatus.OK);
    }

//    private ProjectResponseDto convertToResponseDto(ProjectEntity project) {
//        ProjectResponseDto projectResponseDto = modelMapper.map(project, ProjectResponseDto.class);
//        TranslationEntity translationEntity = translationRepository.findByParentGuidAndFieldAndLanguage(projectResponseDto.getProjectGuid(), "description", LocaleContextHolder.getLocale().getDisplayLanguage());
//
//        if(translationEntity != null){
//            projectResponseDto.setDescription(translationEntity.getContent());
//        }else{
//            projectResponseDto.setDescription("");
//        }
//
//        return projectResponseDto;
//    }
//
//    @RequestMapping(value = "/getProjects", method = RequestMethod.GET)
//    public Page<ProjectResponseDto> getProjects(Pageable pageable) {
//        int totalElements = 0;
//        List<ProjectResponseDto> projectsResponseDto = new ArrayList<ProjectResponseDto>();
//        Page<ProjectEntity> projects = projectRepository.findAll(pageable);
//
//        if(projects != null){
//            totalElements = projects.getNumberOfElements();
//            for(ProjectEntity project : projects){
//                ProjectResponseDto projectResponseDto = this.convertToResponseDto(project);
//                projectsResponseDto.add(projectResponseDto);
//            }
//        }
//
//        return new PageImpl<>(projectsResponseDto, pageable, totalElements);
//    }
}
