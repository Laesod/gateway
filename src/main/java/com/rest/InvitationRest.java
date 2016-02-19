package com.rest;

import com.dto.GroupRequestDto;
import com.dto.InvitationRequestDto;
import com.dto.InvitationResponseDto;
import com.entity.*;
import com.repository.*;
import com.utils.BundleMessageReader;
import com.utils.PermissionsValidator;
import com.utils.SecurityContextReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by aautushk on 9/19/2015.
 */
@Component
@RestController
@RequestMapping("/gateway")
public class InvitationRest {
    @Autowired
    public IInvitationRepository invitationRepository;

    @Autowired
    public IRoleRepository roleRepository;

    @Autowired
    public IUserRepository userRepository;

    @Autowired
    public ITranslationRepository translationRepository;

    @Autowired
    public IProjectRepository projectRepository;

    @Autowired
    public IGroupRepository groupRepository;

    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    public ClassLoaderTemplateResolver emailTemplateResolver;

    @Autowired
    public SpringTemplateEngine thymeleaf;

    @Value("${mailserver.sendFrom}")
    public String mailSendFrom;

    @Value("${gatewayHost}")
    public String gatewayHost;

    @Value("${gatewayPort}")
    public String gatewayPort;

    @Value("${server.contextPath}")
    public String contextPath;

    public EmailSender emailSender;

    public BundleMessageReader bundleMessageReader = new BundleMessageReader();

    public SecurityContextReader securityContextReader = new SecurityContextReader();

    public PermissionsValidator permissionsValidator = new PermissionsValidator();

