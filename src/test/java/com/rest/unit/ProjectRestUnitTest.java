package com.rest.unit;

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


import com.dto.userManagement.ProjectRequestDto;
import com.repository.*;
import com.repository.userManagement.IAuthorityRepository;
import com.repository.userManagement.IProjectRepository;
import com.rest.userManagement.ProjectRest;
import com.utils.SecurityContextReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.security.Principal;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProjectRestUnitTest {

    @Mock
    private IProjectRepository mockProjectRepository;

//    @Mock
//    private IProjectUserRepository mockProjectUserRepository;

    @Mock
    private IAuthorityRepository mockAuthorityRepository;

    @Mock
    private ITranslationRepository mockTranslationRepository;

    @Mock
    private Principal mockPrincipalUser;

//    @Mock
//    private IGroupRepository mockGroupRepository;

//    @Mock
//    private IProjectGroupRepository mockProjectGroupRepository;

//    @Mock
//    private IInvitationRepository mockInvitationRepository;
//
//    @Mock
//    private IInvitationGroupRepository mockInvitationGroupRepository;

    @Mock
    private SecurityContextReader mockSecurityContextReader;

    @InjectMocks
    private ProjectRest projectRest;

    private ProjectRequestDto projectRequestDto = new ProjectRequestDto();

    @Before
    public void setup() {
        projectRequestDto.setDescription("My project");

//        ArrayList<GroupRequestDto> groups = new ArrayList<GroupRequestDto>();
//        GroupRequestDto group = new GroupRequestDto();
//        group.setGroupGuid("1");
//        group.setGroupName("group_1");
//        groups.add(group);
//
//        projectRequestDto.setGroups(groups);
//
//        ArrayList<InvitationRequestDto> invitations = new ArrayList<InvitationRequestDto>();
//        InvitationRequestDto invitation = new InvitationRequestDto();
//        invitation.setGroups(groups);
//        invitations.add(invitation);
//
//        projectRequestDto.setInvitations(invitations);
    }

    //user story 1.
    @Test
    public void createProject(){
        when(mockSecurityContextReader.getUsername()).thenReturn("admin@gmail.com");

      //  ResponseEntity responseEntity = projectRest.createProject(projectRequestDto);

      //  assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
 //       verify(mockProjectRepository, times(1)).save(any(ProjectEntity.class));

//        verify(mockProjectUserRepository, times(1)).save(any(ProjectUserEntity.class));
  //      verify(mockAuthorityRepository, times(1)).save(any(AuthorityEntity.class));
   //     verify(mockTranslationRepository, times(1)).save(any(TranslationEntity.class));
//        verify(mockGroupRepository, times(1)).save(any(GroupEntity.class));
//        verify(mockProjectGroupRepository, times(1)).save(any(ProjectGroupEntity.class));
//        verify(mockInvitationRepository, times(1)).save(any(InvitationEntity.class));
//        verify(mockInvitationGroupRepository, times(1)).save(any(InvitationGroupEntity.class));
    }

    @Test
    public void getProjects(){
        when(mockSecurityContextReader.getUsername()).thenReturn("admin@gmail.com");

      //  projectRest.getProjects(mockPrincipalUser);

        verify(mockProjectRepository, times(1)).findByCreatedByUser(any(String.class));
    }

}
