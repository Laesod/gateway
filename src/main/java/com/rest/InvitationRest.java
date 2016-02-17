package com.rest;

import com.dto.GroupRequestDto;
import com.dto.InvitationRequestDto;
import com.dto.InvitationResponseDto;
import com.entity.*;
import com.repository.*;
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

    public SecurityContextReader securityContextReader = new SecurityContextReader();

    @RequestMapping(value = "/createInvitation", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity createInvitation(@RequestBody InvitationRequestDto invitationRequestDto) {
        //auth check if user has admin role for the project

        //check if user is already on the project...

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

        invitationRepository.save(invitation);

//        if(emailSender == null){// this check needed for unit testing perposes
//            emailSender = new EmailSender(mailSender, emailTemplateResolver, thymeleaf, securityContextReader.getUsername(), mailSendFrom);
//        }
//
//        String requestBaseUrl = this.gatewayHost + ':' + this.gatewayPort + this.contextPath;
//        emailSender.sendInvitationEmail(requestBaseUrl);

        return new ResponseEntity(HttpStatus.OK);
    };

    @RequestMapping(value = "/getPendingInvitations", method = RequestMethod.GET)
    public List<InvitationResponseDto> getPendingInvitations(@RequestParam String projectGuid) {
        //auth check if user has admin role for the project

        List<InvitationResponseDto> invitationResponseDtos = new ArrayList<InvitationResponseDto>();

        ArrayList<Object[]> invitations = invitationRepository.getPendingInvitationsForProject(projectGuid);

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
//    @RequestMapping(value = "/getReceivedInvitations", method = RequestMethod.GET)
//    @Transactional
//    public List<InvitationResponseDto> getReceivedInvitations() {
//        List<InvitationEntity> invitationEntities = new ArrayList<InvitationEntity>();
//        List<InvitationResponseDto> invitationResponseDtos = new ArrayList<InvitationResponseDto>();
//
//      //  invitationEntities = invitationRepository.findByRecipientEmailAndIsInvitationAccepted(securityContextReader.getUsername(), false);
//
//        if(invitationEntities != null){
//            for(InvitationEntity invitationEntity : invitationEntities){
//                InvitationResponseDto invitationResponseDto = new InvitationResponseDto();
//                invitationResponseDto.setInvtitationGuid(invitationEntity.getInvitationGuid());
//
//               // TranslationEntity translationEntity = translationRepository.findByParentGuidAndFieldAndLanguage(invitationEntity.getProjectGuid(), "description", LocaleContextHolder.getLocale().getDisplayLanguage());
//
////                if(translationEntity != null){
////                    invitationResponseDto.setProjectDescription(translationEntity.getContent());
////                    invitationResponseDto.setCreatedBy(invitationEntity.getCreatedByUser());
////                    invitationResponseDto.setCreatedAt(invitationEntity.getCreatedAt());
////                }
//
//                invitationResponseDtos.add(invitationResponseDto);
//            }
//        }
//
//        return invitationResponseDtos;
//    };
}
