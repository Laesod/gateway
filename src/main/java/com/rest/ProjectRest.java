package com.rest;

import com.dto.*;
import com.entity.*;
import com.repository.*;
import com.utils.BundleMessageReader;
import com.utils.PermissionsValidator;
import com.utils.SecurityContextReader;
import com.utils.TranslationManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    public PermissionsValidator permissionsValidator = new PermissionsValidator();

    public BundleMessageReader bundleMessageReader = new BundleMessageReader();

    public TranslationManager translationManager = new TranslationManager();

    @RequestMapping(value = "/createProject", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity createProject(@Valid @RequestBody ProjectRequestDto projectRequestDto) {
        //no auth check - any one can create new projects
        ProjectEntity project = new ProjectEntity();

        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());

        Set<UserEntity> users = new HashSet<UserEntity>();
        users.add(user);

        project.setUsers(users);

        TranslationMapEntity translationMap = new TranslationMapEntity();
        Set<TranslationEntity> translations = translationManager.addTranslation(projectRequestDto.getDescription(), "Project", "description", translationMap);

        for (TranslationEntity translation : translations) {
            translationRepository.save(translation);
        }

        project.setTranslationMap(translationMap);

        translationMapRepository.save(translationMap);

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

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getProject/{projectGuid}", method = RequestMethod.GET)
    public ProjectResponseDto getProject(@PathVariable String projectGuid) {
        //auth check if user is assigned to the project
        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());
        if(!permissionsValidator.projectCheck(user, projectGuid)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        ArrayList<Object[]> projects = projectRepository.getProject(projectGuid, LocaleContextHolder.getLocale().getDisplayLanguage());

        ProjectResponseDto projectResponseDto = new ProjectResponseDto();
        projectResponseDto.setProjectGuid((String) projects.get(0)[0]);
        projectResponseDto.setDescription((String) projects.get(0)[1]);

        return projectResponseDto;
    }

    @RequestMapping(value = "/updateProject/{projectGuid}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity updateProject(@Valid @RequestBody ProjectRequestDto projectRequestDto, @PathVariable String projectGuid) {
        //auh check user has admin role for the project
        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"admin"};
        if(!permissionsValidator.rolesForProjectCheck(user, projectGuid, requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

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
        ArrayList<Object[]> userProjects = userRepository.getUserProjects(securityContextReader.getUsername(), LocaleContextHolder.getLocale().getDisplayLanguage());

        UserEntity userEntity = userRepository.findByUsername(securityContextReader.getUsername());

        for (Object[] userProject : userProjects) {
            UserProjectResponseDto userProjectResponseDto = new UserProjectResponseDto();

            List<RoleResponseDto> roles = new ArrayList<>();
            if(userEntity.getRoles() != null){
                for (RoleEntity role : userEntity.getRoles()) {
                    if (userProject[0].equals(role.getProject().getProjectGuid())) {
                        RoleResponseDto roleResponseDto = new RoleResponseDto();
                        roleResponseDto.setRoleGuid(role.getRoleGuid());
                        roleResponseDto.setRoleName(role.getRoleName());

                        roles.add(roleResponseDto);
                    }
                }
                userProjectResponseDto.setRoles(roles);
            }

            List<GroupResponseDto> groups = new ArrayList<>();
            if(userEntity.getGroups() != null){
                for (GroupEntity group : userEntity.getGroups()) {
                    if (userProject[0].equals(group.getProject().getProjectGuid())) {
                        GroupResponseDto groupResponseDto = new GroupResponseDto();
                        groupResponseDto.setGroupGuid(group.getGroupGuid());
                        groupResponseDto.setGroupName(group.getGroupName());

                        groups.add(groupResponseDto);
                    }
                }
            }

            userProjectResponseDto.setGroups(groups);

            userProjectResponseDto.setProjectGuid((String) userProject[0]);
            userProjectResponseDto.setProjectDescription((String) userProject[1]);


            userProjectResponseDtos.add(userProjectResponseDto);
        }

        return userProjectResponseDtos;
    }

    @RequestMapping(value = "/getProjectUsers/{projectGuid}", method = RequestMethod.GET)
    public List<ProjectUserResponseDto> getProjectUsers(@PathVariable String projectGuid) {
        //auth check if user has admin role for the project
        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"admin"};
        if(!permissionsValidator.rolesForProjectCheck(user, projectGuid, requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        ArrayList<Object[]> users = projectRepository.getProjectUsers(projectGuid);
        List<ProjectUserResponseDto> projectUserResponseDtos = new ArrayList<>();

        for (Object[] userItem : users) {
            UserEntity userEntity = userRepository.findByUsername((String) userItem[0]);
            ProjectUserResponseDto projectUserResponseDto = new ProjectUserResponseDto();

            projectUserResponseDto.setUsername((String) userItem[0]);
            projectUserResponseDto.setFirstName((String) userItem[1]);
            projectUserResponseDto.setLastName((String) userItem[2]);

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

            List<GroupResponseDto> userGroups = new ArrayList<>();

            if(userEntity.getGroups() != null){
                for (GroupEntity group : userEntity.getGroups()) {
                    if (group.getProject().getProjectGuid().equals(projectGuid)) {
                        GroupResponseDto groupResponseDto = new GroupResponseDto();
                        groupResponseDto.setGroupGuid(group.getGroupGuid());
                        groupResponseDto.setGroupName(group.getGroupName());
                        userGroups.add(groupResponseDto);
                    }
                }
                projectUserResponseDto.setGroups(userGroups);
            }


            projectUserResponseDtos.add(projectUserResponseDto);
        }

        return projectUserResponseDtos;
    }

    @RequestMapping(value = "/getProjectRoles/{projectGuid}", method = RequestMethod.PUT)
    public List<RoleResponseDto> getProjectRoles(@PathVariable String projectGuid, @RequestBody RoleSearchDto roleSearchDto) {
        //auth check if user has admin role for the project
        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"admin"};
        if(!permissionsValidator.rolesForProjectCheck(user, projectGuid, requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        List<RoleResponseDto> roles = new ArrayList<>();

        ArrayList<Object[]> projectRoles = roleRepository.getProjectRoles(projectGuid, '%' + roleSearchDto.getNameContains() + '%');
        for (Object[] projectRole : projectRoles) {
            Boolean addRole = true;
            if(roleSearchDto.getCurrentRoles() != null){
                for (RoleResponseDto roleResponseDto : roleSearchDto.getCurrentRoles()) {
                    if(roleResponseDto.getRoleName().equals((String) projectRole[1])){
                        addRole = false;
                        break;
                    }
                }
            }

            if(addRole){
                RoleResponseDto role = new RoleResponseDto();
                role.setRoleGuid((String) projectRole[0]);
                role.setRoleName((String) projectRole[1]);

                roles.add(role);
            }
        }

        return roles;
    }

    @RequestMapping(value = "/removeUserFromProject/{projectGuid}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity removeUserFromProject(@PathVariable String projectGuid, @RequestParam String username) {
        //auth check if user has admin role for the project
        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"admin"};
        if(!permissionsValidator.rolesForProjectCheck(user, projectGuid, requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        user = userRepository.findByUsername(username);
        RoleEntity role = new RoleEntity();
        ProjectEntity project = new ProjectEntity();
        GroupEntity group = new GroupEntity();
        Set<RoleEntity> userRoles = user.getRoles();
        Set<ProjectEntity> userProjects = user.getProjects();
        Set<GroupEntity> userGroups = user.getGroups();

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

        Iterator<GroupEntity> iteratorForGroups = userGroups.iterator();
        while (iteratorForGroups.hasNext()) {
            group = iteratorForGroups.next();

            if (group.getProject().getProjectGuid().equals(projectGuid)) {
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
        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"admin"};
        if(!permissionsValidator.rolesForProjectCheck(user, projectGuid, requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        user = userRepository.findByUsername(username);
        RoleEntity role = new RoleEntity();
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

    @RequestMapping(value = "/getProjectGroups/{projectGuid}", method = RequestMethod.PUT)
    public List<GroupResponseDto> getProjectGroups(@PathVariable String projectGuid, @RequestBody GroupSearchDto groupSearchDto) {
        //auth check if user has admin role for the project
        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"admin"};
        if(!permissionsValidator.rolesForProjectCheck(user, projectGuid, requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        List<GroupResponseDto> groups = new ArrayList<>();

        ArrayList<Object[]> projectGroups = groupRepository.getProjectGroups(projectGuid, '%' + groupSearchDto.getNameContains() + '%');
        for (Object[] projectGroup : projectGroups) {
            Boolean addGroup = true;
            if(groupSearchDto.getCurrentGroups() != null){
                for (GroupResponseDto groupRequestDto : groupSearchDto.getCurrentGroups()) {
                    if(groupRequestDto.getGroupName().equals((String) projectGroup[1])){
                        addGroup = false;
                        break;
                    }
                }
            }

            if(addGroup){
                GroupResponseDto group = new GroupResponseDto();
                group.setGroupGuid((String) projectGroup[0]);
                group.setGroupName((String) projectGroup[1]);

                groups.add(group);
            }
        }

        return groups;
    }

    @RequestMapping(value = "/updateUserGroupsForProject/{projectGuid}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity updateUserGroupsForProject(@RequestBody UpdateUserGroupsRequestDto updateUserGroupsRequestDto, @PathVariable String projectGuid, @RequestParam String username) {
        //auth check if user has admin role for the project
        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"admin"};
        if(!permissionsValidator.rolesForProjectCheck(user, projectGuid, requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        user = userRepository.findByUsername(username);
        ProjectEntity project = projectRepository.findByProjectGuid(projectGuid);
        GroupEntity group = new GroupEntity();
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
                group.setProject(project);
                userGroups.add(group);

                groupRepository.save(group);
            }
        }

        user.setGroups(userGroups);

        userRepository.save(user);

        return new ResponseEntity(HttpStatus.OK);
    }
}
