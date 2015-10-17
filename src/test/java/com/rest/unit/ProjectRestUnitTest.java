package com.rest.unit;

import com.dto.GroupRequestDto;
import com.dto.InvitationRequestDto;
import com.dto.InvitationResponseDto;
import com.dto.ProjectRequestDto;
import com.entity.*;
import com.repository.*;
import com.rest.InvitationRest;
import com.rest.ProjectRest;
import com.utils.SecurityContextReader;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by aautushk on 10/9/2015.
 */
public class ProjectRestUnitTest {
    ProjectRest projectRest = new ProjectRest();
    IProjectRepository mockProjectRepository = Mockito.mock(IProjectRepository.class);
    IProjectUserRepository mockProjectUserRepository = Mockito.mock(IProjectUserRepository.class);
    IAuthorityRepository mockAuthorityRepository = Mockito.mock(IAuthorityRepository.class);
    ITranslationRepository mockTranslationRepository = Mockito.mock(ITranslationRepository.class);
    IGroupRepository mockGroupRepository = Mockito.mock(IGroupRepository.class);
    IProjectGroupRepository mockProjectGroupRepository = Mockito.mock(IProjectGroupRepository.class);
    IInvitationRepository mockInvitationRepository = Mockito.mock(IInvitationRepository.class);
    IInvitationGroupRepository mockInvitationGroupRepository = Mockito.mock(IInvitationGroupRepository.class);

    SecurityContextReader mockSecurityContextReader = Mockito.mock(SecurityContextReader.class);

    ProjectRequestDto projectRequestDto = new ProjectRequestDto();

    public ProjectRestUnitTest(){
        projectRest.projectRepository = mockProjectRepository;
        projectRest.projectUserRepository = mockProjectUserRepository;
        projectRest.authorityRepository = mockAuthorityRepository;
        projectRest.translationRepository = mockTranslationRepository;
        projectRest.groupRepository = mockGroupRepository;
        projectRest.projectGroupRepository = mockProjectGroupRepository;
        projectRest.invitationRepository = mockInvitationRepository;
        projectRest.invitationGroupRepository = mockInvitationGroupRepository;

        projectRest.securityContextReader = mockSecurityContextReader;

        projectRequestDto.setDescription("My project");

        ArrayList<GroupRequestDto> groups = new ArrayList<GroupRequestDto>();
        GroupRequestDto group = new GroupRequestDto();
        group.setGroupGuid("1");
        group.setGroupName("group_1");
        groups.add(group);

        projectRequestDto.setGroups(groups);

        ArrayList<InvitationRequestDto> invitations = new ArrayList<InvitationRequestDto>();
        InvitationRequestDto invitation = new InvitationRequestDto();
        invitation.setGroups(groups);
        invitations.add(invitation);

        projectRequestDto.setInvitations(invitations);
    }

    //user story 1.
    @Test
    public void createProject(){
        when(mockSecurityContextReader.getUsername()).thenReturn("admin@gmail.com");

        ResponseEntity responseEntity = projectRest.createProject(projectRequestDto);

        assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
        verify(mockProjectRepository, times(1)).save(any(ProjectEntity.class));

        verify(mockProjectUserRepository, times(1)).save(any(ProjectUserEntity.class));
        verify(mockAuthorityRepository, times(1)).save(any(AuthorityEntity.class));
        verify(mockTranslationRepository, times(1)).save(any(TranslationEntity.class));
        verify(mockGroupRepository, times(1)).save(any(GroupEntity.class));
        verify(mockProjectGroupRepository, times(1)).save(any(ProjectGroupEntity.class));
        verify(mockInvitationRepository, times(1)).save(any(InvitationEntity.class));
        verify(mockInvitationGroupRepository, times(1)).save(any(InvitationGroupEntity.class));
    }
}