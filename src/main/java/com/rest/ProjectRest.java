package com.rest;

import com.dto.*;
import com.entity.*;
import com.google.common.collect.ObjectArrays;
import com.repository.*;
import com.utils.SecurityContextReader;
import org.modelmapper.ModelMapper;
import org.omg.CORBA.IRObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.security.Principal;
import java.util.*;

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
    public IRoleRepository roleRepository;

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
    public IInvitationRepository invitationRepository;

    public SecurityContextReader securityContextReader = new SecurityContextReader();

    @RequestMapping(value = "/createProject", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity createProject(@Valid @RequestBody ProjectRequestDto projectRequestDto) {
        //no auth check - any one can create new projects
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

        project.setTranslationMap(translationMap);

        translationMapRepository.save(translationMap);
        translationRepository.save(translation);

        RoleEntity role = new RoleEntity();
        role.setProject(project);
        role.setRoleName("user");// can modify entries within his groups
        roleRepository.save(role);

        role = new RoleEntity();
        role.setProject(project);
        role.setRoleName("viewer");// can view entries within his groups
        roleRepository.save(role);

        role = new RoleEntity();
        role.setProject(project);
        role.setRoleName("manager");//can create/modify any entries for the project, assign entries to the groups
        roleRepository.save(role);

        Set<RoleEntity> roles = user.getRoles();
        roles.add(role);

        role = new RoleEntity();
        role.setProject(project);
        role.setRoleName("admin");// manages project (invites/removed users, gives roles, assign/create groups)
        roles.add(role);

        user.setRoles(roles);

        Set<RoleEntity> projectRoles = new HashSet<RoleEntity>();
        projectRoles.add(role);

        project.setRoles(projectRoles);

        roleRepository.save(role);

        userRepository.save(user);
        projectRepository.save(project);

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
    public ProjectResponseDto getProject(@PathVariable String projectGuid) {
        //auth check if user has one of the project related roles
        ArrayList<Object[]> projects = projectRepository.getProject(projectGuid);

        ProjectResponseDto projectResponseDto = new ProjectResponseDto();
        projectResponseDto.setProjectGuid((String) projects.get(0)[0]);
        projectResponseDto.setDescription((String) projects.get(0)[1]);

        return projectResponseDto;
    }

    @RequestMapping(value = "/updateProject/{projectGuid}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity updateProject(@Valid @RequestBody ProjectRequestDto projectRequestDto, @PathVariable String projectGuid) {
        //auh check user has admin role for the project
        TranslationEntity translation = null;

        ProjectEntity project = projectRepository.findByProjectGuid(projectGuid);

        TranslationMapEntity translationMap = project.getTranslationMap();
        List<TranslationEntity> translations = translationRepository.getTranslation(translationMap.getTranslationMapGuid(), "description", LocaleContextHolder.getLocale().getDisplayLanguage());
        if (translations.size() > 0) {
            translation = translations.get(0);
            translation.setContent(projectRequestDto.getDescription());
            translationRepository.save(translation);
        } else {
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

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getUserProjects", method = RequestMethod.GET)
    public List<UserProjectResponseDto> getUserProjects() {
        //no auth check - any one can see list of projects he/she is assigned to
        List<UserProjectResponseDto> userProjectResponseDtos = new ArrayList<UserProjectResponseDto>();
        ArrayList<Object[]> userProjects = userRepository.getUserProjects(securityContextReader.getUsername());

        UserEntity userEntity = userRepository.findByUsername(securityContextReader.getUsername());

        for (Object[] userProject : userProjects) {
            List<RoleResponseDto> roles = new ArrayList<>();
            UserProjectResponseDto userProjectResponseDto = new UserProjectResponseDto();

            for (RoleEntity role : userEntity.getRoles()) {
                if (userProject[0].equals(role.getProject().getProjectGuid())) {
                    RoleResponseDto roleResponseDto = new RoleResponseDto();
                    roleResponseDto.setRoleGuid(role.getRoleGuid());
                    roleResponseDto.setRoleName(role.getRoleName());

                    roles.add(roleResponseDto);
                }
            }
            userProjectResponseDto.setProjectGuid((String) userProject[0]);
            userProjectResponseDto.setProjectDescription((String) userProject[1]);
            userProjectResponseDto.setRoles(roles);

            userProjectResponseDtos.add(userProjectResponseDto);
        }

        return userProjectResponseDtos;
    }

    @RequestMapping(value = "/getProjectUsers/{projectGuid}", method = RequestMethod.GET)
    public List<ProjectUserResponseDto> getProjectUsers(@PathVariable String projectGuid) {
        //auth check if user has admin role for the project
        ArrayList<Object[]> users = projectRepository.getProjectUsers(projectGuid);
        List<ProjectUserResponseDto> projectUserResponseDtos = new ArrayList<>();

        for (Object[] user : users) {
            UserEntity userEntity = userRepository.findByUsername((String) user[0]);
            ProjectUserResponseDto projectUserResponseDto = new ProjectUserResponseDto();

            projectUserResponseDto.setUsername((String) user[0]);
            projectUserResponseDto.setFirstName((String) user[1]);
            projectUserResponseDto.setLastName((String) user[2]);

            List<RoleResponseDto> userRoles = new ArrayList<>();

            for (RoleEntity role : userEntity.getRoles()) {
                if (role.getProject().getProjectGuid().equals(projectGuid)) {
                    RoleResponseDto roleResponseDto = new RoleResponseDto();
                    roleResponseDto.setRoleGuid(role.getRoleGuid());
                    roleResponseDto.setRoleName(role.getRoleName());
                    userRoles.add(roleResponseDto);
                }
            }
            projectUserResponseDto.setRoles(userRoles);

            projectUserResponseDtos.add(projectUserResponseDto);
        }

        return projectUserResponseDtos;
    }

    @RequestMapping(value = "/getProjectRoles/{projectGuid}", method = RequestMethod.GET)
    public List<RoleResponseDto> getProjectRoles(@PathVariable String projectGuid) {
        //auth check if user has admin role for the project
        List<RoleResponseDto> roles = new ArrayList<>();

        ArrayList<Object[]> projectRoles = roleRepository.getProjectRoles(projectGuid);
        for (Object[] projectRole : projectRoles) {
            RoleResponseDto role = new RoleResponseDto();
            role.setRoleGuid((String) projectRole[0]);
            role.setRoleName((String) projectRole[1]);

            roles.add(role);
        }

        return roles;
    }

    @RequestMapping(value = "/removeUserFromProject/{projectGuid}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity removeUserFromProject(@PathVariable String projectGuid, @RequestParam String username) {
        //auth check if user has admin role for the project
        RoleEntity role = new RoleEntity();
        ProjectEntity project = new ProjectEntity();
        UserEntity user = userRepository.findByUsername(username);
        Set<RoleEntity> userRoles = user.getRoles();
        Set<ProjectEntity> userProjects = user.getProjects();

        Iterator<RoleEntity> iteratorForRoles = userRoles.iterator();
        while (iteratorForRoles.hasNext()) {
            role = iteratorForRoles.next();

            if (role.getProject().getProjectGuid().equals(projectGuid)) {
                iteratorForRoles.remove();
            }
        }

        Iterator<ProjectEntity> iteratorForProjects = userProjects.iterator();
        while (iteratorForProjects.hasNext()) {
            project = iteratorForProjects.next();

            if (project.getProjectGuid().equals(projectGuid)) {
                iteratorForProjects.remove();
            }
        }

        userRepository.save(user);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/updateUserRolesForProject/{projectGuid}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity updateUserRolesForProject(@RequestBody UpdateUserRolesRequestDto updateUserRolesRequestDto, @PathVariable String projectGuid, @RequestParam String username) {
        //auth check if user has admin role for the project
        RoleEntity role = new RoleEntity();
        UserEntity user = userRepository.findByUsername(username);
        Set<RoleEntity> userRoles = user.getRoles();

        if (updateUserRolesRequestDto.getRolesToRemove() != null) {
            List<String> rolesToRemove = Arrays.asList(updateUserRolesRequestDto.getRolesToRemove());

            Iterator<RoleEntity> iterator = userRoles.iterator();
            while (iterator.hasNext()) {
                role = iterator.next();

                if (rolesToRemove.contains(role.getRoleGuid())) {
                    iterator.remove();
                }
            }
        }

        if (updateUserRolesRequestDto.getRolesToAdd() != null) {
            for (String roleGuid : updateUserRolesRequestDto.getRolesToAdd()) {
                role = roleRepository.findByRoleGuid(roleGuid);
                userRoles.add(role);
            }
        }

        userRepository.save(user);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getProjectGroups/{projectGuid}", method = RequestMethod.GET)
    public List<GroupResponseDto> getProjectGroups(@PathVariable String projectGuid) {
        //auth check if user has admin role for the project
        List<GroupResponseDto> groups = new ArrayList<>();

        ArrayList<Object[]> projectGroups = groupRepository.getProjectGroups(projectGuid);
        for (Object[] projectGroup : projectGroups) {
            GroupResponseDto group = new GroupResponseDto();
            group.setGroupGuid((String) projectGroup[0]);
            group.setGroupName((String) projectGroup[1]);

            groups.add(group);
        }

        return groups;
    }

    @RequestMapping(value = "/updateUserGroupsForProject/{projectGuid}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity updateUserGroupsForProject(@RequestBody UpdateUserGroupsRequestDto updateUserGroupsRequestDto, @PathVariable String projectGuid, @RequestParam String username) {
        //auth check if user has admin role for the project

        GroupEntity group = new GroupEntity();
        UserEntity user = userRepository.findByUsername(username);
        Set<GroupEntity> userGroups = user.getGroups();

        if (updateUserGroupsRequestDto.getGroupsToRemove() != null) {
            List<String> groupsToRemove = Arrays.asList(updateUserGroupsRequestDto.getGroupsToRemove());

            Iterator<GroupEntity> iterator = userGroups.iterator();
            while (iterator.hasNext()) {
                group = iterator.next();

                if (groupsToRemove.contains(group.getGroupGuid())) {
                    iterator.remove();
                }
            }
        }

        if (updateUserGroupsRequestDto.getGroupsToAdd() != null) {
            for (String groupGuid : updateUserGroupsRequestDto.getGroupsToAdd()) {
                group = groupRepository.findByGroupGuid(groupGuid);
                userGroups.add(group);
            }
        }

        if (updateUserGroupsRequestDto.getGroupsToCreateAndAdd() != null) {
            for (GroupRequestDto groupToCreate : updateUserGroupsRequestDto.getGroupsToCreateAndAdd()) {
                group = new GroupEntity();
                group.setGroupName(groupToCreate.getGroupName());

                userGroups.add(group);

                groupRepository.save(group);
            }
        }

        user.setGroups(userGroups);

        userRepository.save(user);

        return new ResponseEntity(HttpStatus.OK);
    }

}
