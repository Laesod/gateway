package com.rest.it;

import com.MockGatewayApplication;
import com.dto.GroupRequestDto;
import com.dto.InvitationRequestDto;
import com.dto.ProjectRequestDto;
import com.entity.*;
import com.repository.*;
import com.rest.ProjectRest;
import com.utils.SecurityContextReader;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by aautushk on 9/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockGatewayApplication.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback=true)
@ActiveProfiles("test")
public class ProjectRestIntTest {
    @Autowired
    public IUserRepository userRepository;

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

    //user story 1.
    @Test
    public void createProject() {
        UserEntity user = new UserEntity();

        user = userRepository.findByUsername("test@gmail.com");

        if(user == null){
            user = new UserEntity();
            user.setFirstName("tester");
            user.setLastName("tester");
            user.setPassword("password");
            user.setEnabled(true);
            user.setUsername("test@gmail.com");

            userRepository.save(user);// needed to be able to save authority
        }

        ProjectRequestDto projectRequestDto = new ProjectRequestDto();
        projectRequestDto.setDescription("My test project");

        GroupRequestDto group = new GroupRequestDto();
        group.setGroupName("group1");
        group.setGroupGuid("1");
        List<GroupRequestDto> groups = new ArrayList<GroupRequestDto>();
        groups.add(group);
        projectRequestDto.setGroups(groups);

        InvitationRequestDto invitation = new InvitationRequestDto();
        invitation.setGroups(groups);
        List<InvitationRequestDto> invitations = new ArrayList<InvitationRequestDto>();
        invitations.add(invitation);
        projectRequestDto.setInvitations(invitations);

        SecurityContextReader securityContextReaderMock = mock(SecurityContextReader.class);
        when(securityContextReaderMock.getUsername()).thenReturn("test@gmail.com");

        ProjectRest projectRest = new ProjectRest();

        projectRest.projectRepository = projectRepository;
        projectRest.translationRepository = translationRepository;
        projectRest.projectUserRepository = projectUserRepository;
        projectRest.authorityRepository = authorityRepository;
        projectRest.groupRepository = groupRepository;
        projectRest.projectGroupRepository = projectGroupRepository;
        projectRest.invitationRepository = invitationRepository;
        projectRest.invitationGroupRepository = invitationGroupRepository;
        projectRest.securityContextReader = securityContextReaderMock;

        projectRest.createProject(projectRequestDto);

        List<ProjectEntity> projects = projectRepository.findByCreatedByUser("test@gmail.com");
        List<TranslationEntity> translations = translationRepository.findByCreatedByUser("test@gmail.com");
        List<ProjectUserEntity> projectUsers = projectUserRepository.findByCreatedByUser("test@gmail.com");
        List<AuthorityEntity> authorities = authorityRepository.findByCreatedByUser("test@gmail.com");
        List<GroupEntity> foundGroups = groupRepository.findByCreatedByUser("test@gmail.com");
        List<ProjectGroupEntity> projectGroups = projectGroupRepository.findByCreatedByUser("test@gmail.com");
        List<InvitationEntity> foundInvitations = invitationRepository.findByCreatedByUser("test@gmail.com");
        List<InvitationGroupEntity> invitationGroups = invitationGroupRepository.findByCreatedByUser("test@gmail.com");

        assertEquals(projects.size(), 1);
        assertEquals(translations.size(), 1);
        assertEquals(projectUsers.size(), 1);
        assertEquals(authorities.size(), 1);
        assertEquals(groups.size(), 1);
        assertEquals(projectGroups.size(), 1);
        assertEquals(invitations.size(), 1);
        assertEquals(invitationGroups.size(), 1);

        projectRepository.delete(projects.get(0));
        translationRepository.delete(translations.get(0));
        projectUserRepository.delete(projectUsers.get(0));
        authorityRepository.delete(authorities.get(0));
        groupRepository.delete(foundGroups.get(0));
        projectGroupRepository.delete(projectGroups.get(0));
        invitationRepository.delete(foundInvitations.get(0));
        invitationGroupRepository.delete(invitationGroups.get(0));
    }
}