    @RequestMapping(value = "/createInvitation", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity createInvitation(@Valid @RequestBody InvitationRequestDto invitationRequestDto) {
        //auth check if user has admin role for the project
        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"admin"};
        if(!permissionsValidator.rolesForProjectCheck(user, invitationRequestDto.getProjectGuid(), requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        //check if user is already on the project or invitation for the project is already pending...
        UserEntity recipient = userRepository.findByUsername(invitationRequestDto.getEmail());

        if(recipient != null){
            Set<ProjectEntity> projects = recipient.getProjects();
            for (ProjectEntity project : projects) {
                if(project.getProjectGuid().equals(invitationRequestDto.getProjectGuid())){
                    throw new RuntimeException(bundleMessageReader.getMessage("UserIsAlreadyAssignedToProject"));
                }
            }
        }

        List<InvitationEntity> existingInvitations = invitationRepository.findByEmailAndIsAcceptedAndIsDeclined(invitationRequestDto.getEmail(), false, false);
        for (InvitationEntity existingInvitation : existingInvitations) {
            if(existingInvitation.getProject().getProjectGuid().equals(invitationRequestDto.getProjectGuid())){
                throw new RuntimeException(bundleMessageReader.getMessage("PendingIvitationAlreadyExists"));
            }
        }

        InvitationEntity invitation = new InvitationEntity();

        RoleEntity role = new RoleEntity();
        ProjectEntity project = new ProjectEntity();
        GroupEntity group = new GroupEntity();
        Set<RoleEntity> roles = new HashSet<RoleEntity>();
        Set<GroupEntity> groups = new HashSet<GroupEntity>();

        project = projectRepository.findByProjectGuid(invitationRequestDto.getProjectGuid());

        if (invitationRequestDto.getRolesToAdd() != null) {
            for (String roleGuid : invitationRequestDto.getRolesToAdd()) {
                role = roleRepository.findByRoleGuid(roleGuid);
                roles.add(role);
            }
        }

        if (invitationRequestDto.getGroupsToAdd() != null) {
            for (String groupGuid : invitationRequestDto.getGroupsToAdd()) {
                group = groupRepository.findByGroupGuid(groupGuid);
                groups.add(group);
            }
        }

        if (invitationRequestDto.getGroupsToCreateAndAdd() != null) {
            for (GroupRequestDto groupToCreate : invitationRequestDto.getGroupsToCreateAndAdd()) {
                group = new GroupEntity();
                group.setGroupName(groupToCreate.getGroupName());

                groups.add(group);

                groupRepository.save(group);
            }
        }

        invitation.setEmail(invitationRequestDto.getEmail());
        invitation.setProject(project);
        invitation.setRoles(roles);
        invitation.setGroups(groups);
        invitation.setAccepted(false);
        invitation.setDeclined(false);

        invitationRepository.save(invitation);

        if(emailSender == null){// this check needed for unit testing perposes
            emailSender = new EmailSender(mailSender, emailTemplateResolver, thymeleaf, securityContextReader.getUsername(), mailSendFrom);
        }

        String requestBaseUrl = this.gatewayHost + ':' + this.gatewayPort + this.contextPath;
        emailSender.sendInvitationEmail(requestBaseUrl);

        return new ResponseEntity(HttpStatus.OK);
    };

    @RequestMapping(value = "/getPendingInvitations", method = RequestMethod.GET)
    public List<InvitationResponseDto> getPendingInvitations(@RequestParam String projectGuid) {
        //auth check if user has admin role for the project
        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"admin"};
        if(!permissionsValidator.rolesForProjectCheck(user, projectGuid, requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        List<InvitationResponseDto> invitationResponseDtos = new ArrayList<InvitationResponseDto>();

        ArrayList<Object[]> invitations = invitationRepository.getPendingInvitationsForProject(projectGuid);

        for (Object[] invitation : invitations) {
            InvitationResponseDto invitationResponseDto = new InvitationResponseDto();
            invitationResponseDto.setInvtitationGuid((String) invitation[0]);
            invitationResponseDto.setEmail((String) invitation[1]);
            invitationResponseDto.setProjectDescription((String) invitation[2]);
            invitationResponseDto.setCreatedAt((Date) invitation[3]);

            UserEntity creator = userRepository.findByUsername((String) invitation[4]);
            invitationResponseDto.setCreatedBy(creator.getFirstName() + " " + creator.getLastName());

            invitationResponseDtos.add(invitationResponseDto);
        }

        return invitationResponseDtos;
    }

    @RequestMapping(value = "/getReceivedInvitations", method = RequestMethod.GET)
    public List<InvitationResponseDto> getReceivedInvitations() {
        //no auth check - any one can get list of received invitations
        List<InvitationResponseDto> invitationResponseDtos = new ArrayList<InvitationResponseDto>();

        ArrayList<Object[]> invitations = invitationRepository.getReceivedInvitations(securityContextReader.getUsername());

        for (Object[] invitation : invitations) {
            InvitationResponseDto invitationResponseDto = new InvitationResponseDto();
            invitationResponseDto.setInvtitationGuid((String) invitation[0]);
            invitationResponseDto.setEmail((String) invitation[1]);
            invitationResponseDto.setProjectDescription((String) invitation[2]);
            invitationResponseDto.setCreatedAt((Date) invitation[3]);

            UserEntity user = userRepository.findByUsername((String) invitation[4]);
            invitationResponseDto.setCreatedBy(user.getFirstName() + " " + user.getLastName());

            invitationResponseDtos.add(invitationResponseDto);
        }

        return invitationResponseDtos;
    }

    @RequestMapping(value = "/acceptInvitation/{invitationGuid}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity acceptInvitation(@PathVariable String invitationGuid) {
        //auth check - user should be able to accept only his invitations
        InvitationEntity invitation = invitationRepository.findByInvitationGuid(invitationGuid);

        if(!invitation.getEmail().equals(securityContextReader.getUsername())){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());

        Set<ProjectEntity> projects = user.getProjects();
        projects.add(invitation.getProject());
        user.setProjects(projects);

        Set<RoleEntity> roles = user.getRoles();
        for (RoleEntity role : invitation.getRoles()) {
            roles.add(role);
        }
        user.setRoles(roles);

        Set<GroupEntity> groups = user.getGroups();
        for (GroupEntity group : invitation.getGroups()) {
            groups.add(group);
        }
        user.setGroups(groups);

        invitation.setAccepted(true);

        invitationRepository.save(invitation);
        userRepository.save(user);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/declineInvitation/{invitationGuid}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity declineInvitation(@PathVariable String invitationGuid) {
        //auth check - user should be able to decline only his invitations

        InvitationEntity invitation = invitationRepository.findByInvitationGuid(invitationGuid);

        if(!invitation.getEmail().equals(securityContextReader.getUsername())){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        invitation.setDeclined(true);
        invitationRepository.save(invitation);

        return new ResponseEntity(HttpStatus.OK);
    }
}
